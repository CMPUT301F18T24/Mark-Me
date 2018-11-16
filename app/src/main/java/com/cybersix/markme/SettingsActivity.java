package com.cybersix.markme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// This activity will be a place holder for the settings activity that is based from the storyboard
// on the github repo
public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }



    public void changeLanguages(View view) {
        startActivities(new Intent[]{new Intent(SettingsActivity.this, LanguageHandler.class)});



    }

    public void AccountSettings(View view) {
        startActivities(new Intent[]{new Intent(SettingsActivity.this, AccountSettingsActivity.class)});


    }

    public void about(View view) {
        startActivities(new Intent[]{new Intent(SettingsActivity.this, About.class)});


    }
}
