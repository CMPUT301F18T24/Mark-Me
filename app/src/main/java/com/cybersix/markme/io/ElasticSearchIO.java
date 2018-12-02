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

import com.cybersix.markme.adapter.ProblemDataAdapter;
import com.cybersix.markme.adapter.RecordDataAdapter;
import com.cybersix.markme.adapter.UserDataAdapter;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.params.SearchType;

public class ElasticSearchIO implements UserModelIO, ProblemModelIO, RecordModelIO {
    private JestDroidClient client = null;
    private final String INDEX = "cmput301f18t24test2";
    private final String URI = "http://cmput301.softwareprocess.es:8080/";
    private Context context = null;

    protected ElasticSearchIO() {
        setClient();
    }

    /**
     * Create a singleton of the JestDroidClient.
     */
    public void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder(URI).build();
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

        ArrayList<UserModel> users = new ArrayList<UserModel>();

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
            if (result.isSucceeded()) {
                Iterator<BulkResult.BulkResultItem> it = result.getItems().iterator();
                while (it.hasNext())
                    Log.i("ELASTICSEARCHIO", it.next().type);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void findUser(String username, OnTaskComplete handler) {
        // when adding a user, a query should be done
        // for the user's type. A factory should be used
        // to return the correct UserModel Instance
        new FindUserTask().execute(username, handler);
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

    public void addRecord(RecordModel record) {
        try {
            new AddRecordTask().execute(record, GeneralIO.emptyHandler).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            ArrayList<UserModel> users = new ArrayList<UserModel>();
            String name = (String) params[0];
            OnTaskComplete runnable = (OnTaskComplete) params[1];
            users.addAll(asyncFindUser(name));
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
}
