package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login_activity extends AppCompatActivity {

    public static UserProfileController userProfile; // Should this be static?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        userProfile = new UserProfileController();

        initUI();

    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more implementation to attempt robotium intent testing.
    public void initUI() {

        // Add an onClick listener that validates login information
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        // Add an onClick listener that takes the user to the signup Activity.
        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: Fill in the code for opening signup activity.
            }
        });

    }

    // Checks the provided login information against the UserModel.
    // If info is valid, it "logs" the user on.
    // If info is not valid, it displays a error message.
    // TODO: Fill in stub for after login.
    public void checkLogin() {

    }


}
