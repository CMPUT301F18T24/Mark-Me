package com.cybersix.markme.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cybersix.markme.fragment.ListFragment;
import com.cybersix.markme.model.ListItemModel;
import com.cybersix.markme.model.ListModel;

public class ListController {
    ListModel model = null;

    public ListController(ListModel model) {
        this.model = model;
    }

    public void displayListItem(int position) {
        ListItemModel item = model.get(position);
        NavigationController.getInstance().setFragment(item.getDisplayFragment());
    }

    public void addListItem() {
        Fragment fragment = NavigationController.getInstance().getFragment();
        ListItemModel item = model.addNewItem();
        Intent intent = item.getItemCreationIntent(fragment.getContext());
        fragment.startActivityForResult(intent, ListFragment.REQUEST_CODE_ADD);
    }
}
