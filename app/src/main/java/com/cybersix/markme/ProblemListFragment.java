package com.cybersix.markme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Jose: this is what I am meant to fill out. I will have to make sure if anyone else is going
 *       to be referencing this or developing their own version of it
 */
public class ProblemListFragment extends ListFragment {
    // This activity will be spread among all the other activities throughout the application
    // where it deals with a list of problems
    // getProblems(); <- this will probably come from the controller

    private ArrayAdapter<ProblemModel> problemListAdapter;
    private ProblemController controllerInstance = ProblemController.getInstance();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title().setText("List of Problems");

        // set the on click listeners
        addButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put down the procedure of what the click would do when we want to add
                // a problem
                // open problem creation activity with an intent and then let that handle
                // itself
                // Intent intent = new Intent(this, ProblemCreationActivity.activity)
                // startActivity(intent);
            }
        });

        // we are going to set the listener for the list view
        listView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // the user is going to select the problem that they want to view
                Intent intent = new Intent(view.getContext(), RecordListFragment.class);
                intent.putExtra("EXTRA_PROBLEM_INDEX", position);
                startActivity(intent);
                // TODO: for now the resulting activity will show preset data but the later version
                // TODO: will show the records related to the problem
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        // set up the array adapter with the list of problems
        problemListAdapter = new ArrayAdapter<ProblemModel>(getActivity(), R.layout.list_item, controllerInstance.problems);
        listView().setAdapter(problemListAdapter);
    }
}
