/**
 * Jose: this is the activity for the account settings for the user. This is where either the
 *       care provider or the patient can assign their patient/care provider
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class accountSettingsActivity extends AppCompatActivity {
    // NOTE: The change button is only visible to the care provider as they can change which
    //       patient they want to view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
    }
}
