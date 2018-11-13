package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        initUI();
    }

    public void initUI() {

        UserProfileController profileController = UserProfileController.getInstance();

        // Initialize the fields with the user's information
        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        TextView emailText = (TextView) findViewById(R.id.emailText);
        TextView phoneText = (TextView) findViewById(R.id.phoneText);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        // Fill the textboxes with information
        // TODO: These extra calls to the controller could be avoided if model was its own object.
        usernameText.setText(profileController.user.getUserID());
        emailText.setText(profileController.user.getEmail());
        phoneText.setText(profileController.user.getPhone());

        // Disable the contact information textboxs.
        emailText.setEnabled(false);
        phoneText.setEnabled(false);
        saveButton.setVisibility(View.GONE);

        // Add an onClick for the edit contact information button
        Button editContactsButton = (Button) findViewById(R.id.editContactButton);
        editContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableChangingContactInformation();
            }
        });

        // Add an onClick for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewContactInformation();
            }
        });

    }

    // Try to save the new contact information provided by the user.
    public void saveNewContactInformation() {


        // TODO: Set changed?
    }

    // Enable changing the contact info text boxes, show the save button and hide the edit
    // contact information button.
    public void enableChangingContactInformation() {

        // Enable changing the contact information.
        TextView emailText = (TextView) findViewById(R.id.emailText);
        TextView phoneText = (TextView) findViewById(R.id.phoneText);
        emailText.setEnabled(true);
        phoneText.setEnabled(true);

        // Show the save button
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setVisibility(View.VISIBLE);

        // And hide the edit contact info button.
        Button editContactsButton = (Button) findViewById(R.id.editContactButton);
        editContactsButton.setVisibility(View.GONE);

    }

}
