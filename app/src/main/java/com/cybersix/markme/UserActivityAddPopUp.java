/**
 * This activity will be in charge of letting the user (patient) assign another user to their account
 * to view on their progress
 *
 * Version 0.1
 *
 * Date: 2018-11-18
 *
 * Copyright Notice
 *
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UserActivityAddPopUp extends AppCompatActivity {
    private ArrayAdapter<UserModel> usersAdapter;
    private ArrayList<UserModel> users = new ArrayList<UserModel>();
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_pop_up);

        // set up the parameters for the popup window
        // get the display metrics for the edit popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int activity_width = dm.widthPixels;
        int activity_height = dm.heightPixels;

        // set the layout width and height dimensions
        getWindow().setLayout((int) (activity_width*0.9), (int) (activity_height*0.9));

        // set up the layout parameters for consistency
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        // set up any buttons that will be used
        usersListView = (ListView) findViewById(R.id.CareProvideList);
        Button searchFilter = (Button) findViewById(R.id.searchButton2);
        Button assignUserButton = (Button) findViewById(R.id.addAssignUserButton);

        // TODO: Will be removed once server functionality is implemented
        for (int i = 0; i < 15; i++){
            String tempUsername = "UserPerson" + Integer.toString(i);
            String tempPassword = "1234";
            String tempID = "Fake ID " + Integer.toString(i);
            try {
                UserModel tempUser = new UserModel(tempUsername, tempPassword);
                tempUser.setUserID(tempID);
                users.add(tempUser);
            }
            catch (Exception e) {
                // do nothing
            }
        }


        searchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user wants to change the results of the user list
                searchFilterUsers();
            }
        });

        assignUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user selected a user from the list and wants them to be assigned to view their
                // own progress
                assignUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set up the list and the adapter
        // TODO: Get the users from the server
        // users = IOUtilityController.getUsers();
        usersAdapter = new ArrayAdapter<UserModel>(this, R.layout.list_item, users);
        usersListView.setAdapter(usersAdapter);
    }

    private void searchFilterUsers() {
        // This function will search for the users based from the edit text filter and return
        // a list of all of the users based from the keyword

        // make sure that there is something within the search text field
        EditText filterString = (EditText) findViewById(R.id.userFilterText);
        String queryKey = filterString.getText().toString();
        if (queryKey != null || queryKey != "") {
            // users = IOUtilityController.getUsers(queryKey);
            usersAdapter.notifyDataSetChanged();
        }
        else {
            // TODO: prompt the user that there is nothing in the search text
        }
    }

    private void assignUser() {
        if (usersListView.isSelected()) {
            // get and add assign the user to the logged in user
            try {
                UserModel user = users.get(usersListView.getSelectedItemPosition());
                // save this user to the server
                // IOUtilityController.assignUser(user);
                finish();
            }
            catch (Exception e) {
                // display an error when assigning the user on the cloud
                // TODO: display an error prompt
            }
        }
    }
}
