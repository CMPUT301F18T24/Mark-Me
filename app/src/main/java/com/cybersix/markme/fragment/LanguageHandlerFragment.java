/**
 * CMPUT 301 Team 24
 *
 * handles the language change
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Dorsa Nahid
 */
package com.cybersix.markme.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybersix.markme.R;
import com.cybersix.markme.controller.NavigationController;

import java.util.Locale;

public class LanguageHandlerFragment extends Fragment {
    private final String language_code_fr = "fr";
    private final String language_code_en = "en";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_language, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText(R.string.change_lang_text);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });

        View mEnglishButton = getActivity().findViewById(R.id.fragment_language_english_button);
        View mFrenchButton = getActivity().findViewById(R.id.fragment_language_french_button);


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
        Resources res = getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language_code_en.toLowerCase())); // API 17+ only.
        res.updateConfiguration(conf, dm);
        NavigationController.getInstance().setSelectedItem(R.id.body);
    }

    public void change_to_french(View view) {
        Resources res = getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language_code_fr.toLowerCase())); // API 17+ only.
        res.updateConfiguration(conf, dm);
        NavigationController.getInstance().setSelectedItem(R.id.body);
    }
}
