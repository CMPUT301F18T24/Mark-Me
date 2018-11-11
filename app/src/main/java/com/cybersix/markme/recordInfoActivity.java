/**
 * Jose: This is the main activity that will display all of the records' information that has
 *       been selected by the user
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class recordInfoActivity extends AppCompatActivity {

    // record activity will be linked with the photo gallery and being able to add photos, but the
    // user should be able to view all of the information

    // NOTE: the "feebackButton" view is only visible to the care provider. Make sure there is
    //       a case for checking who the user is
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_info);
    }
}
