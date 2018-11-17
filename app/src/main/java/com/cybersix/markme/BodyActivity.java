package com.cybersix.markme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.VectorDrawable;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

//Note: https://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn
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

    private class HighlightView extends View{

        private final float x1;
        private final float x2;
        private final float y1;
        private final float y2;


        public HighlightView(Context context, @Nullable AttributeSet ars, float x1, float y1, float x2, float y2){
            super(context,ars);
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.argb(35,0,0,255));
            p.setStrokeWidth(12);
            canvas.drawRect(new RectF(x1,y1,x2,y2),p);
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
    private RecordController recordController;
    private DisplayMetrics dm;
    private BottomNavigationView bnv;
    private boolean frontFacing = true;
    private int listedCount = 0;
    private int unlistedCount = 0;
    private boolean addingRecord = false;
    private HashMap<EBodyPart,ArrayList<RecordModel>> recordParts = new HashMap<EBodyPart,ArrayList<RecordModel>>();



    /*
        TODO:
        1. Click to view records of body part in a record list
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    return true;
                case R.id.gps:
                    return true;
                case R.id.body:
                    return true;
                case R.id.gallery:
                    return true;
                case R.id.list:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listedCount = 0;
        unlistedCount = 0;
        recordController = RecordController.getInstance();
        initAttributes();
        setListeners();

    }

    private void initAttributes() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bodyView = (ImageView) findViewById(R.id.bodyView);
        bodyConstraintLayout = (ConstraintLayout) findViewById(R.id.bodyConstraintLayout);
        dm = getResources().getDisplayMetrics();
        rotateButton = (ImageButton) findViewById(R.id.rotateButton);
        bnv = (BottomNavigationView) findViewById(R.id.navigation);
        addButton = (ImageButton) findViewById(R.id.addButton);
        viewAllButton = (ImageButton) findViewById(R.id.viewAllButton);
        totalText = (TextView) findViewById(R.id.totalText);
        notListedText = (TextView) findViewById(R.id.notListedText);
        userPromptText = (TextView) findViewById(R.id.userPromptText);

        //Init mapping dict
        recordParts.put(null,new ArrayList<RecordModel>());
        for(EBodyPart part : EBodyPart.values()) {
            recordParts.put(part,new ArrayList<RecordModel>());
        }

        //Add existing records to body mappings
        for(RecordModel r : recordController.records){
            ArrayList<RecordModel> records = recordParts.get(r.getBodyLocation().getBodyPart());
            records.add(r);
            recordParts.put(r.getBodyLocation().getBodyPart(),records);
        }
    }

    private void setListeners(){
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

                    EBodyPart selectedPart = null;

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
                            selectedPart = part;
                        }
                    }
                    selectNewRecord(selectedPart);
                }
                return true;
            }
        });

        //We must wait for the layout to be drawn and measured to use getHeight and getWidth
        // So we listen for the API to tell us measurements are done and then we can draw
        ViewTreeObserver vto = bodyConstraintLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bodyConstraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawRecords();
                totalText.setText("Total: " + Integer.toString(listedCount));
                notListedText.setText("Not Listed: " + Integer.toString(unlistedCount));
            }
        });
    }

    private void reverse(){
        frontFacing = !frontFacing;
    }

    private void selectNewRecord(EBodyPart selectedPart){
        if(selectedPart == null || !addingRecord){
            addingRecord = false;
            userPromptText.setVisibility(View.INVISIBLE);
            return;
        }else{
            addingRecord = false;
            userPromptText.setVisibility(View.INVISIBLE);
            Intent i = new Intent(this, RecordCreationActivity.class);
            i.putExtra("BodyPart",selectedPart);
            startActivity(i);
            finish();
        }
    }

    private void drawRecords(){
        /*
            TODO: Get all records and draw/highlight areas
         */
        for(EBodyPart bp: recordParts.keySet()){
            listedCount += recordParts.get(bp).size();
            if(bp != null && recordParts.get(bp).size() > 0){
                //Get p1 and p2 of the body part
                PointF p1 = bp.getP1();
                PointF p2 = bp.getP2();

                //Need w/h to scale the x and y values dynamically to our screen
                float h = bodyView.getHeight();
                float w = bodyView.getWidth();

                //Create new view and add to layout
                HighlightView highlight = new HighlightView(BodyActivity.this, null,p1.x*w,p1.y*h,p2.x*w,p2.y*h);
                bodyConstraintLayout.addView(highlight);
            } else {
                unlistedCount += recordParts.get(bp).size();
            }
        }
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
        if(!addingRecord){
            userPromptText.setVisibility(View.VISIBLE);
            addingRecord = true;
        } else {
            userPromptText.setVisibility(View.INVISIBLE);
            addingRecord = false;
        }

    }

}
