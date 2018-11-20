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

    NavigationController getNavigationController() {
        return mNavigationController;
    }
}
