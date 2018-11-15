/**
 * Jose: This is the generic list activity that will be shared amoung the problems activity
 *       and the records activity
 *
 *  Description: This will be the base activity for the list view for both the problems list
 *               and the records list
 *  Version 0.1
 *
 *  Date: 2018-11-10
 *
 *  Copyright notice: TODO: what is the copyright among us?
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    private TextView mTitle = null;
    private TextView mDetails = null;
    private View mReturnButton = null;
    private View mAddButton = null;
    private EditText mSearchField = null;
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        GuiUtil.setFullScreen(this);

        mTitle = findViewById(R.id.titleTextView);
        mDetails = findViewById(R.id.descriptionTextView);
        mReturnButton = findViewById(R.id.returnButton);
        mAddButton = findViewById(R.id.addButton);
        mSearchField = findViewById(R.id.seachField);
        mListView = findViewById(R.id.mainListView);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public TextView title() {
        return mTitle;
    }

    public TextView details() {
        return mDetails;
    }

    public View returnButton() {
        return mReturnButton;
    }

    public View addButton() {
        return mAddButton;
    }

    public EditText searchField() {
        return mSearchField;
    }

    public ListView listView() {
        return mListView;
    }
}
