package com.cybersix.markme;

import android.app.Activity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.fragment.BodyFragment;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
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
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup() throws UserModel.UsernameTooShortException {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_CURRENT_USERNAME, "testtest");
        mainActivityTestRule.launchActivity(intent);

        nav = NavigationController.getInstance(mainActivityTestRule.getActivity());
        ProblemController.getInstance().createNewProblem("title","desc");
        ProblemController.getInstance().setSelectedProblem(0);
        RecordController.getInstance().getSelectedProblemRecords().add(new RecordModel("a","b"));
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

        onView(withId(R.id.fragment_body_addButton)).perform(click());
        onView(withId(R.id.fragment_body_bodyView)).perform(click());

        //Confirm the next view has been presented
        onView(withId(R.id.buttonAddRecord)).check(matches(isDisplayed()));
        //Click Add
        onView(withId(R.id.buttonAddRecord)).perform(click());
        mainActivityTestRule.getActivity().setResult(Activity.RESULT_CANCELED);
        mainActivityTestRule.getActivity().finish();
    }

    /*
        Use Cases: 29, 27
        Shows all body locations of selected part (Or all parts)
    */
    @Test
    public void testViewRecords() {

        onView(withId(R.id.fragment_body_viewAllButton)).perform(click());

        //Assert text in list view is being displayed
        onView(withId(R.id.fragment_body_totalText)).check(matches(isDisplayed()));

    }


}