package com.cybersix.markme;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
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

    @Test
    public void testLoginSuccessful() {

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
