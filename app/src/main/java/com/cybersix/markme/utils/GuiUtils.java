/**
 * CMPUT 301 Team 24
 *
 * This will help give fullscreen capabilities to the live camera activity
 *
 * Version 0.1
 *
 * Date: 2018-11-27
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.actvity.LiveCameraActivity
 * @see com.cybersix.markme.controller.CameraPreview
 */
package com.cybersix.markme.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;

public class GuiUtils {
    public static void setFullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }
}
