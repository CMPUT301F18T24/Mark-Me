package com.cybersix.markme;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class BodyActivity extends AppCompatActivity {

    private ImageView bodyView;
    private DisplayMetrics dm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        bodyView = (ImageView) findViewById(R.id.bodyView);
        dm = getResources().getDisplayMetrics();
        bodyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
                    //float xdp = event.getX()*(160f/dpi); //Necessary calculation.. Scales X and Y to screen size
                    //float ydp = event.getY()*(160f/dpi);
                    int h = bodyView.getHeight();
                    int w = bodyView.getWidth();
                    Log.d("Size","h: " + h + " " + "w: " + w);

                    float xdp = 0;
                    float ydp = 0;

                    Log.d("BODY TOUCH","X: " + xdp + " " + "Y: " + ydp);
                    for(EBodyPart part : EBodyPart.values()){
                        if(xdp <= part.getX2() && xdp>=part.getX1() && ydp<=part.getY2() && ydp>=part.getY1()){
                            Log.d("BODY HIT",part.toString());
                        }
                    }
                }
                return true;
            }
        });
    }
}
