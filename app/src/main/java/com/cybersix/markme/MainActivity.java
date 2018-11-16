package com.cybersix.markme;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {
    private NavigationBar mNavigationBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GuiUtils.setFullScreen(this);

        mNavigationBar = new NavigationBar(getSupportFragmentManager(), (BottomNavigationView) findViewById(R.id.navigation));
        mNavigationBar.setSelectedItem(R.id.body);
    }

    public NavigationBar getNavigationBar() {
        return mNavigationBar;
    }
}
