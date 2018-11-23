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
package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class AccountSettingsFragment extends Fragment {
    UserModel userModel = null;
    UserView userView = null;
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
        userController = UserProfileController.getInstance();
        userModel = ((MainActivity) getActivity()).getUser();
        userView = new UserView();

        initUI();
    }

    public void initUI() {
        // Fill the textboxes with information
        // TODO: These extra calls to the controller could be avoided if model was its own object.
        userView.setUsernameView((TextView) getActivity().findViewById(R.id.fragment_account_settings_usernameText));
        userView.setEmailView((TextView) getActivity().findViewById(R.id.fragment_account_settings_email));
        userView.setPhoneView((TextView) getActivity().findViewById(R.id.fragment_account_settings_phoneText));
        userModel.addObserver(userView);
        Log.v("Rizwan", userView.getEmailView().getText().toString());

        // Disable the contact information textboxs.
        userView.getEmailView().setEnabled(false);
        userView.getPhoneView().setEnabled(false);

        // Add an onClick for the edit contact information button
        Button editContactsButton = (Button) getActivity().findViewById(R.id.fragment_account_settings_editContactButton);
        editContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableChangingContactInformation();
            }
        });

        Button saveButton = getActivity().findViewById(R.id.fragment_account_settings_saveButton);
        saveButton.setVisibility(View.GONE);
        // Add an onClick for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewContactInformation();
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText("About Mark-Me");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });
    }

    // Try to save the new contact information provided by the user.
    public void saveNewContactInformation() {
        userController.modifyModel(userModel, userView);
        userController.updateRemoteModel(userModel);

        // Disable the contact info text fields again.
        userView.getEmailView().setEnabled(false);
        userView.getPhoneView().setEnabled(false);

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
        userView.getEmailView().setEnabled(true);
        userView.getPhoneView().setEnabled(true);

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

        userModel.deleteObserver(userView);

        userController = null;
        userModel = null;
        userView = null;
    }
}
