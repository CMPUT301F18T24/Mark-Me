package com.cybersix.markme;

import android.app.Activity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.CursorMatchers;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.fragment.BodyFragment;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class BodyActivityTest {

    NavigationController nav;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup() throws UserModel.UsernameTooShortException {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, "rizwan146");
        mainActivityTestRule.launchActivity(intent);
    }

    /*
        Use Cases: 7,5
     */
    @Test
    public void testAddNewRecord() {
        onView(withId(R.id.fragment_list_mainListView)).check(matches(isDisplayed()));
        onData((anything())).inAdapterView(withId(R.id.fragment_list_mainListView)).atPosition(0).perform(click());
        onView(withId(R.id.body)).perform(click());

        onView(withId(R.id.fragment_body_addButton)).perform(click());
        onView(withId(R.id.fragment_body_bodyView)).perform(click());

        //Confirm the next view has been presented
        onView(withId(R.id.buttonAddRecord)).check(matches(isDisplayed()));
        //Click Add
        onView(withId(R.id.buttonAddRecord)).perform(click());
        mainActivityTestRule.finishActivity();
    }

    /*
        Use Cases: 29
    */
    @Test
    public void testViewRecords() {
        onView(withId(R.id.fragment_list_mainListView)).check(matches(isDisplayed()));

        onData((anything())).inAdapterView(withId(R.id.fragment_list_mainListView)).atPosition(0).perform(click());
        onView(withId(R.id.body)).perform(click());

        onView(withId(R.id.fragment_body_viewAllButton)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_body_viewAllButton)).perform(click());

        // TODO: Assert text in list view is being displayed.. is this necessary?
//        onView(withId(R.id.fragment_body_totalText)).check(matches(isDisplayed()));
        mainActivityTestRule.finishActivity();
    }
}