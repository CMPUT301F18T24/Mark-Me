package com.cybersix.markme;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {
    private NavigationController mNavigationController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GuiUtils.setFullScreen(this);

        mNavigationController = NavigationController.getInstance(this);
        mNavigationController.setSelectedItem(R.id.body);
    }
}
