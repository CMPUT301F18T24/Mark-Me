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

import com.cybersix.markme.io.GeneralIO;
import com.cybersix.markme.io.OnTaskComplete;
import com.cybersix.markme.model.UserModel;
import com.cybersix.markme.model.UserModel.*;
import com.cybersix.markme.observer.UserObserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class UserProfileController {
    public void setModel(UserModel model) {
        this.model = model;
    }

    private UserModel model = null;
    private GeneralIO io = GeneralIO.getInstance();
    private final String SECURITY_FILE_NAME = "securityToken.txt";

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

    public void findUser(OnTaskComplete handler) {
        io.findUser(model.getUsername(), handler);
    }

    // Attempts to add a model to the elasticsearch database.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the model.
    // Outputs: Returns true if added model was successful, false otherwise.
    // TODO: This should save to the elastic search database.

    public void addUser(OnTaskComplete handler) {
        io.addUser(model, handler);
    }

    public void login(OnTaskComplete handler) {
        io.findUser(model.getUsername(), handler);
    }

    public boolean updateSecurityTokenFile(Context context) {
        try (FileOutputStream output = context.openFileOutput(SECURITY_FILE_NAME, Context.MODE_PRIVATE)) {

            OutputStreamWriter writer = new OutputStreamWriter(output);
            writer.write(model.getUsername());
            writer.close();

            return true; // Everything was successful.

        } catch (FileNotFoundException e) {
            Log.d("UserProfileController: ", "Failed to find file.");
        } catch (IOException e) {
            Log.d("UserProfileController: ", "Failed to write to file.");
        }

        return false;
    }

    // Checks locally for a password file, to see if an account has already been created.
    // TODO: Move password file check to diskIO utils?
    // TODO: Uncouple the login button from, the user observers
    public void userExists(Context context, final OnTaskComplete handler) {
        try (FileInputStream input = context.openFileInput(SECURITY_FILE_NAME)) {

            final InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            input.close();
            final String securityToken = stringBuilder.toString().trim();
            Log.d("UserProfileController: ", securityToken);

            io.findUser(securityToken, new OnTaskComplete() {
                @Override
                public void onTaskComplete(Object result) {
                    ArrayList<UserModel> users = (ArrayList<UserModel>) result;
                    if (users.isEmpty())
                        return;
                    try {
                        model.setUsername(securityToken);
                        handler.onTaskComplete(new Object());
                    } catch (UsernameTooShortException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            Log.d("UserProfileController: ", "Failed to find file.");
        } catch (IOException e) {
            Log.d("UserProfileController: ", "Failed to read file.");
        }
    }

    public String transferAccount(String shortCode) {
        return io.transferUser(shortCode);
    }

    public String generateTransferCode() {
        return io.generateTransferCode(model.getUsername());
    }
}
