/**
 * CMPUT 301 Team 24
 *
 * This is the notification handler for setting up notifications on the user's device. This is called
 * when a user is first creating a problem
 *
 * Version 0.1
 *
 * Date: 2018-11-25
 *
 * Copyright Notice
 * @author Dorsa Nahid
 * @see com.cybersix.markme.actvity.ProblemCreationActivity
 */
package com.cybersix.markme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationHandler extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "mark_me_app";
    public static String NOTIFICATION = "Please Take the picture as scheduled!";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }
}
