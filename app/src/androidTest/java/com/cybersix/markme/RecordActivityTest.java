package com.cybersix.markme;

import org.junit.Test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Rule;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v4.app.Fragment;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.actvity.MapSelectActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.fragment.BodyFragment;
import com.cybersix.markme.fragment.ProblemListFragment;
import com.cybersix.markme.fragment.RecordInfoFragment;
import com.cybersix.markme.fragment.RecordListFragment;
import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
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


public class RecordActivityTest {

    NavigationController nav;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup(){
        nav = NavigationController.getInstance(mainActivityTestRule.getActivity());
        ProblemController.getInstance().createNewProblem("title","desc");
        ProblemController.getInstance().setSelectedProblem(0);
        ProblemController.getInstance().getSelectedProblem().getRecords().add(new RecordModel("a","v"));
        try {
            RecordController.getInstance().getSelectedProblemRecords().get(0).addPhoto(Bitmap.createBitmap(10,10, Bitmap.Config.ARGB_4444));
        } catch (RecordModel.TooManyPhotosException e) {
            e.printStackTrace();
        } catch (RecordModel.PhotoTooLargeException e) {
            e.printStackTrace();
        }
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,new ProblemListFragment())
                .commitAllowingStateLoss();
    }


    /*
        Adds new record and edits it
        (Both online and offline)
        Use Cases: 2, 6, 8, 20, 21
    */
    @Test
    public void testViewRecordInfo(){
        Bundle p = new Bundle();
        p.putInt(RecordListFragment.EXTRA_RECORD_INDEX,0);
        Fragment g = new RecordInfoFragment();
        g.setArguments(p);
        //Move to record info
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        UserModel fakeUser = new Patient();
        mainActivityTestRule.getActivity().setUser(fakeUser);
        onView(withId(R.id.recordTitleEdit)).check(matches(isDisplayed()));
        //Type text nd save
        onView(withId(R.id.recordTitleEdit)).perform(typeText("record"));
        onView(withId(R.id.buttonSaveChanges)).perform(click());
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

    /*
        Use Cases: 9
     */
    @Test
    public void addRecordComment(){
        Bundle p = new Bundle();
        p.putInt(RecordListFragment.EXTRA_RECORD_INDEX,0);
        Fragment g = new RecordInfoFragment();
        g.setArguments(p);
        //Move to record info
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        UserModel fakeUser = new CareProvider();
        mainActivityTestRule.getActivity().setUser(fakeUser);
        //Check map has been displayed
        try{
            onView(withId(R.id.editTextComment)).perform(typeText("editing for comment"));
        } catch (PerformException m){
            //certain devices throw exceptions with perform exception due to animations/transitions
            m.printStackTrace();
        }
        //Perform click to add location
        onView(withId(R.id.buttonSaveChanges)).perform(click());
    }

    /*
        Use Case: 11, 16
     */
    @Test
    public void viewRecordPhotos(){
        Bundle p = new Bundle();
        p.putInt(RecordListFragment.EXTRA_RECORD_INDEX,0);
        Fragment g = new RecordInfoFragment();
        g.setArguments(p);
        //Move to record info
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,g)
                .commitAllowingStateLoss();
        UserModel fakeUser = new Patient();
        mainActivityTestRule.getActivity().setUser(fakeUser);
        onView(withId(R.id.buttonViewPhotos)).perform(scrollTo());
        onView(withId(R.id.buttonViewPhotos)).perform(click());
    }
}
