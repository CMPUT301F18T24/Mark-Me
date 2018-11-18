package com.cybersix.markme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LiveCameraActivityTest {
    @Rule
    public ActivityTestRule<LiveCameraActivity> mActivityRule =
            new ActivityTestRule<>(LiveCameraActivity.class);

    @Test
    public void testCameraDeviceToggle() {
        CameraPreview cameraPreview = mActivityRule.getActivity().getCameraPreview();
        assertEquals(cameraPreview.getCurrentView(), CameraPreview.CAMERA_FRONT_VIEW);

        onView(withId(R.id.toggleCameraButton))
                .perform(click())
                .check(matches(withText("FRONT")));
        assertEquals(cameraPreview.getCurrentView(), CameraPreview.CAMERA_FACE_VIEW);

        onView(withId(R.id.toggleCameraButton))
                .perform(click())
                .check(matches(withText("FACE")));
        assertEquals(cameraPreview.getCurrentView(), CameraPreview.CAMERA_FRONT_VIEW);
    }

    @Test
    public void testPhotoIsTaken() {
        onView(withId(R.id.captureCameraButton))
            .perform(click());
        byte[] data = mActivityRule
                .getActivityResult()
                .getResultData()
                .getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        assertNotNull(bitmap); // successfully able to decode back into a bitmap image
    }

    @Test
    public void testOverlayDisplayed() {
        fail("Implementation required");
    }
}
