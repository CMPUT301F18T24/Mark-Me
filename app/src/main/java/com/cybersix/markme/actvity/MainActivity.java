/**
 * CMPUT 301 Team 24
 *
 * This activity displays all of the fragments after logging the user on.
 *
 * Version 1.0
 * Date: 2018-11-20
 * Copyright Notice
 * @author Vishal Patel, Rizwan Qureshi, Curtis Goud, Jose Ramirez
 */
package com.cybersix.markme.actvity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.cybersix.markme.io.GeneralIO;
import com.cybersix.markme.io.OnTaskComplete;
import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.R;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    public static String EXTRA_CURRENT_USERNAME = "COM_CYBERSIX_MARKME_CURRENT_USERNAME";
    private NavigationController mNavigationController = null;
    private UserModel mUser = null;
    private DataModel mData = null;
    private GeneralIO mIO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIO = GeneralIO.getInstance();
        mIO.setContext(this);
        mData = DataModel.getInstance();

        Intent intent = getIntent();
        String username = intent.getStringExtra(EXTRA_CURRENT_USERNAME);
        mIO.findUser(username, onFoundUser);

        mNavigationController = NavigationController.getInstance(this);
        mNavigationController.setSelectedItem(R.id.list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GuiUtils.setFullScreen(this);
    }

    public NavigationController getNavigationController() {
        return mNavigationController;
    }

    public UserModel getUser() {
        return mUser;
    }

    public void setUser(UserModel user) {
        mUser = user;
    }

    OnTaskComplete onFoundUser = new OnTaskComplete() {
        @Override
        public void onTaskComplete(Object result) {
            ArrayList<UserModel> users = (ArrayList<UserModel>) result;
            if (!users.isEmpty()) {
                mUser = users.get(0);
                if (mUser.getUserType().equals(Patient.class.getSimpleName())) {
                    mIO.getEverythingForPatient(mUser);
                    refresh();
                }
            }
        }
    };

    public void refresh() {
        mData.setSelectedPatient((Patient) mUser);
    }
}
