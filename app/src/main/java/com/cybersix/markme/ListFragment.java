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

public class ListFragment<T extends ListItemModel> extends Fragment {
    public static final String EXTRA_ITEM_LAYOUT = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_ITEM_LAYOUT";
    public static final String EXTRA_TITLE = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_TITLE";
    public static final String EXTRA_DESCRIPTION = "COM_CYBERSIX_MARKME_LIST_FRAGMEMT_DESCR";

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
        int layoutId = args.getInt(EXTRA_ITEM_LAYOUT, R.layout.list_item);

        listModel = new ListModel<>(getActivity(), layoutId);
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
}
