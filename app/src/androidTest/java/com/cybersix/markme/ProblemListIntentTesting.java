/**
 * This test is to make sure the intents work as planned and that they popup for the user
 *
 * Date: 2018-11-16
 */
package com.cybersix.markme;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

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

    /**
     * Use case 1
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
        onView(withId(R.id.problemSaveButton)).perform(click());

        ProblemModel problem = ProblemController.getInstance().problems.get(ProblemController.getInstance().problems.size() - 1);
        assertEquals(problem.getTitle(), "My Problem");
        assertEquals(problem.getDescription(), "My Description");
//        fail();
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
