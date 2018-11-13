package com.cybersix.markme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.VectorDrawable;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class BodyActivity extends AppCompatActivity {


    private class PointView extends View{

        private final float x;
        private final float y;

        public PointView(Context context, @Nullable AttributeSet ars, float x, float y){
            super(context,ars);
            this.x = x;
            this.y = y;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.RED);
            canvas.drawCircle(x,y,8,p);
        }



    }

    private ImageView bodyView;
    private PointView point;
    private ImageButton rotateButton;
    private ImageButton addButton;
    private TextView totalText;
    private TextView userPromptText;
    private TextView notListedText;
    private ImageButton viewAllButton;
    private ConstraintLayout bodyConstraintLayout;
    private DisplayMetrics dm;
    private boolean frontFacing = true;


    /*
        TODO:
        1. On click -> Create new problem at point for body location
        2. Get total ailments and display on screen
        3. Map current records to the body and draw
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        bodyView = (ImageView) findViewById(R.id.bodyView);
        bodyConstraintLayout = (ConstraintLayout) findViewById(R.id.bodyConstraintLayout);
        dm = getResources().getDisplayMetrics();
        rotateButton = (ImageButton) findViewById(R.id.rotateButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        viewAllButton = (ImageButton) findViewById(R.id.viewAllButton);
        totalText = (TextView) findViewById(R.id.totalText);
        notListedText = (TextView) findViewById(R.id.notListedText);
        userPromptText = (TextView) findViewById(R.id.userPromptText);


        rotateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                reverse();
            }

        });

        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newRecord();
            }
        });

        viewAllButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewAllRecords();
            }
        });




        bodyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){

                    int h = bodyView.getHeight();
                    int w = bodyView.getWidth();

                    float xdp = event.getX()/w; //Necessary calculation.. Scales X and Y to screen size
                    float ydp = event.getY()/h;


                    Log.d("BODY TOUCH","X: " + xdp + " " + "Y: " + ydp);
                    for(EBodyPart part : EBodyPart.values()){


                        if(xdp >= part.getP1().x && xdp<=part.getP2().x && ydp >= part.getP1().y && ydp <= part.getP2().y && part.getFace() == frontFacing){
                            Log.d("BODY HIT",part.toString());
                            bodyConstraintLayout.removeView(point);
                            point = new PointView(BodyActivity.this, null,event.getX(),event.getY());
                            bodyConstraintLayout.addView(point);
                        }
                    }
                }
                return true;
            }
        });


        drawRecords();
    }


    private void reverse(){
        frontFacing = !frontFacing;
    }

    private void drawRecords(){
        /*
            TODO: Get all records and draw/highlight areas
         */
    }

    private void viewAllRecords(){
        /*
            TODO: Get all records and send to list view
         */
    }

    private void newRecord(){
        /*
            TODO: Instantiate new record with body part and add
         */
        userPromptText.setVisibility(View.VISIBLE);

    }

}
