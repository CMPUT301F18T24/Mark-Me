package com.cybersix.markme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientListActivity extends ListActivity {
    private CareProvider mCareProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();

        Intent intent = getIntent();
        final String userid = intent.getStringExtra(UserModel.USERID);
        mCareProvider = new CareProvider(userid);

        
    }

    protected void setupUI() {
        title().setText("My Patients");
        details().setText("A list or your assigned patients.");
    }

}
