package com.cybersix.markme;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    public static List<ProblemModel> getProblems(String userID) {
        setClient();

        String query = "{ \"query\" : \n" +
                       "{ \"match\" :\n" +
                       "{ \"userID\" : \"" + userID + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("problems")
                .build();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<ProblemModel> problemList;
                problemList = result.getSourceAsObjectList(ProblemModel.class);
                return problemList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<ProblemModel>();
    }

    public static void addProblem(ProblemModel problem) {
        setClient();

        UserProfileController profileController = UserProfileController.getInstance();

        // Create a data class to save to elastic search. This lets us avoid saving the extra
        // information contained in the problemModel.
        NewProblem newProblem = new NewProblem(problem.getTitle(),
                                               problem.getDescription(),
                                               problem.getDateStarted(),
                                               profileController.user.getUserID());

        Index index = new Index.Builder(newProblem)
                .index("cmput301f18t24test")
                .type("problems")
                .build();

        try {
            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                // Associate the ID with the original userModel object.
                problem.setProblemID(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addRecords(ProblemModel problem) {
        setClient();

        UserProfileController profileController = UserProfileController.getInstance();

        for (RecordModel record : problem.getRecords()) {

            // Create a data class to save to elastic search. This lets us avoid saving the extra
            // information contained in the problemModel.
            NewRecord newRecord = new NewRecord(record.getTitle(),
                    record.getDescription(), record.getTimestamp(), record.getComment(),
                    record.getPhotos(), record.getBodyLocation(), record.getMapLocation());


            Index index = new Index.Builder(newRecord)
                    .index("cmput301f18t24test")
                    .type("records")
                    .build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    // Associate the ID with the original userModel object.
                    record.setRecordID(result.getId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static class GetProblemTask extends AsyncTask<String, Void, ArrayList<ProblemModel>> {

        protected ArrayList<ProblemModel> doInBackground(String... strings) {
            ArrayList<ProblemModel> problems = new ArrayList<ProblemModel>();
            for (String s: strings) {
                problems.addAll(getProblems(s));
            }
            return problems;
        }

    }

    /**
     * Adds a problem to the elastic search database. See also addProblem().
     */
    public static class AddProblemTask extends AsyncTask<ProblemModel, Void, Void> {

        protected Void doInBackground(ProblemModel... params) {
            for (ProblemModel problem : params) {
                addProblem(problem);
            }

            return null;
        }
    }

    /**
     * Adds all records for each given problem.
     */
    public static class AddRecordTask extends AsyncTask<ProblemModel, Void, Void> {

        protected Void doInBackground(ProblemModel... params) {
            for (ProblemModel problem : params) {
                addRecords(problem);
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

class NewProblem {

    private String title;
    private String description;
    private Date started;
    private String userID;

    public NewProblem(String title, String description, Date started, String userID) {
        this.title = title;
        this.description = description;
        this.started = started;
        this.userID = userID;
    }
}

class NewRecord {

    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<Bitmap> photos;
    private BodyLocation bodyLocation;
    private Location mapLocation;

    public NewRecord(String title, String description, Date timestamp, String comment,
                     ArrayList<Bitmap> photos, BodyLocation bodyLocation, Location mapLocation) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.comment = comment;
        this.photos = photos;
        this.bodyLocation = bodyLocation;
        this.mapLocation = mapLocation;
    }

}
