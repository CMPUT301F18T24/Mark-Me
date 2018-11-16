package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

public class NavigationBar {
    private FragmentManager mFragmentManager = null;
    private Fragment mFragment = null;
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
                    switchToFragment(BodyFragment.class);
                    return true;
                case R.id.gallery:
                    return true;
                case R.id.list:
                    switchToFragment(ProblemListFragment.class);
                    return true;
            }
            return false;
        }
    };

    public NavigationBar(@NonNull FragmentManager manager, @NonNull BottomNavigationView navigationView) {
        mFragmentManager = manager;
        mNavigationView = navigationView;
        mNavigationView.setOnNavigationItemSelectedListener(mListener);
    }

    public void setSelectedItem(int itemId) {
        mNavigationView.setSelectedItemId(itemId);
    }

    public void switchToFragment(Class<? extends Fragment> clazz) {
        setFragment(clazz);
        if (mFragmentManager.getFragments().size() == 0)
            createFragmentDynamically();
        else
            replaceFragment();
    }

    private void createFragmentDynamically() {
        mFragmentManager
                .beginTransaction()
                .add(R.id.fragment_layout, mFragment)
                .commit();
    }

    private void setFragment(Class<? extends Fragment> clazz) {
        try {
            mFragment = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragment() {
        mFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_layout, mFragment)
                .commit();
    }
}
