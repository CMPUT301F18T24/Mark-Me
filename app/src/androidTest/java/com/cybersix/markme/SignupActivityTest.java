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

        // Generate a random username.
        int min = 0;
        int max = 99999999;
        int username = new Random().nextInt((max - min) + 1) + min;

        //Login with an existing account.

        // Type the username and password.
        onView(withId(R.id.usernameText)).perform(typeText("testtest"));
        onView(withId(R.id.passwordText)).perform(typeText("hihi"));

        // Hit the login button.
        onView(withId(R.id.loginButton)).perform(click());

        // Check if MainActivity
        intended(hasComponent(MainActivity.class.getName()));

    }

}
