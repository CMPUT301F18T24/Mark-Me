/**
 * CMPUT 301 Team 24
 *
 * The activity that includes the about of the app.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Dorsa Nahid
 */
package com.cybersix.markme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.R;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText("About Mark-Me");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });
    }

}
