package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class ProblemActivityCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_creation);

        // get the display metrics for the edit popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int activity_width = dm.widthPixels;
        int activity_height = dm.heightPixels;

        // set the layout width and height dimensions
        getWindow().setLayout((int) (activity_width*0.8), (int) (activity_height*0.8));

        // set up the layout parameters for consistency
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        // set up the buttons
    }
}
