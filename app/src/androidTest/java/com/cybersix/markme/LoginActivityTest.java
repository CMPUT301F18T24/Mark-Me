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
import com.cybersix.markme.fragment.LanguageHandlerFragment;
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
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

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
    Use cases: 32,33
    */
    @Test
    public void userViewShortCode() {
        Patient fakeUser = new Patient();
        mainActivityIntentsTestRule.getActivity().setUser(fakeUser);
        Bundle p = new Bundle();
        Fragment g = new AccountSettingsFragment();
        g.setArguments(p);
        //Move to edit contact info
        mainActivityIntentsTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        //Short code is now in view for user
    }

    /*
        Use Cases: 35
     */
    @Test
    public void userChangeLanguage() {
        Patient fakeUser = new Patient();
        mainActivityIntentsTestRule.getActivity().setUser(fakeUser);
        Bundle p = new Bundle();
        Fragment g = new LanguageHandlerFragment();
        g.setArguments(p);
        //Move to language screen
        mainActivityIntentsTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        onView(withId(R.id.fragment_language_french_button)).perform(click());
    }

    /*
    Use cases: 13,14
    */
    @Test
    public void userEditContactInfo() {
        Patient fakeUser = new Patient();
        mainActivityIntentsTestRule.getActivity().setUser(fakeUser);
        Bundle p = new Bundle();
        Fragment g = new AccountSettingsFragment();
        g.setArguments(p);
        //Move to edit contact info
        mainActivityIntentsTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
    }
}

