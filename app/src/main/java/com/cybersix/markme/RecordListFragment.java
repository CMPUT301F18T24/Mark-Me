package com.cybersix.markme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListFragment extends ListFragment {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTitle().setText("List of Records");

        getReturnButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationBar().switchToFragment(ProblemListFragment.class);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // set the adapter for the list activity
        recordListAdapter = new ArrayAdapter<RecordModel>(getActivity(), R.layout.list_item, controllerInstance.records);
        getListView().setAdapter(recordListAdapter);
    }
}
