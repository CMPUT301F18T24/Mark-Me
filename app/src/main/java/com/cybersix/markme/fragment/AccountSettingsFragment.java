/**
 * CMPUT 301 Team 24
 *
 * This activity allows users to change their user profile information.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.R;
import com.cybersix.markme.observer.UserObserver;
import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.model.UserModel;

public class AccountSettingsFragment extends Fragment {
    UserModel userModel = null;
    UserObserver userObserver = null;
    UserProfileController userController = null;

    @Override
    public void onCreate(@NonNull Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_account_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Add the AccoutSettingsActivity view as an observer to the UserModel.
        userModel = ((MainActivity) getActivity()).getUser();
        userController = new UserProfileController(userModel);
        userObserver = new UserObserver(userController);

        initUI();
    }

    public void initUI() {
        // Fill the textboxes with information
        userObserver.setUsernameView((TextView) getActivity().findViewById(R.id.fragment_account_settings_usernameText));
        userObserver.setEmailView((TextView) getActivity().findViewById(R.id.fragment_account_settings_email));
        userObserver.setPhoneView((TextView) getActivity().findViewById(R.id.fragment_account_settings_phoneText));
        userObserver.setModifierButton(getActivity().findViewById(R.id.fragment_account_settings_saveButton));
        userModel.addView(userObserver);

        // Disable the contact information textboxs.
        userObserver.getEmailView().setEnabled(false);
        userObserver.getPhoneView().setEnabled(false);

        // Add an onClick for the edit contact information button
        Button editContactsButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_editContactButton);
        editContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableChangingContactInformation();
            }
        });

        userObserver.getModifierButton().setVisibility(View.GONE);
        // Add an onClick for the save button
        userObserver.setOnModifierPressed(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewContactInformation();
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText(R.string.About_mark_me);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });
    }

    // Try to save the new contact information provided by the user.
    public void saveNewContactInformation() {
        // Disable the contact info text fields again.
        userObserver.getEmailView().setEnabled(false);
        userObserver.getPhoneView().setEnabled(false);

        // Hide the save button
        Button saveButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_saveButton);
        saveButton.setVisibility(View.GONE);

        // And show the edit contact info button.
        Button editContactsButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_editContactButton);
        editContactsButton.setVisibility(View.VISIBLE);
    }

    // Enable changing the contact info text boxes, show the save button and hide the edit
    // contact information button.
    public void enableChangingContactInformation() {
        userObserver.getEmailView().setEnabled(true);
        userObserver.getPhoneView().setEnabled(true);

        // Show the save button
        Button saveButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_saveButton);
        saveButton.setVisibility(View.VISIBLE);

        // And hide the edit contact info button.
        Button editContactsButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_editContactButton);
        editContactsButton.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userModel.deleteView(userObserver);

        userController = null;
        userModel = null;
        userObserver = null;
    }
}
