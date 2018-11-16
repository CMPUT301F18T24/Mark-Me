package com.cybersix.markme;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class ElasticSearchIOController {

    private static JestDroidClient client = null;

    public static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    // TODO: Implement the userID.
    public static List<UserModel> getUser(String userID) {
        setClient();

        String query = "{ \"query\" : \n" +
                       "{ \"match\" :\n" +
                       "{ \"username\" : \"myFirstUsername\" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("users")
                .build();

        Log.d("Vishal", "I built the query.");

        try {
            JestResult result = client.execute(search);
            Log.d("Vishal", "Search was successful");
            if (result.isSucceeded()) {
                List<UserModel> userList;
                userList = result.getSourceAsObjectList(UserModel.class);
                return userList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<UserModel>();
    }

    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<UserModel>> {

        protected ArrayList<UserModel> doInBackground(String... strings) {
            ArrayList<UserModel> users = new ArrayList<UserModel>();
            Log.d("Vishal", "I'm ready to get users.");
            for (String s: strings) {
                users.addAll(getUser(s));
            }
            return users;
        }
    }

}
