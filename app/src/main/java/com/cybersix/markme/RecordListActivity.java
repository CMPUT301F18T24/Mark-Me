package com.cybersix.markme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListActivity extends ListActivity {
    private ListView recordList;

    // create the problem info pop-up for the activity
    public class ProblemPopUp extends AppCompatActivity {

        // the problem pop-up will display the problem information related by the user
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_problem_pop_up); // will need to intent test this

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // initialize all of the buttons and hid the ones not needed for the record list activity
        Button addButton = (Button) findViewById(R.id.addButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button infoButton = (Button) findViewById(R.id.infoButton);

        // for the record list activity the add button is not needed
        addButton.setVisibility(View.GONE);

        // set the title text
        TextView titleText = (TextView) findViewById(R.id.titleTextView);
        titleText.setText("List of Records");
        EditText searchText = (EditText) findViewById(R.id.searchText);
        searchText.setText("Search for a record");

        // initialize the list view
        recordList = (ListView) findViewById(R.id.mainListView);

        // set the on click listeners for the valid buttons
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up the button click where the user will be able to search for specific
                // records based from the text
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set up the button click where the user will be able to show the problem info
                // popup
            }
        });
    }
}
