/**
 * CMPUT 301 Team 24
 *
 * This activity handles validating a user's credentials by querying the ES database and ensuring
 * the username/password are correct.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme.actvity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cybersix.markme.io.GeneralIO;
import com.cybersix.markme.io.OnTaskComplete;
import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.R;
import com.cybersix.markme.observer.UserObserver;
import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    UserModel userModel = null;
    UserObserver userObserver = null;
    UserProfileController userController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GuiUtils.setFullScreen(this);

        userModel = new UserModel();
        userController = new UserProfileController(userModel);
        userObserver = new UserObserver(userController);
        GeneralIO.getInstance().setContext(this);

        initUI();
    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more complete implementation to attempt robotium intent testing.
    public void initUI() {
        userObserver.setUsernameView( (TextView) findViewById(R.id.fragment_account_settings_usernameText) );
        userObserver.setModifierButton(findViewById(R.id.loginButton));
        // Add an onClick listener that validates login information
        userObserver.setOnModifierPressed(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.login(new OnTaskComplete() {
                    @Override
                    public void onTaskComplete(Object result) {
                        ArrayList<UserModel> users = (ArrayList<UserModel>) result;
                        if (!users.isEmpty())
                            onLogin();
                    }
                });
            }
        });

        userModel.addObserver(userObserver);
        // Add an onClick listener that takes the user to the signup Activity.
        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupActivity();
            }
        });
    }

    // Launches the signup activity.
    public void openSignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    // Checks the provided login information against the UserModel.
    // If info is valid, it "logs" the user on.
    // If info is not valid, it displays a error message.
    // Assumes that the user model has been loaded with user info.
    // Inputs: Reads the userText and passText.
    public void onLogin() {
        // If we got exactly one username returned.
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, userModel.getUsername());
        startActivity(intent);
        finish();
    }

}
