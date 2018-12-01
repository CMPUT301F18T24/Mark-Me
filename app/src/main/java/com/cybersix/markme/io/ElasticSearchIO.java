package com.cybersix.markme.io;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cybersix.markme.adapter.ProblemDataAdapter;
import com.cybersix.markme.adapter.RecordDataAdapter;
import com.cybersix.markme.adapter.UserDataAdapter;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.params.SearchType;

public class ElasticSearchIO implements UserModelIO, ProblemModelIO, RecordModelIO {
    private JestDroidClient client = null;
    private final String INDEX = "cmput301f18t24test";
    private final String URI = "http://cmput301.softwareprocess.es:8080/";
    private Context context = null;

    protected ElasticSearchIO() {
        setClient();
    }

    public boolean isConnected() {
        return true;
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

    private void asyncAddProblem(ProblemModel problem) {
        Index index = new Index.Builder(new ProblemDataAdapter(problem))
                .index(INDEX)
                .type(problem.getClass().getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                problem.setProblemId(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void asyncAddRecord(RecordModel record) {
        Index index = new Index.Builder(new RecordDataAdapter(record))
                .index(INDEX)
                .type(record.getClass().getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                record.setRecordId(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void asyncAddUser(UserModel user) {
        Index index = new Index.Builder(new UserDataAdapter(user))
                .index(INDEX)
                .type(UserModel.class.getSimpleName())
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                user.setUserId(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public UserModel findUser(String username) {
        // when adding a user, a query should be done
        // for the user's type. A factory should be used
        // to return the correct UserModel Instance
        try {
            ArrayList<UserModel> users = new FindUserTask().execute(username).get();
            if (!users.isEmpty())
                return users.get(0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(UserModel user) {
        // If username exists, then send a fail.
        if (findUser(user.getUsername()) != null) {
            return false;
        }

        try {
            new AddUserTask().execute(user).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(UserModel user) {
        return false;
    }

    @Override
    public boolean editUser(UserModel user) {
        try {
            new AddUserTask().execute(user).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProblemModel findProblem(String problemId) {
        try {
            return new FindProblemTask().execute(problemId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addProblem(ProblemModel problem) {
        try {
            new AddProblemTask().execute(problem).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<ProblemModel> getProblems(UserModel user) {
        ArrayList<ProblemModel> problems = new ArrayList<>();
        try {
            problems = new GetProblemTask().execute(user).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return problems;
    }

    @Override
    public RecordModel findRecord(String recordId) {
        try {
            return new FindRecordTask().execute(recordId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addRecord(RecordModel record) {
        try {
            new AddRecordTask().execute(record).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<RecordModel> getRecords(ProblemModel problem) {
        ArrayList<RecordModel> records = new ArrayList<>();
        try {
            records = new GetRecordTask().execute(problem).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * Queries a list the elastic search database for a list of users. See also getUser().
     */
    private class FindUserTask extends AsyncTask<String, Void, ArrayList<UserModel>> {
        protected ArrayList<UserModel> doInBackground(String... usernames) {
            ArrayList<UserModel> users = new ArrayList<UserModel>();
            for (String name: usernames) {
                users.addAll(asyncFindUser(name));
            }
            return users;
        }
    }

    private class AddUserTask extends AsyncTask<UserModel, Void, Void> {
        protected Void doInBackground(UserModel... params) {
            for (UserModel user : params) {
                asyncAddUser(user);
            }
            return null;
        }
    }

    /**
     * Gets the record for each given problemID.
     */
    private class GetRecordTask extends AsyncTask<ProblemModel, Void, ArrayList<RecordModel>> {
        protected ArrayList<RecordModel> doInBackground(ProblemModel... params) {
            ArrayList<RecordModel> records = new ArrayList<RecordModel>();
            for (ProblemModel p: params) {
                records.addAll(asyncGetRecords(p));
            }
            return records;
        }
    }

    /**
     * Gets the record for each given problemID.
     */
    private class FindRecordTask extends AsyncTask<String, Void, RecordModel> {
        protected RecordModel doInBackground(String... params) {
            ArrayList<RecordModel> records = new ArrayList<RecordModel>();
            for (String id: params) {
                records.addAll(asyncGetRecords(id));
            }
            if (!records.isEmpty())
                return records.get(0);
            else
                return null;
        }
    }

    /**
     * Adds all records for each given problem.
     */
    private class AddRecordTask extends AsyncTask<RecordModel, Void, Void> {
        protected Void doInBackground(RecordModel... params) {
            for (RecordModel r: params) {
                asyncAddRecord(r);
            }
            return null;
        }
    }

    private class GetProblemTask extends AsyncTask<UserModel, Void, ArrayList<ProblemModel>> {
        protected ArrayList<ProblemModel> doInBackground(UserModel... params) {
            ArrayList<ProblemModel> problems = new ArrayList<ProblemModel>();
            for (UserModel u: params) {
                problems.addAll(asyncGetProblems(u));
            }
            return problems;
        }
    }

    private class FindProblemTask extends AsyncTask<String, Void, ProblemModel> {
        protected ProblemModel doInBackground(String... params) {
            ArrayList<ProblemModel> problems = new ArrayList<>();
            for (String id: params) {
                problems.addAll(asyncGetProblems(id));
            }
            if(!problems.isEmpty())
                return problems.get(0);
            else
                return null;
        }
    }

    /**
     * Adds a problem to the elastic search database. See also addProblem().
     */
    private class AddProblemTask extends AsyncTask<ProblemModel, Void, Void> {
        protected Void doInBackground(ProblemModel... params) {
            for (ProblemModel problem : params) {
                asyncAddProblem(problem);
            }
            return null;
        }
    }
}
