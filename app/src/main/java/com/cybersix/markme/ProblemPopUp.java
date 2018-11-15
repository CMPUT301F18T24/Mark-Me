/**
 * Jose: This will be the popup for the problem while we are viewing the individual records
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProblemPopUp extends AppCompatActivity {

    // the problem pop-up will display the problem information related by the user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_pop_up);
    }
}
