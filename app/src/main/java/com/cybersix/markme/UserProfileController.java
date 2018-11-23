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
    private UserModel currentUser = null;
    private UserModelIO io = (UserModelIO) ElasticSearchController.getInstance();

    // Is the controller a singleton, or is the model a singleton?
    protected UserProfileController() {
        currentUser = new UserModel();
    }

    // Lazy construction of instance.
    public static UserProfileController getInstance() {
        if (instance == null) {
            instance = new UserProfileController();
        }
        return instance;
    }

    public void modifyModel(UserModel model, UserView view) {
        modifyUsername(model, view);
        modifyEmail(model, view);
        modifyPhone(model, view);
    }

    protected void modifyPhone(UserModel model, UserView view) {
        if (model == null || view == null || view.getPhoneView() == null)
            return;

        try {
            model.setPhone(view.getPhoneView().getText().toString());
        } catch (InvalidPhoneNumberException e) {
            e.printStackTrace();
        }
    }

    protected void modifyUsername(UserModel model, UserView view) {
        if (model == null || view == null || view.getUsernameView() == null)
            return;

        try {
            model.setUsername(view.getUsernameView().getText().toString());
        } catch (UsernameTooShortException e) {
            e.printStackTrace();
        }
    }

    protected void modifyEmail(UserModel model, UserView view) {
        if (model == null || view == null || view.getEmailView() == null)
            return;

        try {
            model.setEmail(view.getEmailView().getText().toString());
        } catch (InvalidEmailAddressException e) {
            e.printStackTrace();
        }
    }

    public void updateRemoteModel(UserModel model) {
        // TODO: Update the model information on elastic search
    }

    // Attempts to add a user to the elasticsearch database.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the user.
    // Outputs: Returns true if added user was successful, false otherwise.
    // TODO: This should save to the elastic search database.
    public boolean addUser(UserModel newUser) {
        return io.addUser(newUser);
    }

    public UserModel findUser(String username) {
        return io.findUser(username);
    }
}
