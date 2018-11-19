package com.cybersix.markme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListFragment extends ListFragment {
    private ArrayAdapter<RecordModel> recordListAdapter;
    private RecordController controllerInstance = RecordController.getInstance();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTitle().setText("List of Records"); // Title should be title of problem
        getDetails().setText("List of records"); // Detail should be problem description
        getReturnButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(ProblemListFragment.class);
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
    public void onStart() {
        super.onStart();
        // set the adapter for the list activity
        recordListAdapter = new ArrayAdapter<RecordModel>(getActivity(), R.layout.list_item, controllerInstance.records);
        getListView().setAdapter(recordListAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), RecordInfoActivity.class);
                i.putExtra("RecordIdx",position);
                startActivity(i);
            }
        });
    }
}
