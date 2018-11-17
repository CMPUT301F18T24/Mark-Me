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
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchIOController {

    private static JestDroidClient client = null;

    /**
     * Create a singleton of the JestDroidClient.
     */
    public static void setClient() {
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
    public static List<UserModel> getUser(String username) {
        setClient();

        // Case does not matter.
        String query = "{ \"query\" : \n" +
                       "{ \"match\" :\n" +
                       "{ \"username\" : \" " + username + " \" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("users")
                .build();

        try {
            JestResult result = client.execute(search);
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

    public static void addUser(UserModel user) {
        setClient();

        // Create a data class to save to elastic search. This lets us avoid saving the extra
        // information contained in the usermodel.
        NewUser newUser = new NewUser(user.getUsername(),
                                      user.getEmail(),
                                      user.getPhone(),
                                      user.getPassword(),
                                      user.getUserType());

        Index index = new Index.Builder(newUser)
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

    /**
     * Queries a list the elastic search database for a list of users. See also getUser().
     */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<UserModel>> {

        protected ArrayList<UserModel> doInBackground(String... strings) {
            ArrayList<UserModel> users = new ArrayList<UserModel>();
            for (String s: strings) {
                users.addAll(getUser(s));
            }
            return users;
        }
    }

    public static class AddUserTask extends AsyncTask<UserModel, Void, Void> {

        protected Void doInBackground(UserModel... params) {
            for (UserModel user : params) {
                addUser(user);
            }

            return null;
        }
    }

}

class NewUser {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String userType;

    public NewUser(String username, String email, String phone, String password,
                   String userType) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }
}
