/**
 * CMPUT 301 Team 24
 *
 * This is the patient list fragment. The user (of type "Care Provider") will be able to view a
 * list of all of their assigned patients and select one of them to view all of their information
 *
 * Version 0.1
 *
 * Date: 2018-11-11
 *
 * Copyright Notice
 * @author Rizwan Qureshi? TODO: May need to change!!
 * @see com.cybersix.markme.model.Patient
 * @see com.cybersix.markme.fragment.UserAssignmentFragment
 */
package com.cybersix.markme.fragment;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.cybersix.markme.fragment.ListFragment;
import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.Patient;

public class PatientListFragment extends ListFragment {
    private CareProvider mCareProvider = null;
    private ArrayAdapter<Patient> mArrayAdapter = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUI();

//        mCareProvider = (CareProvider) ((MainActivity) getActivity()).getUser();
//        Log.d("CareProvider", mCareProvider.getPatients().toString());
//        mArrayAdapter = new ArrayAdapter<Patient>(getActivity(), R.layout.list_item, mCareProvider.getPatients());
//        getListView().setAdapter(mArrayAdapter);
    }

    protected void setupUI() {
//        getTitle().setText("My Patients");
//        getDetails().setText("A list or your assigned patients.");
    }

}
