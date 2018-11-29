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

import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserFactory;
import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.R;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.UserModel;

public class MainActivity extends FragmentActivity {
    public static String EXTRA_CURRENT_USERNAME = "COM_CYBERSIX_MARKME_CURRENT_USERNAME";
    private NavigationController mNavigationController = null;
    private UserModel mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra(EXTRA_CURRENT_USERNAME);
        mUser = ElasticSearchIO.getInstance().findUser(username);

        try {
            mUser = UserFactory.inflateUser(mUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNavigationController = NavigationController.getInstance(this);
        mNavigationController.setSelectedItem(R.id.body);
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
}
