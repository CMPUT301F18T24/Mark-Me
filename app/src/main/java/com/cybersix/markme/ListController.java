package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class ListController {
    ListModel model = null;
    Fragment prev = null, next = null;

    ListController(@NonNull ListModel model) {
        this.model = model;
    }

    public void displayModelInfo(int position) {
        DataModel info = model.get(position);
        NavigationController.getInstance().setFragment(info.getDataFragment());
    }

    public void addModel() {

    }
}
