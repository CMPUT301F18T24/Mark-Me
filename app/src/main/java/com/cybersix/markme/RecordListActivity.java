package com.cybersix.markme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListActivity extends ListActivity {
    private ListView recordListView;
    private ArrayAdapter<RecordModel> recordListAdapter;
    private RecordController recordController = RecordController.getInstance();
    private ArrayList<RecordModel> recordsToDisplay = new ArrayList<>();


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
        //setContentView(R.layout.activity_list);

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
        recordListView = (ListView) findViewById(R.id.mainListView);

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

        Intent i = getIntent();
        //Get selected part from intent
        EBodyPart selectedPart = (EBodyPart) i.getSerializableExtra("SelectedPart");

        //If null, we want all records
        if(selectedPart == null){
            recordsToDisplay = recordController.selectedProblemRecords;
        } else {
            //Otherwise we filter out for just the selected part
            for(RecordModel r : recordController.selectedProblemRecords){
                if(r.getBodyLocation().getBodyPart() == selectedPart){
                    recordsToDisplay.add(r);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // set the adapter for the list activity
        recordListAdapter = new ArrayAdapter<RecordModel>(this, R.layout.list_item, recordsToDisplay);
        recordListView.setAdapter(recordListAdapter);
        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(RecordListActivity.this, RecordInfoActivity.class);
                i.putExtra("RecordIdx",position);
                startActivity(i);
                finish();
            }
        });
    }
}
