package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

public class NavigationController {
    private FragmentManager mFragmentManager = null;
    private Fragment mFragment = null;
    private BottomNavigationView mNavigationView = null;
    private static NavigationController mNavigationController = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    switchToFragment(SettingsFragment.class);
                    return true;
                case R.id.gps:
                    return true;
                case R.id.body:
                    switchToFragment(BodyFragment.class);
                    return true;
                case R.id.gallery:
                    switchToFragment(GalleryFragment.class);
                    return true;
                case R.id.list:
                    switchToFragment(ProblemListFragment.class);
                    return true;
            }
            return false;
        }
    };

    private NavigationController(@NonNull FragmentManager manager, @NonNull BottomNavigationView navigationView) {
        mFragmentManager = manager;
        mNavigationView = navigationView;
        mNavigationView.setOnNavigationItemSelectedListener(mListener);
    }

    public static NavigationController getInstance(MainActivity activity) {
        if (mNavigationController == null)
            mNavigationController = new NavigationController(
                    activity.getSupportFragmentManager(),
                    (BottomNavigationView) activity.findViewById(R.id.navigation)
            );

        return mNavigationController;
    }

    public static NavigationController getInstance() {
        return mNavigationController;
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
