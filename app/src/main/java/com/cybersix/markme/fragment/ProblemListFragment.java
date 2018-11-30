/**
 * CMPUT 301 Team 24
 *
 * This is the Problem List Fragment that will display all of the problems associated to the user
 *
 * Date: 2018-11-11
 *
 * Version 0.1
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.fragment.ListFragment
 */
package com.cybersix.markme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cybersix.markme.R;
import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.actvity.ProblemCreationActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.fragment.ListFragment;
import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Jose: this is what I am meant to fill out. I will have to make sure if anyone else is going
 *       to be referencing this or developing their own version of it
 */
public class ProblemListFragment extends ListFragment {
    // This activity will be spread among all the other activities throughout the application
    // where it deals with a list of problems
    // getProblems(); <- this will probably come from the controller

    public static final int REQUEST_CODE_ADD = 1;
    public static final String EXTRA_PROBLEM_INDEX = "EXTRA_PROBLEM_INDEX";
    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";

    private ArrayAdapter<ProblemModel> problemListAdapter = null;
    private ProblemController controllerInstance = ProblemController.getInstance();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getTitle().setText("List of Problems");

        // set the on click listeners
        getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProblemCreationActivity.class);
                startActivityForResult(i, REQUEST_CODE_ADD);
            }
        });

//         we are going to set the listener for the list view
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // the user is going to select the problem that they want to view
                controllerInstance.setSelectedProblem(position);
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_PROBLEM_INDEX, position);
                // TODO: for now the resulting activity will show preset data but the later version
                // TODO: will show the records related to the problem
                NavigationController.getInstance()
                        .switchToFragment(RecordListFragment.class, bundle);


            }

        });

        problemListAdapter = new ArrayAdapter<ProblemModel>(getActivity(), R.layout.list_item, DataModel.getInstance().getProblems());
        getListView().setAdapter(problemListAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            update(
                    intent.getStringExtra(ProblemCreationActivity.EXTRA_TITLE),
                    intent.getStringExtra(ProblemCreationActivity.EXTRA_DESCRIPTION)
            );
        }
    }

    public void update(String title, String description) {
        ProblemController instance = ProblemController.getInstance();
        instance.createNewProblem(title, description);
//        controllerInstance.loadProblemData();
        problemListAdapter.notifyDataSetChanged();
    }
}
