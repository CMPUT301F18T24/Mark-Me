package com.cybersix.markme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        initUI();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more complete implementation to attempt robotium intent testing.
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
                openSignupActivity();
            }
        });

        // Debug purposes:
        Button debug = (Button) findViewById(R.id.debug);
        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccountSettingsActivity();
            }
        });

    }

    // DEBUG ONLY - launches the openAccountSettingsActivity.
    public void openAccountSettingsActivity() {
        Intent intent = new Intent(this, AccountSettingsActivity.class);
        startActivity(intent);
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
    // TODO: Fill in stub for after login.
    public void checkLogin() {

        UserProfileController profileController = UserProfileController.getInstance();
        TextView userText = (TextView) findViewById(R.id.usernameText);
        TextView passText = (TextView) findViewById(R.id.passwordText);

         if (profileController.isUserValid(userText.getText().toString(),
                                               passText.getText().toString())) {
             Log.d("Vishal_Login_Activity", "Successful Login.");
             // Put code here for after login...
         } else {

             // Clear password box.
             passText.setText("");

             // Notify user that login failed.
             Toast toast = Toast.makeText(this, "Invalid login information!", Toast.LENGTH_SHORT);
             toast.show();

         }
    }


}
