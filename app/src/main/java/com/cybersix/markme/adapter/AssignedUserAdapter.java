/**
 * CMPUT 301 Team 24
 *
 * This is the data adapter for the user assignment data. This will format the sent and recieved
 * data information for easier readability and for easier uploading.
 *
 * Version 0.1
 *
 * Date: 2018-12-01
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.fragment.UserAssignmentFragment
 */
package com.cybersix.markme.adapter;

import android.util.Log;
import android.util.Pair;

import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import java.util.ArrayList;
import java.util.Date;

public class AssignedUserAdapter {
    private String patientID = null;
    private String providerID = null;

    public AssignedUserAdapter(String patientUserID, String careProviderID) {
        // Add the care provider username and the patient username
        patientID = patientUserID;
        providerID = careProviderID;
    }

    public Pair<String, String> get() {
        return new Pair<String, String>(patientID, providerID);
    }
}
