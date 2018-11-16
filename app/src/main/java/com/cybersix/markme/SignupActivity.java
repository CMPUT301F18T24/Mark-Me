package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        GuiUtils.setFullScreen(this);
        initUI();

    }

    // Initializes onClick listeners for UI elements.
    // TODO: Need a more complete implementation to attempt robotium intent testing.
    public void initUI() {

        // Add an onClick listener that validates signup information
        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
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

        // Get the ccntroller.
        UserProfileController profileController = UserProfileController.getInstance();

        // Get the signup information.
        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        TextView emailText = (TextView) findViewById(R.id.emailText);
        TextView phoneText = (TextView) findViewById(R.id.phoneText);
        TextView passwordText = (TextView) findViewById(R.id.passwordText);

        // Create a user of type patient by default.
        if (profileController.addUser(usernameText.getText().toString(),
                                      emailText.getText().toString(),
                                      phoneText.getText().toString(),
                                      passwordText.getText().toString(), "patient")) {

            // If successful, return to the signup activity.
            this.finish();
        } else {
            // Notify the user that registration was unsuccessful.
            // TODO: Can we let the user know exactly what went wrong?
            Toast toast = Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
