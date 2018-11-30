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
package com.cybersix.markme.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.io.UserModelIO;
import com.cybersix.markme.model.UserModel;
import com.cybersix.markme.model.UserModel.*;
import com.cybersix.markme.observer.UserObserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class UserProfileController {
    private UserModel model = null;
    private UserModelIO io = (UserModelIO) ElasticSearchIO.getInstance();

    public UserProfileController(@NonNull UserModel user) {
        this.model = user;
    }

    public void modifyModel(UserObserver view) {
        modifyUsername(view);
        modifyEmail(view);
        modifyPhone(view);
    }

    protected void modifyPhone(UserObserver view) {
        if (model == null || view == null || view.getPhoneView() == null)
            return;

        try {
            model.setPhone(view.getPhoneView().getText().toString());
        } catch (InvalidPhoneNumberException e) {
            e.printStackTrace();
        }
    }

    protected void modifyUsername(UserObserver view) {
        if (model == null || view == null || view.getUsernameView() == null)
            return;

        try {
            model.setUsername(view.getUsernameView().getText().toString());
        } catch (UsernameTooShortException e) {
            e.printStackTrace();
        }
    }

    protected void modifyEmail(UserObserver view) {
        if (model == null || view == null || view.getEmailView() == null)
            return;

        try {
            model.setEmail(view.getEmailView().getText().toString());
        } catch (InvalidEmailAddressException e) {
            e.printStackTrace();
        }
    }

    public void updateRemoteModel() {
        // TODO: Update the model information on elastic search
    }

    // Attempts to add a model to the elasticsearch database.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the model.
    // Outputs: Returns true if added model was successful, false otherwise.
    // TODO: This should save to the elastic search database.
    public boolean addUser() {
        return io.addUser(model);
    }

    // Checks locally for a password file, to see if an account has already been created.
    // TODO: Move password file check to diskIO utils?
    public boolean userExists(Context context) {

        String filename = context.getApplicationContext().getFilesDir() + "/security_token.txt";
        try (FileInputStream fis = context.openFileInput(filename)) {

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            isr.close();
            String securityToken = sb.toString();
            return io.findUser(securityToken) != null;

        } catch (FileNotFoundException e) {
            Log.d("UserProfileController: ", "Failed to find file.");
        } catch (IOException e) {
            Log.d("UserProfileController: ", "Failed to read file.");
        }

        return false;

    }
}
