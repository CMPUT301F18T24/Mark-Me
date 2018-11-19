package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        GuiUtils.setFullScreen(this);
        initUI();

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

        UserProfileController profileController = UserProfileController.getInstance();
        TextView userText = (TextView) findViewById(R.id.usernameText);
        TextView passText = (TextView) findViewById(R.id.passwordText);
        ArrayList<UserModel> foundUsers = new ArrayList<UserModel>();

        // Search elasticsearch database for the username.
        try {
            foundUsers = new ElasticSearchIOController.GetUserTask()
                    .execute(userText.getText().toString()).get();
            Log.d("Vishal_Login_Activity", Integer.toString(foundUsers.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If we got exactly one username returned.
        if (foundUsers.size() == 1) {

            // Check if the password match.
            if (foundUsers.get(0).getPassword().compareTo(passText.getText().toString()) == 0) {

                // Tell the controller to update the usermodel.
                profileController.setUser(foundUsers.get(0).getUserID(),
                                          foundUsers.get(0).getUsername(),
                                          foundUsers.get(0).getEmail(),
                                          foundUsers.get(0).getPhone(),
                                          foundUsers.get(0).getPassword(),
                                          foundUsers.get(0).getUserType());

                Log.d("Vishal_Login_Activity", "Successful Login.");

                // Launch the next activity depending on whether the user is a patient or care provider.
                if (profileController.user.getUserType().compareTo("patient") == 0) {
                    Intent intent = new Intent(this, ProblemListActivity.class);
                    startActivity(intent);
                } else if (profileController.user.getUserType().compareTo("care_provider") == 0) {
                    // Stub: Launch the patient list activity.
                }

            } else {
                // Clear password box.
                passText.setText("");

                // Notify user that login failed.
                Toast toast = Toast.makeText(this, "Invalid login information!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {

            // Clear password box.
            passText.setText("");

            // Notify user that login failed.
            Toast toast = Toast.makeText(this, "Invalid login information!", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

}
