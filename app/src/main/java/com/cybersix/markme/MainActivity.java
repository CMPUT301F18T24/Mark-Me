package com.cybersix.markme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this,UserAssignmentActivity.class);
        this.startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // send the intent to the problem list view
//        Intent intent = new Intent(this, ProblemListActivity.class);
//        startActivity(intent);
    }
}
