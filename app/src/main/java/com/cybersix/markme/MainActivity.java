/**
 * CMPUT 301 Team 24
 *
 * 
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {
    private NavigationController mNavigationController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationController = NavigationController.getInstance(this);
        mNavigationController.setSelectedItem(R.id.body);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GuiUtils.setFullScreen(this);
    }
}
