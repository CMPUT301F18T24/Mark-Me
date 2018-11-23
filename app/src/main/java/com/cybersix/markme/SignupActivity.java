/**
 * CMPUT 301 Team 24
 *
 * This activity handles registering an account for a user. It validates the user's input before
 * creating a new user profile in the ES database.
 *
 * Todo: The errors returned upon failed registration are not very verbose, it needs more work to
 * make the errors more intuitive.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    UserModel userModel = null;
    UserProfileController userController = null;
    UserObserver userObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        GuiUtils.setFullScreen(this);

        userModel = new Patient();
        userController = new UserProfileController(userModel);
        userObserver = new UserObserver(userController);

        initUI();
    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more complete implementation to attempt robotium intent testing.
    public void initUI() {
        // Get the signup information.
        userObserver.setUsernameView((TextView) findViewById(R.id.fragment_account_settings_usernameText));
        userObserver.setEmailView((TextView) findViewById(R.id.fragment_account_settings_email));
        userObserver.setPhoneView((TextView) findViewById(R.id.fragment_account_settings_phoneText));
        userObserver.setModifierButton(findViewById(R.id.signupButton));
        TextView passwordText = (TextView) findViewById(R.id.passwordText);

        // Add an onClick listener that validates signup information
        userObserver.setOnModifierPressed(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegistration();
            }
        });
    }

    // Checks the info the user provided and creates a new account if the info is valid,
    // or lets the user know if the info is not valid.
    // TODO: I need server integration to test if a user already exists.
    public void checkRegistration() {
        // Create a user of type patient by default.
        if (userController.addUser()) {
            finish();
        } else {
            // Notify the user that registration was unsuccessful.
            // TODO: Can we let the user know exactly what went wrong?
            Toast toast = Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
