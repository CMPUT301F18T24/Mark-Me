package com.cybersix.markme;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class AccountSettingsFragment extends Fragment implements Observer {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_account_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Add the AccoutSettingsActivity view as an observer to the UserModel.
        UserProfileController profileController = UserProfileController.getInstance();
        profileController.user.addObserver(this);

        initUI();
    }

    public void initUI() {

        UserProfileController profileController = UserProfileController.getInstance();

        // Initialize the fields with the user's information
        TextView usernameText = (TextView) getActivity().findViewById(R.id.usernameText);
        TextView emailText = (TextView) getActivity().findViewById(R.id.emailText);
        TextView phoneText = (TextView) getActivity().findViewById(R.id.phoneText);
        Button saveButton = (Button) getActivity().findViewById(R.id.saveButton);

        // Fill the textboxes with information
        // TODO: These extra calls to the controller could be avoided if model was its own object.
        usernameText.setText(profileController.user.getUserID());
        emailText.setText(profileController.user.getEmail());
        phoneText.setText(profileController.user.getPhone());

        // Disable the contact information textboxs.
        emailText.setEnabled(false);
        phoneText.setEnabled(false);
        saveButton.setVisibility(View.GONE);

        // Add an onClick for the edit contact information button
        Button editContactsButton = (Button) getActivity().findViewById(R.id.editContactButton);
        editContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableChangingContactInformation();
            }
        });

        // Add an onClick for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewContactInformation();
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {
        // Update the contact information fields.
        updateContactInformationFields((UserModel) o);
    }

    // Update the contact information fields using the updated values from the model.
    public void updateContactInformationFields(UserModel model) {
        TextView emailText = (TextView) getActivity().findViewById(R.id.emailText);
        TextView phoneText = (TextView) getActivity().findViewById(R.id.phoneText);

        emailText.setText(model.getEmail());
        phoneText.setText(model.getPhone());
    }

    // Try to save the new contact information provided by the user.
    public void saveNewContactInformation() {

        UserProfileController profileController = UserProfileController.getInstance();

        TextView emailText = (TextView) getActivity().findViewById(R.id.emailText);
        TextView phoneText = (TextView) getActivity().findViewById(R.id.phoneText);

        // Edit the contact information.
        profileController.editContactInformation(emailText.getText().toString(),
                                                 phoneText.getText().toString());

        // Disable the contact info text fields again.
        emailText.setEnabled(false);
        phoneText.setEnabled(false);

        // Hide the save button
        Button saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        saveButton.setVisibility(View.GONE);

        // And show the edit contact info button.
        Button editContactsButton = (Button) getActivity().findViewById(R.id.editContactButton);
        editContactsButton.setVisibility(View.VISIBLE);

    }

    // Enable changing the contact info text boxes, show the save button and hide the edit
    // contact information button.
    public void enableChangingContactInformation() {

        // Enable changing the contact information.
        TextView emailText = (TextView) getActivity().findViewById(R.id.emailText);
        TextView phoneText = (TextView) getActivity().findViewById(R.id.phoneText);
        emailText.setEnabled(true);
        phoneText.setEnabled(true);

        // Show the save button
        Button saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        saveButton.setVisibility(View.VISIBLE);

        // And hide the edit contact info button.
        Button editContactsButton = (Button) getActivity().findViewById(R.id.editContactButton);
        editContactsButton.setVisibility(View.GONE);

    }


}