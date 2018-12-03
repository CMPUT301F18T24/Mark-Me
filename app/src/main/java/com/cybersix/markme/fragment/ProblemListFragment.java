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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
    private ArrayList<ProblemModel> problemsToDisplay = new ArrayList<>();
    private List<String> ShowHist;
    private ArrayAdapter<String> adapter;


    private void saveHistory(String string){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        editor.putString(ts, string);
        editor.apply();
    }


    public List<String> readHistory() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        List<String> historyToShow = new ArrayList<String>();

        ArrayList<ArrayList<String>> history = new ArrayList<ArrayList<String>>();
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String val = String.valueOf(entry.getValue());
            history.add(new ArrayList<String>(Arrays.asList(key,val)));
        }

        Collections.sort(history, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        });

        Collections.reverse(history);

        int size = (history.size() < 3) ? history.size() : 3;

        for (int i = 0; i < size; i++) {
            historyToShow.add(history.get(i).get(1));
        }

        return historyToShow;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getTitle().setText(R.string.list_of_prob);

        ShowHist = new ArrayList<String>(readHistory());
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,ShowHist);
        getSearchField().setAdapter(adapter);

        // set the on click listeners
        getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProblemCreationActivity.class);
                startActivityForResult(i, REQUEST_CODE_ADD);
            }
        });

        getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = getSearchField().getText().toString();
                saveHistory(value);
                ShowHist.clear();
                ShowHist.addAll(readHistory());
                adapter.notifyDataSetChanged();
                searchProblems();
            }
        });

        getSearchField().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchField().showDropDown();
            }
        });

        // we are going to set the listener for the list view
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // the user is going to select the problem that they want to view

                Bundle bundle = new Bundle();
                int recordIndex = 0;
                for (int i = 0; i < controllerInstance.getProblems().size(); i++){
                    if (controllerInstance.getProblems().get(i).equals(problemsToDisplay.get(position))){
                        recordIndex = i;
                    }
                }

                controllerInstance.setSelectedProblem(recordIndex);
                bundle.putInt(EXTRA_PROBLEM_INDEX, recordIndex);
                NavigationController.getInstance()
                        .switchToFragment(RecordListFragment.class, bundle);

            }

        });

        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            controllerInstance.createNewProblem(intent.getStringExtra(ProblemCreationActivity.EXTRA_TITLE),
                                      intent.getStringExtra(ProblemCreationActivity.EXTRA_DESCRIPTION));
            updateUI();
        }
    }

    // Searches with the specified term but it does not handle updates to the data.
    // Browsing back to this fragment will reset the search.
    // Also doesn't do multiple searches well.
    public void searchProblems() {

        // Start off fresh.
        updateUI();

        // Get the search term
        String term = getSearchField().getText().toString().trim().toLowerCase();
        ArrayList<ProblemModel> searchedProblems = new ArrayList<>();

        Log.d("vishal_search", term);

        // Only perform searching if something was given in search term.
        if (term.compareTo("") != 0) {
            // Iterate through all records to see if we should display them.
            for (ProblemModel problem : problemsToDisplay) {
                if (problem.getTitle().trim().toLowerCase().contains(term)) {
                    searchedProblems.add(problem);
                }
            }

            // Add only the searched records.
            problemsToDisplay = new ArrayList<ProblemModel>();
            problemsToDisplay.addAll(searchedProblems);

            // Update the display
            problemListAdapter = new ArrayAdapter<ProblemModel>(getActivity(), R.layout.list_item, problemsToDisplay);
            getListView().setAdapter(problemListAdapter);
            problemListAdapter.notifyDataSetChanged();
        }

    }

    public void updateUI() {

        problemsToDisplay = new ArrayList<ProblemModel>();
        problemsToDisplay.addAll(controllerInstance.getProblems());
        problemListAdapter = new ArrayAdapter<ProblemModel>(getActivity(), R.layout.list_item, problemsToDisplay);
        getListView().setAdapter(problemListAdapter);
        problemListAdapter.notifyDataSetChanged();
    }
}
