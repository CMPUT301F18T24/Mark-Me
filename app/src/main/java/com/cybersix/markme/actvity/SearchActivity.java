/**
 * CMPUT 301 Team 24
 *
 * This is a general search activity that allows users to select 1 or more filters and returns
 * the choices the user wants to filter with.
 *
 * Version 1.0
 *
 * Date: 2018-12-3
 *
 * Copyright Notice
 * @author Vishal Patel
 * @see com.cybersix.markme.model.RecordModel
 * @see com.cybersix.markme.fragment.RecordListFragment
 * @see com.cybersix.markme.model.ProblemModel
 * @see com.cybersix.markme.fragment.ProblemListFragment
 */
package com.cybersix.markme.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cybersix.markme.R;
import com.cybersix.markme.model.EBodyPart;

public class SearchActivity extends AppCompatActivity {

    public static final String QUERY_TERM = "com.cybersix.markme.QUERY_TERM";
    public static final String QUERY_LOCATION = "com.cybersix.markme.QUERY_LOCATION";
    public static final String QUERY_BUNDLE = "com.cybersix.markme.QUERY_BUNDLE";
    private Spinner bodyLocationSpinner;
    private EditText editTextTermSearch;
    private Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize the body locations
        bodyLocationSpinner = (Spinner) findViewById(R.id.activity_search_bodyLocationSpinner);
        bodyLocationSpinner.setAdapter(new ArrayAdapter<EBodyPart>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1,
                                        EBodyPart.values()));

        searchButton = (Button) findViewById(R.id.activity_search_accept_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnSearchQuery();
            }
        });

        editTextTermSearch = (EditText) findViewById(R.id.activity_search_searchTermsText);

    }

    private void returnSearchQuery() {
        Intent data = new Intent();
        Bundle bunbun = new Bundle();

        // Initialize bunbun
        bunbun.putString(QUERY_TERM, "");
        bunbun.putSerializable(QUERY_LOCATION, null);

        String term = editTextTermSearch.getText().toString();
        if (term.compareTo("") != 0) {
            bunbun.putString(QUERY_TERM, term);
        }

        EBodyPart bodyPart = (EBodyPart) bodyLocationSpinner.getSelectedItem();
        if (bodyPart != null) {
            bunbun.putSerializable(QUERY_LOCATION, bodyPart);
        }

        data.putExtra(QUERY_BUNDLE, bunbun);
        setResult(RESULT_OK, data);
        finish();
    }



}
