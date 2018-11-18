package com.cybersix.markme;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// This activity will be a place holder for the settings activity that is based from the storyboard
// on the github repo
public class SettingsFragment extends Fragment {
    private View mLanguageButton = null;
    private View mAccountButton = null;
    private View mAboutButton = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLanguageButton = getActivity().findViewById(R.id.language);
        mAccountButton = getActivity().findViewById(R.id.Account);
        mAboutButton = getActivity().findViewById(R.id.About);

        mLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeLanguages();
            }
        });

        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccountSettings();
            }
        });

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAbout();
            }
        });
    }

    public void openChangeLanguages() {
        NavigationController.getInstance().switchToFragment(LanguageHandlerFragment.class);
    }

    public void openAccountSettings() {
        NavigationController.getInstance().switchToFragment(AccountSettingsFragment.class);
    }

    public void openAbout() {
        NavigationController.getInstance().switchToFragment(AboutFragment.class);
    }
}
