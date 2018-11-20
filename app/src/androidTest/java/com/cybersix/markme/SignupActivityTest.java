package com.cybersix.markme;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void testAccountCreation() {

        // Generate a random username. TODO: Maybe I can generate string hashes for our tests?
        int min = 0;
        int max = 99999999;
        int username = new Random().nextInt((max - min) + 1) + min;

        // Type the username and password.
        onView(withId(R.id.emailText)).perform(typeText("test@test"));
        onView(withId(R.id.usernameText)).perform(typeText(Integer.toString(username)));
        onView(withId(R.id.passwordText)).perform(typeText("hihi"));
        onView(withId(R.id.phoneText)).perform(typeText("7880-123-4567"));

        // Hit the signup button.
        onView(withId(R.id.signupButton)).perform(click());

        // Check if we returned back to loginActivity
        intended(hasComponent(MainActivity.class.getName()));

    }

}
