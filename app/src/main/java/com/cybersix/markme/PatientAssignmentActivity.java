package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// This activity will be visible when the user goes to the settings menu and selects "Account
// settings"
public class PatientAssignmentActivity extends AppCompatActivity {
    // will need to set a list adapter to the view to be notified if any of the models have been
    // updated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_assignment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // the list view will show all of the registered care providers from within the database
        // getCareProviders();
        // displayCareProvides();
        // TODO: Make sure to check the previous assignment for reference into how to use the
        //      list adapter
    }
}
