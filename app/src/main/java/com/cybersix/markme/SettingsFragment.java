package com.cybersix.markme;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// This activity will be a place holder for the settings activity that is based from the storyboard
// on the github repo
public class SettingsFragment extends Fragment {
    private View mLanguageButton = null;
    private View mAccountButton = null;
    private View mAboutButton = null;
    private View mAssignmentButton = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLanguageButton = getActivity().findViewById(R.id.language);
        mAccountButton = getActivity().findViewById(R.id.Account);
        mAboutButton = getActivity().findViewById(R.id.About);
        mAssignmentButton = getActivity().findViewById(R.id.userAssignmentButton);

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText("Settings");
        returnButton.setVisibility(View.GONE);

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

        mAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserAssignment();
            }
        });
    }

    protected void openChangeLanguages() {
        NavigationController.getInstance().switchToFragment(LanguageHandlerFragment.class);
    }

    protected void openAccountSettings() {
        NavigationController.getInstance().switchToFragment(AccountSettingsFragment.class);
    }

    protected void openUserAssignment() {
        NavigationController.getInstance().switchToFragment(UserAssignmentFragment.class);
    }

    protected void openAbout() {
        NavigationController.getInstance().switchToFragment(AboutFragment.class);
    }
}
