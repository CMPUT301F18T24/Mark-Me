package com.cybersix.markme;

import android.os.Bundle;
import android.widget.ArrayAdapter;

public class PatientListFragment extends ListFragment {
    private CareProvider mCareProvider = null;
    private ArrayAdapter<Patient> mArrayAdapter = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUI();

//        Intent intent = getIntent();
//        final String userid = intent.getStringExtra(UserModel.USERID);
//        try {
//            mCareProvider = new CareProvider(userid, CloudUtils.getAssignedPatients(userid));
//            mArrayAdapter = new ArrayAdapter<Patient>(this, R.layout.list_item, mCareProvider.getPatients());
//            listView().setAdapter(mArrayAdapter);
//        } catch (UserIDTooShortException e) {
//            e.printStackTrace();
//        }
    }

    protected void setupUI() {
        title().setText("My Patients");
        details().setText("A list or your assigned patients.");
    }

}
