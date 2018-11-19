package com.cybersix.markme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LanguageHandlerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_language, container, false);
    }


    public void english(View view) {
        NavigationController.getInstance().switchToFragment(BodyFragment.class);


    }

    public void french(View view) {
        NavigationController.getInstance().switchToFragment(BodyFragment.class);


    }
}
