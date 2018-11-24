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

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment<T extends ListItemModel> extends Fragment {
    public static final String EXTRA_ITEM_LAYOUT = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_ITEM_LAYOUT";
    public static final String EXTRA_TITLE = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_TITLE";
    public static final String EXTRA_DESCRIPTION = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_DESCR";
    public static final String EXTRA_CLASS = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_CLASS";
    public static final int REQUEST_CODE_ADD = 1;

    private ListObserver listObserver = null;
    private ListController listController = null;
    private ListModel<T> listModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        String title = args.getString(EXTRA_TITLE, "List Title");
        String details = args.getString(EXTRA_DESCRIPTION, "List Description");
        Class<T> clazz = (Class<T>) args.getSerializable(EXTRA_CLASS);
        int layoutId = args.getInt(EXTRA_ITEM_LAYOUT, R.layout.list_item);

        listModel = new ListModel<>(getActivity(), layoutId, clazz);
        listModel.setTitle(title);
        listModel.setDetails(details);

        listController = new ListController(listModel);
        listObserver = new ListObserver(listController);

        listObserver.setTitleView((TextView) getView().findViewById(R.id.fragment_list_titleTextView));
        listObserver.setDetailView((TextView) getView().findViewById(R.id.fragment_list_descriptionTextView));
        listObserver.setReturnButton(getActivity().findViewById(R.id.fragment_list_returnButton));
        listObserver.setAddButton(getActivity().findViewById(R.id.fragment_list_addButton));
        listObserver.setSearchField((EditText) getActivity().findViewById(R.id.fragment_list_seachField));
        listObserver.setListView((ListView) getActivity().findViewById(R.id.fragment_list_mainListView));

        listModel.addObserver(listObserver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CODE_ADD:
                if (resultCode == Activity.RESULT_OK) {
                    String title = intent.getStringExtra(EXTRA_TITLE);
                    String description = intent.getStringExtra(EXTRA_DESCRIPTION);
                    listModel.back().set(title, description);
                    listModel.refresh();
                } else {
                    listModel.delete(listModel.back());
                }
                break;
        }
    }

}
