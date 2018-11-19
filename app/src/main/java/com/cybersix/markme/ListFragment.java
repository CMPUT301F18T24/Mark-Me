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

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment {
    private TextView mTitle = null;
    private TextView mDetails = null;
    private View mReturnButton = null;
    private View mAddButton = null;
    private EditText mSearchField = null;
    private ListView mListView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTitle = getActivity().findViewById(R.id.titleTextView);
        mDetails = getActivity().findViewById(R.id.descriptionTextView);
        mReturnButton = getActivity().findViewById(R.id.returnButton);
        mAddButton = getActivity().findViewById(R.id.addButton);
        mSearchField = getActivity().findViewById(R.id.seachField);
        mListView = getActivity().findViewById(R.id.mainListView);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivity.finish();
            }
        });
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getDetails() {
        return mDetails;
    }

    public View getReturnButton() {
        return mReturnButton;
    }

    public View getAddButton() {
        return mAddButton;
    }

    public EditText getSearchField() {
        return mSearchField;
    }

    public ListView getListView() {
        return mListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mTitle = null;
        mDetails = null;
        mReturnButton = null;
        mAddButton = null;
        mSearchField = null;
        mListView = null;
        mReturnButton = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GuiUtils.setFullScreen(getActivity());
    }
}
