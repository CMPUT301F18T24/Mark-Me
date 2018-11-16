package com.cybersix.markme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class NavigationBar {
    private Activity mActivity = null;
    private BottomNavigationView mNavigationView = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    return true;
                case R.id.gps:
                    return true;
                case R.id.body:
                    switchToActivity(BodyActivity.class);
                    return true;
                case R.id.gallery:
                    return true;
                case R.id.list:
                    return true;
            }
            return false;
        }
    };

    public NavigationBar(@NonNull Activity activity, @NonNull BottomNavigationView navigationView) {
        mActivity = activity;
        mNavigationView = navigationView;
        mNavigationView.setOnNavigationItemSelectedListener(mListener);
    }

    public void setSelectedItem(int itemId) {
        mNavigationView.setSelectedItemId(itemId);
    }

    private void switchToActivity(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
