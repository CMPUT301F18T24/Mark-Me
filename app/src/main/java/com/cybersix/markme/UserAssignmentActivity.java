/**
 * Patient assignment activity is to be a separate activity entirely under the settings activity.
 *
 * Version 0.1
 *
 * Date: 2018-11-18
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// This activity will be visible when the user goes to the settings menu and inside "Account
// settings" click "ADD"
public class UserAssignmentActivity extends AppCompatActivity {
    // will need to set a list adapter to the view to be notified if any of the models have been
    // updated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_assignment);

        // set up all of the buttons that are used within this activity
    }

    @Override
    protected void onStart() {
        super.onStart();
        // the list view will show all of the registered care providers from within the database
        // getCareProviders();
        // displayCareProvides();
        // TODO: Make sure to check the previous assignment for reference into how to use the list adapter
        // The list will have a "Select is on" mode where the user can select one of the patient
        // items
    }
}
