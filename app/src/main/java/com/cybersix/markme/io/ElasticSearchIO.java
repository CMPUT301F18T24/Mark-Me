/**
 * CMPUT 301 Team 24
 *
 * This is the elastic search IO controller that will handle all of the queries, insertions, and
 * updates to the connected elastic search database.
 *
 * Version 0.1
 *
 * Date: 2018-11-17
 *
 * Copyright Notice
 * @author Vishal Patel
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.adapter.UserDataAdapter
 * @see com.cybersix.markme.adapter.RecordDataAdapter
 * @see com.cybersix.markme.adapter.ProblemDataAdapter
 */
package com.cybersix.markme.io;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.cybersix.markme.adapter.AssignedUserAdapter;
import com.cybersix.markme.adapter.ProblemDataAdapter;
import com.cybersix.markme.adapter.RecordDataAdapter;
import com.cybersix.markme.adapter.TransferDataAdapter;
import com.cybersix.markme.adapter.UserDataAdapter;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.MultiSearch;
import io.searchbox.core.MultiSearchResult;
import io.searchbox.core.Search;
import io.searchbox.params.SearchType;

public class ElasticSearchIO implements UserModelIO, ProblemModelIO, RecordModelIO, AssignmentIO {
    private JestDroidClient client = null;
    private final String INDEX = "cmput301f18t24test2";
    private final String URI = "http://cmput301.softwareprocess.es:8080/";
    private Context context = null;
    private static ElasticSearchIO instance = null;
    private final String USER_ASSIGNMENT = "UserAssignment";
    private final String TYPE_ASSIGNMENT = "AssignmentTransfer";
    private final String TYPE_TRANSFER = "transfer";

    protected ElasticSearchIO() {
        setClient();
    }

    /**
     * Create a singleton of the JestDroidClient.
     */
    public void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder(URI)
                    .build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Queries the elasticsearch database for a user. Do NOT call directly, call getUserTask
     * instead.
     * @param username - The username you want to find.
     * @return - Returns a list of users that match the username, which should be 0 or 1.
     */
    private List<UserModel> asyncFindUser(String username) {
        // Case does matter, and subset of usernames will not cause problems.
        String query = "{\"from\" : 0, \"size\" : 10000,\n" +
                "\t\"query\" : {\n" +
                "\t\t\"match\": {\"username\": \"" + username + "\"}\t\n" +
                "\t}\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(UserModel.class.getSimpleName())
                .build();

        ArrayList<UserModel> users = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<UserDataAdapter> userAdapter = result.getSourceAsObjectList(UserDataAdapter.class);
                for (UserDataAdapter user: userAdapter) {
                    users.add(user.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private boolean asyncAddProblem(ProblemModel problem) {
        Index index = new Index.Builder(new ProblemDataAdapter(problem))
                .index(INDEX)
                .type(problem.getClass().getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                problem.setProblemId(result.getId());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean asyncBulkAddPatient(Patient patient) {
        Bulk.Builder bulkBuilder = new Bulk.Builder()
                .defaultIndex(INDEX)
                .defaultType(ProblemModel.class.getSimpleName());

        for (ProblemModel problem: patient.getProblems()) {
            problem.setPatientId(patient.getUserId());
            bulkBuilder.addAction(
                    new Index.Builder(new ProblemDataAdapter(problem))
                    .index(INDEX)
                    .type(problem.getClass().getSimpleName())
                    .build()
            );
            for (RecordModel record: problem.getRecords()) {
                bulkBuilder.addAction(
                        new Index.Builder(new RecordDataAdapter(record))
                                .index(INDEX)
                                .type(record.getClass().getSimpleName())
                                .build()
                );
            }
        }

        try {
            BulkResult result = client.execute(bulkBuilder.build());
            Log.i("ELASTICSEARCHIO", "" + result.isSucceeded());
            if (result.isSucceeded()) {
            }
            return true;
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * Gets all problems for a user. Note: It does not initialize the records for each problem.
     * They need to be grabbed separately.
     * @param user The UserModel of the user to pull all problems using its userId.
     * @return All problems for the given userID.
     */
    private List<ProblemModel> asyncGetProblems(UserModel user) {
        String query = "{\"from\" : 0, \"size\" : 10000,\n" +
                "\t\"query\" : {\n" +
                "\t\t\"match\": {\"patientId\": \"" + user.getUserId() + "\"}\t\n" +
                "\t}\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(ProblemModel.class.getSimpleName())
                .build();

        List<ProblemModel> problems = new ArrayList<ProblemModel>();
        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<ProblemDataAdapter> adapters = result.getSourceAsObjectList(ProblemDataAdapter.class);
                for (ProblemDataAdapter problem : adapters) {
                    problems.add( problem.get() );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return problems;
    }

    /**
     * Gets all problems for a user. Note: It does not initialize the records for each problem.
     * They need to be grabbed separately.
     * @param problemId
     * @return All problems for the given userID.
     */
    private List<ProblemModel> asyncGetProblems(String problemId) {
        String query = "{\"from\" : 0, \"size\" : 10000,\n" +
                "\t\"query\" : {\n" +
                "\t\t\"match\": {\"problemId\": \"" + problemId + "\"}\t\n" +
                "\t}\n" +
                "}";
        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(ProblemModel.class.getSimpleName())
                .build();

        List<ProblemModel> problems = new ArrayList<ProblemModel>();
        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<ProblemDataAdapter> adapters = result.getSourceAsObjectList(ProblemDataAdapter.class);
                for (ProblemDataAdapter problem : adapters) {
                    problems.add( problem.get() );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return problems;
    }

    private boolean asyncAddRecord(RecordModel record) {
        Index index = new Index.Builder(new RecordDataAdapter(record))
                .index(INDEX)
                .type(record.getClass().getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                record.setRecordId(result.getId());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean asyncAddUser(UserModel user) {
        Index index = new Index.Builder(new UserDataAdapter(user))
                .index(INDEX)
                .type(UserModel.class.getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                user.setUserId(result.getId());
                return true;
            }
            Log.d("vishal_addUser", user.getUsername() + " " + result.isSucceeded());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<RecordModel> asyncGetRecords(ProblemModel problem) {
        String query = "{\"from\" : 0, \"size\" : 10000,\n" +
                "\t\"query\" : {\n" +
                "\t\t\"match\": {\"problemId\": \"" + problem.getProblemId() + "\"}\t\n" +
                "\t}\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(RecordModel.class.getSimpleName())
                .build();

        ArrayList<RecordModel> recordList = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<RecordDataAdapter> adapters = result.getSourceAsObjectList(RecordDataAdapter.class);
                for (RecordDataAdapter r: adapters) {
                    recordList.add(r.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordList;
    }

    private List<RecordModel> asyncGetRecords(String recordId) {
        String query = "{\"from\" : 0, \"size\" : 10000,\n" +
                "\t\"query\" : {\n" +
                "\t\t\"match\": {\"recordId\": \"" + recordId + "\"}\t\n" +
                "\t}\n" +
                "}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(RecordModel.class.getSimpleName())
                .build();

        ArrayList<RecordModel> recordList = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<RecordDataAdapter> adapters = result.getSourceAsObjectList(RecordDataAdapter.class);
                for (RecordDataAdapter r: adapters) {
                    recordList.add(r.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordList;
    }

    /**
     * Gets all of the assigned users (patient) this the current user is taking are of.
     * @param providerId The Care Provider's username
     * @return returns the string pair of id information (patientID, providerID)
     */
    private List<Pair<String, String>> asyncGetAssignedUsers(String providerId) {
        // Case does matter, and subset of usernames will not cause problems.
        String query = "{ \"query\" : \n" +
                "{ \"match\" :\n" +
                "{ \"providerID\" : \"" + providerId + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(USER_ASSIGNMENT)
                .build();

        ArrayList<Pair<String, String>> assignments = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<AssignedUserAdapter> userAdapter = result.getSourceAsObjectList(AssignedUserAdapter.class);
                for (AssignedUserAdapter assignment : userAdapter) {
                    assignments.add(assignment.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    /**
     * Adds an assignment to the elastic search for the user to then keep track of
     * @param patientUserName The patient that wants to be tracked
     * @param providerID The provider that is tracking the patient
     */
    private void asyncAddAssignedUser(String patientUserName, String providerID){
        Index index = new Index.Builder(new AssignedUserAdapter(patientUserName, providerID))
                .index(INDEX)
                .type(USER_ASSIGNMENT)
                .build();
        try {
            DocumentResult result = client.execute(index);
            // TODO: May not need this part unless we need to keep track of the assignment id
//            if (result.isSucceeded()) {
//                // Associate the ID with the original userModel object.
//                user.setUserId(result.getId());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String asyncGenerateAssignmentCode(String username) {
        String shortcode = generateShortCode();
        Index index = new Index.Builder(new TransferDataAdapter(shortcode, username))
                .index(INDEX)
                .type(TYPE_ASSIGNMENT)
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Return the short code
                return shortcode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private List<String> asyncGetCodeAssignmentUser(String shortCode) {

        String query = "{ \"query\" : \n" +
                "{ \"match\" :\n" +
                "{ \"shortcode\" : \"" + shortCode + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(TYPE_ASSIGNMENT)
                .build();

        ArrayList<String> usernameList = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<TransferDataAdapter> transferData = result.getSourceAsObjectList(TransferDataAdapter.class);
                for (TransferDataAdapter t: transferData) {
                    usernameList.add(t.getUsername());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usernameList;
    }

    private List<String> asyncTransferUser(String shortCode) {

        String query = "{ \"query\" : \n" +
                "{ \"match\" :\n" +
                "{ \"shortcode\" : \"" + shortCode + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex(INDEX)
                .addType(TYPE_TRANSFER)
                .build();

        ArrayList<String> usernameList = new ArrayList<>();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<TransferDataAdapter> transferData = result.getSourceAsObjectList(TransferDataAdapter.class);
                for (TransferDataAdapter t: transferData) {
                    usernameList.add(t.getUsername());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usernameList;
    }


    // Generates a random 5 character string.
    // Credit to:  Eugen Paraschiv, Generate Random Bounded String with Plain Java
    // Link: https://www.baeldung.com/java-random-string
    public String generateShortCode() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public String asyncGenerateTransferCode(String username) {
        String shortcode = generateShortCode();
        Index index = new Index.Builder(new TransferDataAdapter(shortcode, username))
                .index(INDEX)
                .type(TYPE_TRANSFER)
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Return the short code
                return shortcode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void findUser(String username, OnTaskComplete handler) {
        // when adding a user, a query should be done
        // for the user's type. A factory should be used
        // to return the correct UserModel Instance
        new FindUserTask().execute(username, handler);
    }

    public UserModel findUser(String username) {
        // when adding a user, a query should be done
        // for the user's type. A factory should be used
        // to return the correct UserModel Instance
        final ArrayList<UserModel> users = new ArrayList<>();
        try {
            new FindUserTask().execute(username, new OnTaskComplete() {
                @Override
                public void onTaskComplete(Object result) {
                    users.addAll((ArrayList<UserModel>) result);
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    @Override
    public String transferUser(String shortCode) {
        try {
            return new TransferAccountTask().execute(shortCode).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateTransferCode(String username) {
        try {
            return new GenerateTransferCodeTask().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addUser(UserModel user, final OnTaskComplete handler) {
        new AddUserTask().execute(user, handler);
    }

    public void bulkAddPatient(Patient patient, OnTaskComplete handler) {
        new BulkAddTask().execute(patient, handler);
    }

    @Override
    public void deleteUser(UserModel user, OnTaskComplete handler) {

    }

    // TODO: Add all of the functions that are to call the assignment of functions

    @Override
    public ArrayList<UserModel> getAssignedUsers(String providerID) {
        // get all of the patient Ids based from the provider id
        // then get all of the user models
        // TODO: For now I am going to test just the ids of the patient and then
        // TODO: add the functionality of showing the username information
        ArrayList<Pair<String, String>> results = new ArrayList<>();
        try {
            // first string is the patient ID, the second is the provider ID
            results = new FindAssignedUserTask().execute(providerID).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<UserModel> resultUsers = new ArrayList<>();
        for (Pair<String, String> resultPair: results) {
            // we are going to get the User from each of the patient IDs
            resultUsers.add(findUser(resultPair.first));
        }
        return resultUsers;
    }

    @Override
    public void addAssignedUser(String patientUserName, String providerID) {
        // Add the user assignment ids to the elastic search IO
        try {
            Pair<String, String> input = new Pair<>(patientUserName, providerID);
            new AddAssignedUserTask().execute(input).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAssignedUser(String patientID, String providerID) {
        // remove the assigned user from the elastic search database
        // TODO: To be implemented
        // TODO: Please note that the Delete.builder requires the assignment ID
    }

    @Override
    public String generateAssignmentCode(String username) {
        try {
            return new GenerateAssignmentCodeTask().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserAssignmentCode(String shortCode) {
        try {
            return new GetAssignmentCodeUserTask().execute(shortCode).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editUser(UserModel user, OnTaskComplete handler) {
        new AddUserTask().execute(user, handler);
    }

    @Override
    public void findProblem(String problemId, OnTaskComplete handler) {
        new FindProblemTask().execute(problemId, handler);
    }

    @Override
    public void addProblem(ProblemModel problem, OnTaskComplete handler) {
        new AddProblemTask().execute(problem, handler);
    }

    @Override
    public void getProblems(UserModel user, OnTaskComplete handler) {
        new GetProblemTask().execute(user, handler);
    }

    @Override
    public void findRecord(String recordId, OnTaskComplete handler) {
        new FindRecordTask().execute(recordId, handler);
    }

    @Override
    public void addRecord(RecordModel record, OnTaskComplete handler) {
        new AddRecordTask().execute(record, handler);
    }

    public ArrayList<RecordModel> getRecords(final ProblemModel problem) {
        final ArrayList<RecordModel> records = new ArrayList<>();
        try {
            new GetRecordTask().execute(problem, new OnTaskComplete() {
                @Override
                public void onTaskComplete(Object result) {
                    records.addAll((ArrayList<RecordModel>) result);
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void getRecords(ProblemModel problem, OnTaskComplete handler) {
        new GetRecordTask().execute(problem, handler);
    }

    /**
     * Queries a list the elastic search database for a list of users. See also getUser().
     */
    private class FindUserTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {

            String name = (String) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            List<UserModel> users = asyncFindUser(name);
            return new Object[] {users, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            ArrayList<UserModel> users = (ArrayList<UserModel>) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(users);
        }
    }

    private class AddUserTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            UserModel user = (UserModel) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            Boolean success = asyncAddUser(user);
            return new Object[] {success, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            Boolean success = (Boolean) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(success);
        }
    }

    private class BulkAddTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            Patient user = (Patient) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            Boolean success = asyncBulkAddPatient(user);
            return new Object[] {success, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            Boolean success = (Boolean) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(success);
        }
    }

    /**
     * Queries for the list of user assignments
     */
    private class FindAssignedUserTask extends AsyncTask<String, Void, ArrayList<Pair<String, String>>> {
        protected ArrayList<Pair<String, String>> doInBackground(String... providerIDs) {
            ArrayList<Pair<String, String>> ids = new ArrayList<>();
            for (String providerID: providerIDs) {
                ids.addAll(asyncGetAssignedUsers(providerID));
            }
            return ids;
        }
    }

    private class AddAssignedUserTask extends AsyncTask<Pair<String, String>, Void, Void> {
        protected Void doInBackground(Pair<String, String>... pairs) {
            for (Pair<String, String> assignment: pairs) {
                asyncAddAssignedUser(assignment.first, assignment.second);
            }
            return null;
        }
    }

    private class GenerateAssignmentCodeTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            for (String username: params) {
                String shortcode = asyncGenerateAssignmentCode(username);
                if (shortcode.compareTo("") != 0) {
                    return shortcode;
                }
            }
            return null;
        }
    }

    private class GetAssignmentCodeUserTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            ArrayList<String> usernames = new ArrayList<>();
            for (String shortCode: params) {
                usernames.addAll(asyncGetCodeAssignmentUser(shortCode));
            }
            if(!usernames.isEmpty()) {
                return usernames.get(0);
            }

            return null;
        }
    }

    /**
     * Gets the record for each given problemID.
     */
    private class GetRecordTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            ArrayList<RecordModel> records = new ArrayList<>();
            ProblemModel p = (ProblemModel) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            records.addAll(asyncGetRecords(p));
            return new Object[] {records, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            ArrayList<RecordModel> records = (ArrayList<RecordModel>) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(records);
        }
    }

    /**
     * Gets the record for each given problemID.
     */
    private class FindRecordTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            ArrayList<RecordModel> records = new ArrayList<>();
            String id = (String) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            asyncGetRecords(id);
            if (!records.isEmpty())
                return new Object[] {records.get(0), runnable};
            else
                return new Object[] {null, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            ArrayList<RecordModel> records = (ArrayList<RecordModel>) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(records);
        }
    }

    /**
     * Adds all records for each given problem.
     */
    private class AddRecordTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            RecordModel record = (RecordModel) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            Boolean success = asyncAddRecord(record);
            return new Object[] {success, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            Boolean success = (Boolean) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(success);
        }
    }

    private class GetProblemTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            UserModel user = (UserModel) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            ArrayList<ProblemModel> problems = new ArrayList<ProblemModel>();
            problems.addAll(asyncGetProblems(user));
            return new Object[] {problems, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            ArrayList<ProblemModel> problems = (ArrayList<ProblemModel>) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(problems);
        }
    }

    private class FindProblemTask extends AsyncTask<Object, Void, Object[]> {
        protected Object[] doInBackground(Object... params) {
            ArrayList<ProblemModel> problems = new ArrayList<>();
            String id = (String) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            problems.addAll(asyncGetProblems(id));
            if(!problems.isEmpty())
                return new Object[] {problems.get(0), runnable};
            else
                return new Object[] {null, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            ArrayList<ProblemModel> problems = (ArrayList<ProblemModel>) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(problems);
        }
    }

    /**
     * Adds a problem to the elastic search database. See also addProblem().
     */
    private class AddProblemTask extends AsyncTask<Object, Void, Object[]> {
        @Override
        protected Object[] doInBackground(Object... params) {
            ProblemModel problem = (ProblemModel) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            Boolean success = asyncAddProblem(problem);
            return new Object[] {success, runnable};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            Boolean success = (Boolean) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            runnable.onTaskComplete(success);
        }
    }

    private class TransferAccountTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            ArrayList<String> usernames = new ArrayList<>();
            for (String shortCode: params) {
                usernames.addAll(asyncTransferUser(shortCode));
            }
            if(!usernames.isEmpty()) {
                return usernames.get(0);
            }

            return null;
        }
    }

    private class GenerateTransferCodeTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            for (String username: params) {
                String shortcode = asyncGenerateTransferCode(username);
                if (shortcode.compareTo("") != 0) {
                    return shortcode;
                }
            }
            return null;
        }
    }
}
