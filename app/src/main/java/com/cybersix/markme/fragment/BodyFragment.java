/**
 * CMPUT 301 Team 24
 *
 * This activity displays a view of the user's body and
 * allows users to add records to their problems.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Curtis Goud, Rizwan Qureshi
 */
package com.cybersix.markme.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybersix.markme.actvity.LiveCameraActivity;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.R;
import com.cybersix.markme.actvity.RecordCreationActivity;
import com.cybersix.markme.model.RecordModel;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class BodyFragment extends Fragment {
    private class PointView extends View {

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

    private final static int REQUEST_CODE_PHOTO = 2;
    public static final int REQUEST_RECORD_INFO = 1;
    public static final String EXTRA_SELECTED_PART = "SelectedPart";
    private ImageView bodyView;
    private ImageView bodyImageView;
    private PointView point;
    private ImageButton rotateButton;
    private ImageButton addButton;
    private ImageButton viewAllButton;
    private ImageButton addBodyPhotoButton;
    private TextView totalText;
    private TextView userPromptText;
    private TextView notListedText;
    private ConstraintLayout bodyConstraintLayout;
    private ProblemController problemController = ProblemController.getInstance();
    private RecordController recordController = RecordController.getInstance();
    private boolean frontFacing = true;
    private int listedCount = 0;
    private int unlistedCount = 0;
    private boolean addingRecord = false;
    private HashMap<EBodyPart,ArrayList<RecordModel>> recordParts = new HashMap<EBodyPart,ArrayList<RecordModel>>();
    private ArrayList<HighlightView> drawnViews = new ArrayList<HighlightView>();
    private int screenHeight = 0;
    private int screenWidth = 0;

    /*
        TODO:
        1. Click to view records of body part in a record list
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_body, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GuiUtils.setFullScreen(getActivity());

        bodyView = (ImageView) getActivity().findViewById(R.id.fragment_body_bodyView);
        bodyConstraintLayout = (ConstraintLayout) getActivity().findViewById(R.id.bodyConstraintLayout);


        if(problemController.getSelectedProblem() == null){
            //Send to problem view
            NavigationController.getInstance().setSelectedItem(R.id.list);

        }

    }

    @Override
    public void onResume(){
        super.onResume();
        listedCount = 0;
        unlistedCount = 0;
        problemController = ProblemController.getInstance();
        initAttributes();
        setListeners();
        ViewTreeObserver vto = bodyConstraintLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //https://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn
                // Assistance with drawing POST measurement
                bodyConstraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                drawRecords();
                totalText.setText("Total: " + Integer.toString(listedCount));
                notListedText.setText("Unlisted: " + Integer.toString(unlistedCount));

            }
        });

    }

    private void initAttributes() {
        bodyView = (ImageView) getActivity().findViewById(R.id.fragment_body_bodyView);
        bodyImageView = (ImageView) getActivity().findViewById(R.id.fragment_body_bodyImageView);
        bodyConstraintLayout = (ConstraintLayout) getActivity().findViewById(R.id.bodyConstraintLayout);
        rotateButton = (ImageButton) getActivity().findViewById(R.id.fragment_body_rotateButton);
        addButton = (ImageButton) getActivity().findViewById(R.id.fragment_body_addButton);
        viewAllButton = (ImageButton) getActivity().findViewById(R.id.fragment_body_viewAllButton);
        addBodyPhotoButton = (ImageButton) getActivity().findViewById(R.id.fragment_body_bodyCameraButton);
        totalText = (TextView) getActivity().findViewById(R.id.fragment_body_totalText);
        notListedText = (TextView) getActivity().findViewById(R.id.fragment_body_notListedText);
        userPromptText = (TextView) getActivity().findViewById(R.id.fragment_body_userPromptText);

        //Init mapping dict
        for(EBodyPart part : EBodyPart.values()) {
            recordParts.put(part,new ArrayList<RecordModel>());
        }

        //TODO: Remove this check here
        if(problemController.getSelectedProblem() != null){
            //Add existing records to body mappings
            Log.d("records",recordController.getSelectedProblemRecords().toString());
            for(RecordModel r : recordController.getSelectedProblemRecords()){
                ArrayList<RecordModel> records = recordParts.get(r.getBodyLocation().getBodyPart());
                records.add(r);
                recordParts.put(r.getBodyLocation().getBodyPart(),records);
            }
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
        addBodyPhotoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addNewBodyPhotos();
            }
        });
        viewAllButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewRecords(null);
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
                            point = new PointView(getActivity(), null,event.getX(),event.getY());
                            bodyConstraintLayout.addView(point);
                            selectedPart = part;
                            viewRecords(selectedPart);
                        }
                    }
                    selectNewRecord(selectedPart);
                }
                return true;
            }
        });

        //We must wait for the layout to be drawn and measured to use getHeight and getWidth
        // So we listen for the API to tell us measurements are done and then we can draw
    }

    private void reverse(){
        frontFacing = !frontFacing;
        drawRecords();
        //TODO: swap back/front image
    }


    /*
        Creates new record for selected part if one is selected
     */
    private void selectNewRecord(EBodyPart selectedPart){
        if(!addingRecord){
            addingRecord = false;
            userPromptText.setVisibility(View.INVISIBLE);
            return;
        }else{
            addingRecord = false;
            userPromptText.setVisibility(View.INVISIBLE);
            Intent i = new Intent(getActivity(), RecordCreationActivity.class);
            if(selectedPart == null){
                selectedPart = EBodyPart.UNLISTED;
            }
            i.putExtra("BodyPart",selectedPart);
            startActivityForResult(i, REQUEST_RECORD_INFO);
        }
    }

    /*
        Draws an overlay for records on the screen
    */
    private void drawRecords(){
        //Clear all previously drawn views
        for(HighlightView v: drawnViews){
            bodyConstraintLayout.removeView(v);
        }
        drawnViews.clear();
        for(EBodyPart bp: recordParts.keySet()){
            if(bp != EBodyPart.UNLISTED){
                listedCount += recordParts.get(bp).size();
            } else {
                unlistedCount += recordParts.get(bp).size();
            }
            if(recordParts.get(bp).size() > 0 && bp.getFace() == frontFacing){
                //Get p1 and p2 of the body part
                PointF p1 = bp.getP1();
                PointF p2 = bp.getP2();

                //Need w/h to scale the x and y values dynamically to our screen
                if(screenHeight == 0 || screenWidth == 0) {
                    screenHeight = bodyConstraintLayout.getHeight();
                    screenWidth = bodyConstraintLayout.getWidth();
                }
                float w = screenWidth;
                float h = screenHeight;

                //Create new view and add to layout
                HighlightView highlight = new HighlightView(getActivity(), null,p1.x*w,p1.y*h,p2.x*w,p2.y*h);
                bodyConstraintLayout.addView(highlight);
                drawnViews.add(highlight);
            }
        }
    }

    /*
        Send to list view with selected records
     */
    private void viewRecords(EBodyPart selectedPart){
        //If we have records for the clicked part
        if(selectedPart==null || recordParts.get(selectedPart).size() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_SELECTED_PART, selectedPart);
            NavigationController.getInstance().switchToFragment(RecordListFragment.class,bundle);
        }
    }

    private void addNewBodyPhotos(){
        // TODO: Create overlays for screen and send in intent
        Intent i = new Intent(getActivity(),LiveCameraActivity.class);
        i.putExtra(LiveCameraActivity.OVERLAY_RESOURCE_ID,R.drawable.body_upright);
        startActivityForResult(i, REQUEST_CODE_PHOTO);
    }


    /*
        turns on/off record adding mode
     */
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_RECORD_INFO && resultCode == RESULT_OK){
            Bundle b = new Bundle();
            int index = data.getIntExtra(RecordListFragment.EXTRA_RECORD_INDEX, 0);
            b.putInt(RecordListFragment.EXTRA_RECORD_INDEX, index);
            NavigationController.getInstance().switchToFragment(RecordInfoFragment.class, b);
        } else if(requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK){
            //Bundle b = new Bundle();
            //int index = data.getIntExtra(RecordListFragment.EXTRA_RECORD_INDEX, 0);
            //b.putInt(RecordListFragment.EXTRA_RECORD_INDEX, index);
            //NavigationController.getInstance().switchToFragment(RecordInfoFragment.class, b);
            //TODO: Implement body location saving AND implement flow for front/back photo
            byte[] photo = data.getByteArrayExtra("image");
            Bitmap photoMap = BitmapFactory.decodeByteArray(photo,0, photo.length);
            bodyImageView.setImageBitmap(photoMap);
            Log.d("Returned from cam", "Ret");
        }
    }

}
