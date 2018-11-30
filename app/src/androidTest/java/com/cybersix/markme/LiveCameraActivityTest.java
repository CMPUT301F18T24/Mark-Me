package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;

import com.cybersix.markme.actvity.LiveCameraActivity;
import com.cybersix.markme.controller.CameraPreview;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LiveCameraActivityTest {
    @Rule
    public ActivityTestRule<LiveCameraActivity> mActivityRule =
            new ActivityTestRule<>(LiveCameraActivity.class);

    /**
     * NOTE: These tests might fail if emulator is not focused!??!?! 3:24PM
     * Sending to Google's issue tracker.
     */

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

    /*
        Use Cases: 15
     */
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
        Intent intent = new Intent();
        intent.putExtra(LiveCameraActivity.OVERLAY_RESOURCE_ID, R.drawable.body_upright);
        LiveCameraActivity camera = mActivityRule.launchActivity(intent);

        onView(withId(R.id.overlayView))
                .check(matches(isDisplayed()));

        Drawable realDrawable = camera.getResources().getDrawable(R.drawable.body_upright);
        Bitmap realBitmap = Bitmap.createBitmap(realDrawable.getIntrinsicWidth(), realDrawable.getIntrinsicHeight(), Bitmap.Config.ALPHA_8);

        Drawable overlayDrawable = camera.getOverlayView().getDrawable();
        Bitmap overlayBitmap = Bitmap.createBitmap(overlayDrawable.getIntrinsicWidth(), overlayDrawable.getIntrinsicHeight(), Bitmap.Config.ALPHA_8);

        assertTrue(realBitmap.getByteCount() == overlayBitmap.getByteCount());
        assertTrue(0.75 == camera.getOverlayView().getAlpha());
    }
}
