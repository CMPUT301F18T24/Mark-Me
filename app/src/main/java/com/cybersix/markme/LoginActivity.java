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
package com.cybersix.markme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    UserModel userModel = new UserModel();
    UserView userView = new UserView();
    UserProfileController userController = UserProfileController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GuiUtils.setFullScreen(this);
        initUI();

        userView.setUsernameView( (TextView) findViewById(R.id.fragment_account_settings_usernameText) );
        userModel.addObserver(userView);
    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more complete implementation to attempt robotium intent testing.
    public void initUI() {
        // Add an onClick listener that validates login information
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.modifyModel(userModel, userView);
                checkLogin();
            }
        });

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
    public void checkLogin() {
        UserModel foundUser = userController.findUser(userModel.getUsername());
        // If we got exactly one username returned.
        if (foundUser != null) {
            // Tell the controller to update the usermodel.
            Log.d("Vishal_Login_Activity", "Successful Login.");
            // Launch the next activity depending on whether the user is a patient or care provider.
            if (foundUser.getUserType().compareTo(Patient.class.getSimpleName()) == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (foundUser.getUserType().compareTo(CareProvider.class.getSimpleName()) == 0) {
                // Stub: Launch the patient list activity.
            }
        }
    }

}
