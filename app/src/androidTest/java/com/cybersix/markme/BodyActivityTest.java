package com.cybersix.markme;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v4.app.Fragment;

import com.cybersix.markme.BodyFragment;
import com.cybersix.markme.MainActivity;

import java.security.spec.ECField;
import java.util.List;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressKey;
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

public class BodyActivityTest {

    NavigationController nav;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        nav = NavigationController.getInstance(mainActivityTestRule.getActivity());
        ProblemController.getInstance().setSelectedProblem(0);
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,new BodyFragment())
                .commitAllowingStateLoss();
    }

    /*
        Use Cases: 7,5
     */
    @Test
    public void testAddNewRecord() {

        int numRecordsOld = RecordController.getInstance().getSelectedProblemRecords().size();
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.bodyView)).perform(click());

        //Confirm the next view has been presented
        onView(withId(R.id.buttonAddRecord)).check(matches(isDisplayed()));
        //Click Add
        onView(withId(R.id.buttonAddRecord)).perform(click());
        int numRecordsNew = RecordController.getInstance().getSelectedProblemRecords().size();

        assertTrue(numRecordsNew == numRecordsOld+1);
        mainActivityTestRule.getActivity().setResult(Activity.RESULT_CANCELED);
        mainActivityTestRule.getActivity().finish();
    }

    /*
        Use Cases: 29
    */
    @Test
    public void testViewRecords() {

        onView(withId(R.id.viewAllButton)).perform(click());

        //Assert text in list view is being displayed
        onView(withId(R.id.totalText)).check(matches(isDisplayed()));

    }


}