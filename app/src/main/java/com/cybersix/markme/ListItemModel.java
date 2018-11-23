package com.cybersix.markme;

import android.content.Intent;
import android.support.v4.app.Fragment;

public interface ListItemModel {
    /**
     * @return A fragment which describes/displays the model's data.
     */
    Fragment getDisplayFragment();

    /**
     * @return An intent for creating the data model through user input.
     */
//    Intent createDataModel();
}