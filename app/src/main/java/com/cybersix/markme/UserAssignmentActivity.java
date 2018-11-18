/**
 * Patient assignment activity is to be a separate activity entirely under the settings activity.
 *
 * Version 0.1
 *
 * Date: 2018-11-18
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class UserAssignmentActivity extends AppCompatActivity {
    // will need to set a list adapter to the view to be notified if any of the models have been
    // updated
    private ArrayAdapter<UserModel> userListAdapter;
    private ArrayList<UserModel> userList;
    private ListView assignedUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_assignment);

        // set up all of the buttons that are used within this activity
        Button addButton = (Button) findViewById(R.id.addAssignUserButton);
        Button removeButton = (Button) findViewById(R.id.removeUserButton);
        assignedUserListView = (ListView) findViewById(R.id.assignedUserLIstView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will open the add user popup
                Intent addUserIntent = new Intent(UserAssignmentActivity.this, UserActivityAddPopUp.class);
                startActivity(addUserIntent);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the user has selected a user from the list view and wants to remove the user
                removeUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // the list view will show all of the registered care providers from within the database
        // getCareProviders();
        // displayCareProvides();
        // TODO: Make sure to check the previous assignment for reference into how to use the list adapter
        // The list will have a "Select is on" mode where the user can select one of the patient
        // items

        // TODO: load the list of assigned users from the server
        // userList = IOUtilityController.getUsers();
        userListAdapter = new ArrayAdapter<UserModel>(this, R.layout.list_item, userList);
        assignedUserListView.setAdapter(userListAdapter);

    }

    private void removeUser() {
        // TODO: add a prompt asking if the user is sure they want to remove the item
        if (assignedUserListView.isSelected()) {
            // get and remove the selected item
            try {
                userList.remove(assignedUserListView.getSelectedItemPosition());
                // save the list into the server
                // IOUtilityController.saveUser(userList);
            }
            catch (Exception e) {
                // display an error when removing the list item
                // TODO: display an error prompt
            }
        }
    }
}
