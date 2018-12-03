package com.cybersix.markme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v4.app.Fragment;

import com.cybersix.markme.actvity.LoginActivity;
import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.actvity.ProblemCreationActivity;
import com.cybersix.markme.fragment.AccountSettingsFragment;
import com.cybersix.markme.fragment.RecordInfoFragment;
import com.cybersix.markme.fragment.RecordListFragment;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.UserModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> loginActivityTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    /*
        Use cases: 31
     */
    @Test
    public void userLogin() {
        //Because of auto login we check this first
        //Try is a must here because of auto login..
        //To make this test meaningful, we catch only specific exceptions
        //relating to clicking on a non-existing element. (IE we have auto logged in)
        try {
            onView(withId(R.id.signupButton)).perform(click());
            onView(withId(R.id.fragment_account_settings_email)).perform(typeText("lol123@123.com"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.fragment_account_settings_usernameText)).perform(typeText("supertestaccount"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.fragment_account_settings_phoneText)).perform(typeText("780-000-0000"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.signupButton)).perform(click());
        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }

    }

    /*
        Use cases: 13,14
     */
    @Test
    public void userEditContactInfo() {
        Activity activity = loginActivityTestRule.getActivity();
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, "testtest");
        activity.startActivity(i);
        Bundle p = new Bundle();
        Fragment g = new AccountSettingsFragment();
        g.setArguments(p);
        //Move to edit contact info
        loginActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();

    }

    /*
    Use cases: 32,33
    */
    @Test
    public void userViewShortCode() {
        Activity activity = loginActivityTestRule.getActivity();
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, "testtest");
        activity.startActivity(i);
        Bundle p = new Bundle();
        Fragment g = new AccountSettingsFragment();
        g.setArguments(p);
        //Move to edit contact info
        loginActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        //Short code is now in view for user
    }
}

