package com.cybersix.markme;

import org.junit.Test;

import android.content.Intent;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Rule;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v4.app.Fragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;


public class RecordActivityTest {

    NavigationController nav;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        nav = NavigationController.getInstance(mainActivityTestRule.getActivity());
        ProblemController.getInstance().createNewProblem("title","desc");
        ProblemController.getInstance().setSelectedProblem(0);
        ProblemController.getInstance().getSelectedProblemRecords().add(new RecordModel("a","v"));
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,new ProblemListFragment())
                .commitAllowingStateLoss();
    }


    /*
        Use Cases: 6, 8
    */
    @Test
    public void testViewRecordInfo(){

        //Perform click, set data values
        onView(withId(R.id.fragment_list_mainListView)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_list_mainListView)).perform(click());

        //Send to next view
        Bundle b = new Bundle();
        b.putSerializable(RecordListFragment.EXTRA_RECORD_INDEX,null);
        Fragment f = new RecordListFragment();
        f.setArguments(b);
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,f)
                .commitAllowingStateLoss();

        //Assert list view is being displayed
        onView(withId(R.id.fragment_list_mainListView)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_list_mainListView)).perform(click());

        Bundle p = new Bundle();
        p.putInt(RecordListFragment.EXTRA_RECORD_INDEX,0);
        Fragment g = new RecordInfoFragment();
        g.setArguments(p);
        //Move to record info
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        onView(withId(R.id.recordTitleEdit)).check(matches(isDisplayed()));
        //Type text nd save
        onView(withId(R.id.recordTitleEdit)).perform(typeText("record"));
        onView(withId(R.id.buttonSaveChanges)).perform(click());
    }

    @Test
    public void viewRecordPhotos(){
        fail("Implementation required");
    }

    /*
        Use cases: 17, 18
     */
    @Test
    public void viewRecordLocation(){
        Intent i = new Intent(mainActivityTestRule.getActivity(),MapSelectActivity.class);
        i.putExtra(RecordListFragment.EXTRA_RECORD_INDEX,0);
        mainActivityTestRule.getActivity().startActivity(i);
        //Check map has been displayed
        onView(withId(R.id.g_map_select)).check(matches(isDisplayed()));

        //Perform click to add location
        onView(withId(R.id.g_map_select)).perform(longClick());
    }

}