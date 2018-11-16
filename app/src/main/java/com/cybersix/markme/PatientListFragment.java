package com.cybersix.markme;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class PatientListFragment extends ListFragment {
    private CareProvider mCareProvider = null;
    private ArrayAdapter<Patient> mArrayAdapter = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUI();

        String userid = UserProfileController.getInstance().user.getUserID();
        try {
            mCareProvider = new CareProvider(userid, CloudUtils.getAssignedPatients(userid));
            Log.d("CareProvider", mCareProvider.getPatients().toString());
            mArrayAdapter = new ArrayAdapter<Patient>(getActivity(), R.layout.list_item, mCareProvider.getPatients());
            getListView().setAdapter(mArrayAdapter);
        } catch (UserIDTooShortException e) {
            e.printStackTrace();
        }
    }

    protected void setupUI() {
        getTitle().setText("My Patients");
        getDetails().setText("A list or your assigned patients.");
    }

}
