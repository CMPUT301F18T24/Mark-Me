package com.cybersix.markme.io;

import android.os.AsyncTask;
import android.util.Log;

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

public class ElasticSearchIO implements UserModelIO {
    private static ElasticSearchIO instance = null;
    private JestDroidClient client = null;

    private ElasticSearchIO() {
        setClient();
    }

    public static ElasticSearchIO getInstance() {
        if (instance == null)
            instance = new ElasticSearchIO();

        return instance;
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
                    .Builder("http://cmput301.softwareprocess.es:8080").build();
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
        String query = "{ \"query\" : \n" +
                "{ \"match\" :\n" +
                "{ \"username\" : \"" + username + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("users")
                .build();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                return result.getSourceAsObjectList(UserModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<UserModel>();
    }

    private void asyncAddUser(UserModel user) {
        Index index = new Index.Builder(user)
                .index("cmput301f18t24test")
                .type("users")
                .build();

        try {
            DocumentResult result = client.execute(index);
            Log.d("Vishal", "addUser: " + result.isSucceeded() + " " + index.getId());
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                user.setUserID(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserModel findUser(String username) {
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
    public void editUser(UserModel user) {

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
}
