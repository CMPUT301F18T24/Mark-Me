package com.cybersix.markme;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.cybersix.markme.actvity.SignupActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    /*
        Use cases: 12
     */
    @Test
    public void testAccountCreation() {

        // Generate a random username. TODO: Maybe I can generate string hashes for our tests?
        int min = 0;
        int max = 99999999;
        int username = new Random().nextInt((max - min) + 1) + min;

        // Type the username and password.
        onView(withId(R.id.fragment_account_settings_email)).perform(typeText("test@test"));
        onView(withId(R.id.fragment_account_settings_usernameText)).perform(typeText(Integer.toString(username)));
       // onView(withId(R.id.passwordText)).perform(typeText("hihi"));
        onView(withId(R.id.fragment_account_settings_phoneText)).perform(typeText("780-123-4567"));

        Espresso.closeSoftKeyboard();

        // Hit the signup button.
        onView(withId(R.id.signupButton)).perform(click());

        // Check if we returned back to loginActivity
        assertTrue(signupActivityTestRule.getActivity().isFinishing());

    }

}
