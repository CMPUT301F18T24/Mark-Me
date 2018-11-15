package com.cybersix.markme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListActivity extends ListActivity {
    private ArrayAdapter<RecordModel> recordListAdapter;
    private RecordController controllerInstance = RecordController.getInstance();

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
        title().setText("List of Records");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // set the adapter for the list activity
        recordListAdapter = new ArrayAdapter<RecordModel>(this, R.layout.list_item, controllerInstance.records);
        listView().setAdapter(recordListAdapter);
    }
}
