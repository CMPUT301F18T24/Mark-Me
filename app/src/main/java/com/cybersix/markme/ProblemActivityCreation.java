package com.cybersix.markme;

    import android.app.AlarmManager;
    import android.app.Notification;
    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.media.RingtoneManager;
    import android.os.Build;
    import android.os.SystemClock;
    import android.support.v4.app.NotificationCompat;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.DisplayMetrics;
    import android.view.Gravity;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.TextView;

    import org.w3c.dom.Text;

public class ProblemActivityCreation extends AppCompatActivity {
    private String consistency;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_creation);
        createNotificationChannel();
        GuiUtils.setFullScreen(this);
        consistency = "n";


        // get the display metrics for the edit popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int activity_width = dm.widthPixels;
        int activity_height = dm.heightPixels;

        // set the layout width and height dimensions
        getWindow().setLayout((int) (activity_width*0.9), (int) (activity_height*0.9));

        // set up the layout parameters for consistency
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        // set up the buttons
        Button saveButton = (Button) findViewById(R.id.problemSaveButton);
        RadioGroup radioGroup = findViewById(R.id.reminder);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The system will now save the information for the problem, create a new problem,
                // and go back to the main activity
                saveProblem();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                consistency = String.valueOf(radioButton.getTag());
            }
        }) ;
    }

    private void saveProblem() {
        // saves the information of the problem
        // TODO: leave out the notification stuff for now as that is a WOW factor
        EditText problemTitle = (EditText) findViewById(R.id.problemTitleText);
        EditText problemDescription = (EditText) findViewById(R.id.problemDescText);
//        EditText problemNotifyTime = (EditText) findViewById(R.id.problemNotifyTime);

        ProblemController instance = ProblemController.getInstance();
        instance.createNewProblem(problemTitle.getText().toString(), problemDescription.getText().toString());

        // build the alert dialog that will show that the problem was saved
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Problem Created");
        builder.setMessage("The problem has been created and is now saved");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        if(consistency.equalsIgnoreCase("d")){
            scheduleNotification(this, 86400000, 12);
        }else if(consistency.equalsIgnoreCase("b")){
            scheduleNotification(this, 172800000, 12);

        }else if(consistency.equalsIgnoreCase("w")){
            scheduleNotification(this, 604800000, 12);
        }
        dialog.show();


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationHandler.NOTIFICATION_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleNotification(Context context, long delay, int notificationId) {
        //delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NotificationHandler.NOTIFICATION_ID)
                .setContentTitle("Mark Me")
                .setContentText("Please Take a Picture!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo_transparent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, LiveCameraActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationHandler.class);
        notificationIntent.putExtra(NotificationHandler.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationHandler.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
