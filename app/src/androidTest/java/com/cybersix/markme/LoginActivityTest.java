package com.cybersix.markme;

import com.robotium.solo.*;

import android.support.test.rule.ActivityTestRule;
import android.test.*;

import org.junit.Rule;
import org.junit.Test;


public class LoginActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


    public void setUp() throws Exception {
        solo = new Solo(loginActivityTestRule.g);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }


    @Test
    public void changeTextTest() {
        solo.waitForActivity("LoginActivity", 2000);
        //Set default small timeout to 15425 milliseconds
        Timeout.setSmallTimeout(15425);
        //Enter the text: 'test@example.com'
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.usernameText));
        solo.enterText((android.widget.EditText) solo.getView(R.id.usernameText), "wassap");
    }
}
