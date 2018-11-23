/**
 * CMPUT 301 Team 24
 *
 * This controller handles creating and editing the UserModel.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme;

import android.util.Log;

import java.util.ArrayList;

public class UserProfileController {
    private static UserProfileController instance = null;
    private UserModel user;

    // Is the controller a singleton, or is the model a singleton?
    protected UserProfileController() {
        user = new UserModel();
    }

    // Lazy construction of instance.
    public static UserProfileController getInstance() {
        if (instance == null) {
            instance = new UserProfileController();
        }
        return instance;
    }

    // Attempts to change a user's contact information.
    // Inputs: email, phone - Contact information
    // Outputs: Returns true if contact information was successfully changed, false otherwise.
    public void editContactInformation(String email, String phone) {

    }

    // Attempts to set the user in the UserModel.
    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return this.user;
    }

    // Attempts to add a user to the elasticsearch database.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the user.
    // Outputs: Returns true if added user was successful, false otherwise.
    // TODO: This should save to the elastic search database.
    public Boolean addUser(UserModel newUser) {

        // Check if the user exists.
        ArrayList<UserModel> foundUsers = new ArrayList<UserModel>();
        try {
            foundUsers = new ElasticSearchIOController.GetUserTask().execute(newUser.getUsername()).get();
            Log.d("Vishal_ProfileCont", Integer.toString(foundUsers.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If username exists, then send a fail.
        if (foundUsers.size() > 0) {
            return false;
        }

        try {
            new ElasticSearchIOController.AddUserTask().execute(newUser);
            return true;
        } catch (Exception e) { // TODO: Can we handle specific exceptions?
            Log.d("Vishal_ProfileCont", e.toString());
            return false;
        }

    }

}
