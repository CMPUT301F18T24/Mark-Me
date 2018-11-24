/**
 * This file contains the NavigationController.
 *
 * Its primary purpose is to allow easier navigation between the different
 * fragments within the app. Currently the controller guarantees that only "main" navigation
 * can exist at a time. Thus, the controller gives a mini API for easing the switching
 * between fragments.
 *
 * Since it is closely related to the bottom navigation view, the controller also
 * implements the logic behind the view's on click selection scheme.
 */

package com.cybersix.markme;

import android.os.Bundle;
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

    private final BottomNavigationView.OnNavigationItemSelectedListener mListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    switchToFragment(SettingsFragment.class);
                    return true;
                case R.id.gps:
                    switchToFragment(MapFragment.class);
                    return true;
                case R.id.body:
                    switchToFragment(BodyFragment.class);
                    return true;
                case R.id.gallery:
                    switchToFragment(FullGalleryFragment.class);
                    return true;
                case R.id.list:
                    switchToListFragment(ProblemModel.class);
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

    /**
     * Used to create the first, and only instance of the NavigationController.
     * It can only be instantiated by the MainActivity since this is the only activity
     * which consists of a Navigation Bar.
     * @param activity
     * @return the singleton instance
     */
    public static NavigationController getInstance(MainActivity activity) {
        if (mNavigationController == null)
            mNavigationController = new NavigationController(
                    activity.getSupportFragmentManager(),
                    (BottomNavigationView) activity.findViewById(R.id.navigation)
            );

        return mNavigationController;
    }

    /**
     * @return the singleton instance
     */
    public static NavigationController getInstance() {
        return mNavigationController;
    }

    /**
     * Progmatically selects an item on the navigation bar with the given resource Id.
     * @param itemId
     */
    public void setSelectedItem(int itemId) {
        mNavigationView.setSelectedItemId(itemId);
    }

    /**
     * Progmatically selects an item on the navigation bar with the given resource Id.
     * Once an item is selected, the given bundle is sent to the Fragment corresponding
     * to that item.
     * @param itemId
     * @param bundle
     */
    public void setSelectedItem(int itemId, Bundle bundle) {
        setSelectedItem(itemId);
        mFragment.setArguments(bundle);
    }

    /**
     * Switches the current fragment, if it exists, with a new fragment.
     * If a fragment does not already exist, it is created dynamically.
     *
     * NOTE: We try to ensure that only one fragment can exist at a time via
     * this controller.
     * @param clazz
     */
    public void switchToFragment(Class<? extends Fragment> clazz) {
        setFragment(clazz);
        if (mFragmentManager.getFragments().size() == 0)
            createFragmentDynamically();
        else
            replaceFragment();
    }

    /**
     * Sends the given bundle to the newly switched Fragment.
     * @param clazz
     * @param bundle
     */
    public void switchToFragment(Class<? extends Fragment> clazz, Bundle bundle) {
        switchToFragment(clazz);
        mFragment.setArguments(bundle);
    }

    public <T extends ListItemModel> void switchToListFragment (Class<T> clazz) {
        switchToListFragment(clazz, new Bundle());
    }

    public <T extends ListItemModel> void switchToListFragment (Class<T> clazz, Bundle bundle) {
        bundle.putSerializable(ListFragment.EXTRA_CLASS, clazz);
        mFragment = new ListFragment<T>();
        if (mFragmentManager.getFragments().size() == 0)
            createFragmentDynamically();
        else
            replaceFragment();
        mFragment.setArguments(bundle);
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

    public void setFragment(@NonNull Fragment fragment) {
        mFragment = fragment;
        replaceFragment();
    }

    private void replaceFragment() {
        mFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_layout, mFragment)
                .commit();
    }

    public Fragment getFragment(){
        return mFragment;
    }
}
