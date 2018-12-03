/**
 * CMPUT 301 Team 24
 *
 * Patient assignment activity is to be a separate activity entirely under the settings activity.
 *
 * Version 0.1
 *
 * Date: 2018-11-18
 *
 * Copyright Notice
 * @author Jose Ramirez
 */
package com.cybersix.markme.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cybersix.markme.R;
import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.actvity.UserActivityAddPopUp;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class UserAssignmentFragment extends Fragment {
    // will need to set a list adapter to the view to be notified if any of the models have been
    // updated
    private ArrayAdapter<UserModel> userListAdapter;
    private ArrayList<UserModel> userList = new ArrayList<UserModel>();
    private ListView assignedUserListView;
    private int removePosition;
    private UserModel currentUser = null;
    private ElasticSearchIO ESController = ElasticSearchIO.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_assignment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set up all of the buttons that are used within this activity
        Button addButton = (Button) getActivity().findViewById(R.id.fragment_user_assignment_addAssignUserButton);
        Button removeButton = (Button) getActivity().findViewById(R.id.fragment_user_assignment_removeUserButton);
        Button generateButton = (Button) getActivity().findViewById(R.id.fragment_user_assignment_GenerateCode);
        assignedUserListView = (ListView) getActivity().findViewById(R.id.fragment_user_assignment_listView);
        currentUser = ((MainActivity) getActivity()).getUser();

        // get the list of users that are from the elastic search database
        userList.addAll(ESController.getAssignedUsers(currentUser.getUserId()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will open the add user popup
//                Intent addUserIntent = new Intent(getActivity(), UserActivityAddPopUp.class);
//                startActivity(addUserIntent);
                addUser();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the user has selected a user from the list view and wants to remove the user
                // ask for a popup whether or not the user wishes to continue

                removeUser();
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // generate the code and show it in a dialog popup
                generateAssignmentCode();
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText(R.string.assign_myself);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });

        //TODO: still need to set the "selected" animation to be there
        assignedUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // this is when one of the selected items within the list view is selected
                view.setSelected(true);
                assignedUserListView.setSelected(true);
                removePosition = position;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // the list view will show all of the registered care providers from within the database
        // getCareProviders();
        // displayCareProvides();
        // TODO: Make sure to check the previous assignment for reference into how to use the list adapter
        // The list will have a "Select is on" mode where the user can select one of the patient
        // items

        // TODO: load the list of assigned users from the server. For now it is just fake data created
        // TODO: from onCreate
        // userList = IOUtilityController.getUsers();
        userListAdapter = new ArrayAdapter<UserModel>(getContext(), R.layout.list_item, userList);
        assignedUserListView.setAdapter(userListAdapter);

    }

    private void removeUser() {
        // TODO: add a prompt asking if the user is sure they want to remove the item
        if (assignedUserListView.isSelected()) {
            assignedUserListView.setSelected(false);
            // get and remove the selected item
            UserModel removeUser = userList.get(removePosition);
            // build the warning dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("Are you sure you want to remove this patient?\n" + removeUser.toString());
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        userList.remove(removePosition);
                        userListAdapter.notifyDataSetChanged();
                        //TODO: save the list into the server
                        Log.d("Remove Assign User", "The user assignment has been removed");
                    }
                    catch (Exception e) {
                        // display an error when removing the list item
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // return null to the activity
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void addUser() {
        final EditText patientCodeEdit;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Assign a patient");
        builder.setMessage("Please enter the generated patient assignment code below...");
        patientCodeEdit = new EditText(this.getContext());
        builder.setView(patientCodeEdit);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // get the patient ID from the server, then add the assignment to the database
                // TODO: get the patient ID from the server based from the code.
                // String patientID = getPatientID();
                // for now the patient ID is hard coded
                String code = patientCodeEdit.getText().toString();
                String patientUsername = ESController.getUserAssignmentCode(code);
                // now add the assigned to elastic search
                ESController.addAssignedUser(patientUsername, currentUser.getUserId());
                userList.add(ESController.findUser(patientUsername));
                userListAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void generateAssignmentCode() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Generate Assignment Code");
        String code = ESController.generateAssignmentCode(currentUser.getUsername());
        builder.setMessage("Please send and notify the care provider the code that has been generated.\n" +
        "Assignment code: " + code);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
                return;

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
