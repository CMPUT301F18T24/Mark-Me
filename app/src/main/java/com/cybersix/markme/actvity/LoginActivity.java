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

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.R;
import com.cybersix.markme.observer.UserObserver;
import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.model.UserModel;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    static final int SIGNUP_REQUEST = 1;
    UserModel userModel = null;
    UserObserver userObserver = null;
    UserProfileController userController = null;

    // Create the Handler object (on the main thread by default)
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GuiUtils.setFullScreen(this);

        userModel = new UserModel();
        userController = new UserProfileController(userModel);
        userObserver = new UserObserver(userController);
        handler = new Handler();

        initUI();
        checkLogin();
    }

    // Credit to: Android developers, Intent results.
    // https://developer.android.com/training/basics/intents/result#java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SIGNUP_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                // Set the username
                userController.setUsername(data.getStringExtra(SignupActivity.EXTRA_USERNAME));

                // Display the spinner
                // And the offline button
                TextView loginAssurance = (TextView) findViewById(R.id.activity_login_assurance);
                loginAssurance.setVisibility(View.VISIBLE);

                Button signInOfflineButton = (Button) findViewById(R.id.signinAnwaysButton);
                signInOfflineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchMain();
                    }
                });
                signInOfflineButton.setVisibility(View.VISIBLE);

                // Start the initial runnable task by posting through the handler
                handler.post(runnableCode);
            }
        }
    }

    // Define the code block to be executed
    Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Try to login.
            checkLogin();
            // Do something here on the main thread
            Log.d("Handlers", "Called on main thread");
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, 2000);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    // Initializes onClick listeners for UI elements.
    public void initUI() {
        userObserver.setUsernameView( (TextView) findViewById(R.id.fragment_account_settings_usernameText));
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
        startActivityForResult(intent, SIGNUP_REQUEST);

        // Immediately try to login. There is a race condition with a slow server. Implement
        // fix that queries the server until the account is setup.
        // checkLogin();
    }

    // Checks the provided login information against the UserModel.
    // If info is valid, it "logs" the user on.
    // If info is not valid, it displays a error message.
    // Assumes that the user model has been loaded with user info.
    // Inputs: Reads the userText and passText.
    public void checkLogin() {

        // If we got exactly one username returned.
        if (userController.userExists(this.getApplicationContext())) {
            launchMain();
        }

    }

    public void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, userModel.getUsername());
        startActivity(intent);
        finish();
    }

}
