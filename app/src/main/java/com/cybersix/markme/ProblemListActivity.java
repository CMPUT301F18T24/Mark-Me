package com.cybersix.markme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Jose: this is what I am meant to fill out. I will have to make sure if anyone else is going
 *       to be referencing this or developing their own version of it
 */
public class ProblemListActivity extends ListActivity {
    // This activity will be spread among all the other activities throughout the application
    // where it deals with a list of problems
    // getProblems(); <- this will probably come from the controller

    private ListView problemListView;
    private ArrayAdapter<ProblemModel> problemListAdapter;
    private ProblemController controllerInstance = ProblemController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // initialize all of the buttons and hid the ones not needed for the problem list activity
        Button addButton = (Button) findViewById(R.id.addButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button infoButton = (Button) findViewById(R.id.infoButton);

        // for the problem list activity, the info button is not relevant
        infoButton.setVisibility(View.GONE);

        // Also initialize the title textview
        TextView titleText = (TextView) findViewById(R.id.titleTextView);
        titleText.setText("List of Problems");
        EditText searchText = (EditText) findViewById(R.id.searchText);
        searchText.setText("Search for a problem");

        // initialize the list view
        problemListView = (ListView) findViewById(R.id.mainListView);

        // set the on click listeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put down the procedure of what the click would do when we want to add
                // a problem
                // open problem creation activity with an intent and then let that handle
                // itself
                Intent intent = new Intent(v.getContext(), ProblemActivityCreation.class);
                startActivity(intent);
                // TODO: talk to friends about how to notify data? Is this through the controller?
                problemListAdapter.notifyDataSetChanged();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put down the procure of searching for specific problems in the activity
                // this is going to search through the list from the problems, or we could
                // use elasticsearch
                // TODO: search through list or get results from elastic search
            }
        });

        // we are going to set the listener for the list view
        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // the user is going to select the problem that they want to view
                controllerInstance.setSelectedProblem(position);
                Intent intent = new Intent(view.getContext(), RecordListActivity.class);
                intent.putExtra("EXTRA_PROBLEM_INDEX", position);
                startActivity(intent);
                problemListAdapter.notifyDataSetChanged();
                // TODO: for now the resulting activity will show preset data but the later version
                // TODO: will show the records related to the problem
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        // set up the array adapter with the list of problems
        problemListAdapter = new ArrayAdapter<ProblemModel>(this, R.layout.list_item, controllerInstance.problems);
        problemListView.setAdapter(problemListAdapter);
        problemListAdapter.notifyDataSetChanged();
    }
}
