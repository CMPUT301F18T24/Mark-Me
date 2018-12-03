/**
 * This test is to make sure the intents work as planned and that they popup for the user
 *
 * Date: 2018-11-16
 */
package com.cybersix.markme;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.actvity.ProblemCreationActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.fragment.ProblemListFragment;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ProblemListIntentTesting {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private NavigationController navigationController = null;
    private MainActivity mainActivity = null;

    @Before
    public void setup(){
        navigationController = NavigationController.getInstance(mActivityRule.getActivity());
        ProblemController.getInstance().createNewProblem("title","desc");
        ProblemController.getInstance().setSelectedProblem(0);
        ProblemController.getInstance().getSelectedProblem().getRecords().add(new RecordModel("a","v"));
        mActivityRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,new ProblemListFragment())
                .commitAllowingStateLoss();
    }

    /*
        Use case 1, 34
        Creates a problem, with notification (Unfortunately unable to test the notification popup)
     */
    @Test
    public void testProblemCreationIntent(){
        // setup
        mainActivity = mActivityRule.getActivity();
        Intent i = new Intent(mainActivity, ProblemCreationActivity.class);
        mainActivity.startActivity(i);

        // click on add Problem button
        onView(withId(R.id.problemTitleText)).perform(typeText("My Problem"));
        onView(withId(R.id.problemDescText)).perform(typeText("My Description"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.problemSaveButton)).perform(ViewActions.scrollTo());
        onView(withId(R.id.problemSaveButton)).perform(click());
    }

    /**
     * User case 4
     */
    @Test
    public void testViewListOfProblems() {
        mainActivity = mActivityRule.getActivity();
        navigationController = mainActivity.getNavigationController();
        navigationController.setSelectedItem(R.id.list);
        assertEquals(navigationController.getFragment().getClass(), ProblemListFragment.class);
    }
}
