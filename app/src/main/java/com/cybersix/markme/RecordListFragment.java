package com.cybersix.markme;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
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
    public static final String EXTRA_RECORD_INDEX = "RecordIdx";
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        //Get selected part from intent
        EBodyPart selectedPart = (EBodyPart) args.getSerializable(BodyFragment.EXTRA_SELECTED_PART);
        ProblemModel problemModel = ProblemController.getInstance().getSelectedProblem();

        getTitle().setText(problemModel.getTitle()); // Title should be title of problem
        getDetails().setText(problemModel.getDescription()); // Detail should be problem description
        getReturnButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(ProblemListFragment.class);
            }
        });

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

        // set the adapter for the list activity
        recordListAdapter = new ArrayAdapter<RecordModel>(getActivity(), R.layout.list_item, recordsToDisplay);
        getListView().setAdapter(recordListAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), RecordInfoActivity.class);
                i.putExtra(EXTRA_RECORD_INDEX, position);
                startActivity(i);
            }
        });

        getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: What if we want to add a record with using the body?
//                Intent i = new Intent(getActivity(), RecordCreationActivity.class);
//                startActivity(i);
            }
        });
    }
}
