/**
 * CMPUT 301 Team 24
 *
 * This controller handles getting and pulling data from the Elastic Search database. Currently
 * it supports getting and pulling data for UserModel, ProblemModel and RecordMove.
 *
 * Todo: - Implement methods that allow for searching by field values.
 *
 * Known Bugs: - Any query will only return a maximum of 10 records.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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
//        setClient();
//
//        // Create a data class to save to elastic search. This lets us avoid saving the extra
//        // information contained in the usermodel.
//        NewUser newUser = new NewUser(user.getUsername(),
//                                      user.getEmail(),
//                                      user.getPhone(),
//                                      "",
//                                      user.getUserType());
//
//        Index index = new Index.Builder(newUser)
//                        .index("cmput301f18t24test")
//                        .type("users")
//                        .build();
//
//        try {
//            DocumentResult result = client.execute(index);
//            Log.d("Vishal", "addUser: " + result.isSucceeded() + " " + index.getId());
//            if (result.isSucceeded()) {
//                // Associate the ID with the original userModel object.
//                user.setUserID(result.getId());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Gets all problems for a user. Note: It does not initialize the records for each problem.
     * They need to be grabbed separately.
     * @param userID The userID of the user to pull all problems.
     * @return All problems for the given userID.
     */
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

    public static void addProblem(UserModel user, ProblemModel problem) {
        setClient();

//        UserProfileController profileController = UserProfileController.getInstance();
//
//        // Create a data class to save to elastic search. This lets us avoid saving the extra
//        // information contained in the problemModel.
//        NewProblem newProblem = new NewProblem(problem.getTitle(),
//                                               problem.getDescription(),
//                                               problem.getDateStarted(),
//                                               user.getUserID());
//
//        Index index = new Index.Builder(newProblem)
//                .index("cmput301f18t24test")
//                .type("problems")
//                .build();
//
//        try {
//            DocumentResult result = client.execute(index);
//            if (result.isSucceeded()) {
//                // Associate the ID with the original userModel object.
//                problem.setProblemID(result.getId());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static List<RecordModel> getRecords(String problemID) {
        setClient();

        String query = "{ \"query\" : \n" +
                       "{ \"match\" :\n" +
                       "{ \"problemID\" : \"" + problemID + "\" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("records")
                .build();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<RecordModel> recordList;
                recordList = result.getSourceAsObjectList(RecordModel.class);
                return recordList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<RecordModel>();
    }

    public static List<Patient> getAssignedPatients(String username) {
        // TODO: implement me
        return new ArrayList<Patient>();
    }

    public static void addRecords(UserModel user, ProblemModel problem) {
        setClient();

        UserProfileController profileController = UserProfileController.getInstance();

        for (RecordModel record : problem.getRecords()) {

            // Create a data class to save to elastic search. This lets us avoid saving the extra
            // information contained in the problemModel.
//            NewRecord newRecord = new NewRecord(record.getTitle(),
//                    record.getDescription(), record.getTimestamp(), record.getComment(),
//                    record.getPhotos(), record.getBodyLocation(), record.getMapLocation(),
//                    problem.getProblemID(), user.getUserID());
//
//            Index index = new Index.Builder(newRecord)
//                    .index("cmput301f18t24test")
//                    .type("records")
//                    .build();

//            try {
//                DocumentResult result = client.execute(index);
//                if (result.isSucceeded()) {
//                    // Associate the ID with the original userModel object.
//                    record.setRecordID(result.getId());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
//                addProblem(problem);
            }

            return null;
        }
    }

    /**
     * Gets the record for each given problemID.
     */
    public static class GetRecordTask extends AsyncTask<String, Void, ArrayList<RecordModel>> {

        protected ArrayList<RecordModel> doInBackground(String... strings) {
            ArrayList<RecordModel> records = new ArrayList<RecordModel>();
            for (String s: strings) {
                records.addAll(getRecords(s));
            }
            return records;
        }

    }

    /**
     * Adds all records for each given problem.
     */
    public static class AddRecordTask extends AsyncTask<ProblemModel, Void, Void> {

        protected Void doInBackground(ProblemModel... params) {
            for (ProblemModel problem : params) {
//                addRecords(problem);
            }

            return null;
        }

    }

}
