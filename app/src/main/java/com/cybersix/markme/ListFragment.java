/**
 * Jose: This is the generic list fragment that will be shared among the problems activity
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
    ListObserver listObserver = null;
    ListController listController = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listController = new ListController();
        listObserver = new ListObserver(listController);

        listObserver.setTitleView((TextView) getView().findViewById(R.id.fragment_list_titleTextView));
        listObserver.setDetailView((TextView) getView().findViewById(R.id.fragment_list_descriptionTextView));
        listObserver.setReturnButton(getActivity().findViewById(R.id.fragment_list_returnButton));
        listObserver.setAddButton(getActivity().findViewById(R.id.fragment_list_addButton));
        listObserver.setSearchField((EditText) getActivity().findViewById(R.id.fragment_list_seachField));
        listObserver.setListView((ListView) getActivity().findViewById(R.id.fragment_list_mainListView));
    }
}
