package com.cybersix.markme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LanguageHandlerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_language, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView title = getActivity().findViewById(R.id.fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.returnButton);

        title.setText("Select Your Language");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });

        View mEnglishButton = getActivity().findViewById(R.id.english_button);
        View mFrenchButton = getActivity().findViewById(R.id.french_button);


        mEnglishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_to_english(v);
            }
        });

        mFrenchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_to_french(v);
            }
        });
    }

    public void change_to_english(View view) {
        NavigationController.getInstance().setSelectedItem(R.id.body);
    }

    public void change_to_french(View view) {
        NavigationController.getInstance().setSelectedItem(R.id.body);
    }
}
