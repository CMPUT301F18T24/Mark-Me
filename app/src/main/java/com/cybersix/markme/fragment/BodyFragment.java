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
import android.graphics.Point;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybersix.markme.actvity.LiveCameraActivity;
import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.model.UserModel;
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
        private final int col;

        public PointView(Context context, @Nullable AttributeSet ars, float x, float y, int col){
            super(context,ars);
            this.x = x;
            this.y = y;
            this.col=col;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(col);
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
    private Button testButton;
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

    }

    @Override
    public void onResume(){
        super.onResume();
        listedCount = 0;
        unlistedCount = 0;
        problemController = ProblemController.getInstance();
        initAttributes();
        setListeners();
        if(problemController.getSelectedProblem() == null){
            //Send to problem view
            NavigationController.getInstance().setSelectedItem(R.id.list);

        } else {
            ViewTreeObserver vto = bodyConstraintLayout.getViewTreeObserver();
            vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //https://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn
                    // Assistance with drawing POST measurement
                    bodyConstraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    setBodyImage();
                    drawRecords();
                    totalText.setText(String.format("%s%s", getString(R.string.total_frag_name), Integer.toString(listedCount)));
                    notListedText.setText(String.format("%s%s", getString(R.string.unlisted_frag_name), Integer.toString(unlistedCount)));

                }
            });
        }


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
        testButton = (Button) getActivity().findViewById(R.id.button_tests);
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
        testButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    runTests();
                } catch (Exception e){
                    e.printStackTrace();
                }
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
                            point = new PointView(getActivity(), null,event.getX(),event.getY(),Color.RED);
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
        setBodyImage();
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
        listedCount = 0;
        unlistedCount = 0;
        for(EBodyPart bp: recordParts.keySet()){
            if(bp != EBodyPart.UNLISTED){
                listedCount += recordParts.get(bp).size();
            } else {
                unlistedCount += recordParts.get(bp).size();
            }

            //Need w/h to scale the x and y values dynamically to our screen
            if(screenHeight == 0 || screenWidth == 0) {
                screenHeight = bodyConstraintLayout.getHeight();
                screenWidth = bodyConstraintLayout.getWidth();
            }
            float w = screenWidth;
            float h = screenHeight;

            if(recordParts.get(bp).size() > 0 && bp.getFace() == frontFacing && bp!=EBodyPart.HEAD){
                //Get p1 and p2 of the body part
                PointF p1 = bp.getP1();
                PointF p2 = bp.getP2();

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
        if(selectedPart==null || recordParts.get(selectedPart).size() > 0 && !addingRecord){
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_SELECTED_PART, selectedPart);
            NavigationController.getInstance().switchToFragment(RecordListFragment.class,bundle);
        }
    }

    private void addNewBodyPhotos(){
        // TODO: Create overlays for screen and send in intent
        UserModel user = ((MainActivity) getActivity()).getUser();
        if(user.getPhotoFront() != null && user.getPhotoBack() != null){
            user.setPhotoFront(null); //wipe pics for new ones
            user.setPhotoBack(null);
        }
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

    private void setBodyImage(){
        UserModel user = ((MainActivity) getActivity()).getUser();
        if(user.getPhotoFront() != null && user.getPhotoBack() != null) {
            if (frontFacing) {
                bodyImageView.setImageBitmap(user.getPhotoFront());
            } else {
                bodyImageView.setImageBitmap(user.getPhotoBack());
            }
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
            //TODO: Implement body location saving AND implement flow for front/back photo
            byte[] photo = data.getByteArrayExtra("image");
            Bitmap photoMap = BitmapFactory.decodeByteArray(photo,0, photo.length);
            UserModel user = ((MainActivity) getActivity()).getUser();
            if(user.getPhotoFront() == null){ //This triggers one time per cycle to take the back photo
                //This is challenging due to the async return from the activity
                user.setPhotoFront(photoMap);
                addNewBodyPhotos();
            }else {
                user.setPhotoBack(photoMap);
                setBodyImage();
                Log.d("Returned from cam", "Ret");
            }
        }
    }


    private void runTests() throws InterruptedException{
        ArrayList<PointView> pvList = new ArrayList<>();
        for(EBodyPart bp:EBodyPart.values()){
            if(bp != EBodyPart.HEAD && bp != EBodyPart.UNLISTED){
                PointF topLeftCorner = bp.getP1();
                PointF bottomRightCorner = bp.getP2();
                PointF topRightCorner = new PointF(bottomRightCorner.x,topLeftCorner.y);
                PointF bottomLeftCorner = new PointF(topLeftCorner.x,bottomRightCorner.y);

                float diffTop = topRightCorner.x - topLeftCorner.x;
                float diffLeft = bottomLeftCorner.y - topLeftCorner.y;
                float diffRight = bottomRightCorner.y - topRightCorner.y;
                float diffBottom = bottomRightCorner.x - bottomLeftCorner.x;

                PointF pTop0 =  new PointF(topLeftCorner.x+(diffTop/3),topLeftCorner.y); //1/3 of the line
                PointF pTop1 =  new PointF(topRightCorner.x-(diffTop/3),topLeftCorner.y); //2/3 of the line
                PointF pTop2 =  new PointF((pTop1.x+pTop0.x)/2,topLeftCorner.y-0.02f); //Just a bit past


                PointF pBot0 =  new PointF(bottomLeftCorner.x+(diffBottom/3),bottomRightCorner.y);
                PointF pBot1 =  new PointF(bottomRightCorner.x-(diffBottom/3),bottomRightCorner.y);
                PointF pBot2 =  new PointF((pBot0.x+pBot1.x)/2,bottomRightCorner.y+0.02f); //On the outside


                PointF pRight0 =  new PointF(topRightCorner.x,topRightCorner.y+(diffRight/3));
                PointF pRight1 =  new PointF(topRightCorner.x,bottomRightCorner.y-(diffRight/3));
                PointF pRight2 =  new PointF(topRightCorner.x+0.03f,(pRight0.y+pRight1.y)/2);


                PointF pLeft0 =  new PointF(bottomLeftCorner.x,topLeftCorner.y+(diffLeft/3));
                PointF pLeft1 =  new PointF(bottomLeftCorner.x,bottomLeftCorner.y-(diffLeft/3));
                PointF pLeft2 =  new PointF(bottomLeftCorner.x-0.03f,(pLeft0.y+pLeft1.y)/2);

                PointF pMiddle = new PointF((bottomLeftCorner.x+bottomRightCorner.x)/2,(topLeftCorner.y+bottomRightCorner.y)/2);


                PointView pT0 = null;
                PointView pT1 = null;
                PointView pT2 = null;

                PointView pB0 = null;
                PointView pB1 = null;
                PointView pB2 = null;

                PointView pL0 = null;
                PointView pL1 = null;
                PointView pL2 = null;

                PointView pR0 = null;
                PointView pR1 = null;
                PointView pR2 = null;

                PointView pMid = null;


                if(doCheck(pTop0, bp,true)){
                    pT0 = new PointView(getActivity(), null,pTop0.x*screenWidth,pTop0.y*screenHeight,Color.GREEN);
                } else {
                    pT0 = new PointView(getActivity(), null,pTop0.x*screenWidth,pTop0.y*screenHeight,Color.RED);
                }

                if(doCheck(pTop1, bp,true)){
                    pT1 = new PointView(getActivity(), null,pTop1.x*screenWidth,pTop1.y*screenHeight,Color.GREEN);
                } else {
                    pT1 = new PointView(getActivity(), null,pTop1.x*screenWidth,pTop1.y*screenHeight,Color.RED);
                }

                if(doCheck(pTop2, bp,false)){
                    pT2 = new PointView(getActivity(), null,pTop2.x*screenWidth,pTop2.y*screenHeight,Color.GREEN);
                } else {
                    pT2 = new PointView(getActivity(), null,pTop2.x*screenWidth,pTop2.y*screenHeight,Color.RED);
                }

                if(doCheck(pRight0, bp,true)){
                    pR0 = new PointView(getActivity(), null,pRight0.x*screenWidth,pRight0.y*screenHeight, Color.GREEN);
                } else {
                    pR0 = new PointView(getActivity(), null,pRight0.x*screenWidth,pRight0.y*screenHeight, Color.RED);
                }

                if(doCheck(pRight1, bp,true)){
                    pR1 = new PointView(getActivity(), null,pRight1.x*screenWidth,pRight1.y*screenHeight, Color.GREEN);
                } else {
                    pR1 = new PointView(getActivity(), null,pRight1.x*screenWidth,pRight1.y*screenHeight, Color.RED);
                }

                if(doCheck(pRight2, bp,false)){
                    pR2 = new PointView(getActivity(), null,pRight2.x*screenWidth,pRight2.y*screenHeight, Color.GREEN);
                } else {
                    pR2 = new PointView(getActivity(), null,pRight2.x*screenWidth,pRight2.y*screenHeight, Color.RED);
                }

                if(doCheck(pLeft0, bp,true)){
                    pL0 = new PointView(getActivity(), null,pLeft0.x*screenWidth,pLeft0.y*screenHeight,Color.GREEN);
                } else {
                    pL0 = new PointView(getActivity(), null,pLeft0.x*screenWidth,pLeft0.y*screenHeight,Color.RED);
                }

                if(doCheck(pLeft1, bp,true)){
                    pL1 = new PointView(getActivity(), null,pLeft1.x*screenWidth,pLeft1.y*screenHeight,Color.GREEN);
                } else {
                    pL1 = new PointView(getActivity(), null,pLeft1.x*screenWidth,pLeft1.y*screenHeight,Color.RED);
                }

                if(doCheck(pLeft2, bp,false)){
                    pL2 = new PointView(getActivity(), null,pLeft2.x*screenWidth,pLeft2.y*screenHeight,Color.GREEN);
                } else {
                    pL2 = new PointView(getActivity(), null,pLeft2.x*screenWidth,pLeft2.y*screenHeight,Color.RED);
                }

                if(doCheck(pBot0, bp,true)){
                    pB0 = new PointView(getActivity(), null,pBot0.x*screenWidth,pBot0.y*screenHeight,Color.GREEN);
                } else {
                    pB0 = new PointView(getActivity(), null,pBot0.x*screenWidth,pBot0.y*screenHeight,Color.RED);
                }

                if(doCheck(pBot1, bp,true)){
                    pB1 = new PointView(getActivity(), null,pBot1.x*screenWidth,pBot1.y*screenHeight,Color.GREEN);
                } else {
                    pB1 = new PointView(getActivity(), null,pBot1.x*screenWidth,pBot1.y*screenHeight,Color.RED);
                }

                if(doCheck(pBot2, bp,false)){
                    pB2 = new PointView(getActivity(), null,pBot2.x*screenWidth,pBot2.y*screenHeight,Color.GREEN);
                } else {
                    pB2 = new PointView(getActivity(), null,pBot2.x*screenWidth,pBot2.y*screenHeight,Color.RED);
                }

                if(doCheck(pMiddle, bp,true)){
                    pMid = new PointView(getActivity(), null,pMiddle.x*screenWidth,pMiddle.y*screenHeight,Color.GREEN);
                } else {
                    pMid = new PointView(getActivity(), null,pMiddle.x*screenWidth,pMiddle.y*screenHeight,Color.RED);
                }

                pvList.add(pT0);
                pvList.add(pT1);
                pvList.add(pT2); //The Outside one (should fail)

                pvList.add(pB0);
                pvList.add(pB1);
                pvList.add(pB2);

                pvList.add(pR0);
                pvList.add(pR1);
                pvList.add(pR2);

                pvList.add(pL0);
                pvList.add(pL1);
                pvList.add(pL2);

                pvList.add(pMid);
            } else if (bp == EBodyPart.HEAD){
                //TODO: 2 points on each boundary, + 1 inside, +1 outside
                PointF p1 = bp.getP1();
                PointF p2 = bp.getP2();

                PointF pTop = new PointF(0.5f,p1.y);
                PointF pBot = new PointF(0.5f,p2.y);
                PointF pLeft = new PointF(p1.x,(p2.y+p1.y)/2);
                PointF pRight = new PointF(p2.x,(p2.y+p1.y)/2);

                PointF pointTL0 = new PointF(((pLeft.x*(1f/3f))+(pTop.x*(2f/3f))),((pLeft.y*(1f/3f))+(pTop.y*(2f/3f))));
                PointF pointTL1 = new PointF(((pLeft.x*(2f/3f))+(pTop.x*(1f/3f))),((pLeft.y*(2f/3f))+(pTop.y*(1f/3f))));
                PointF pointTL2 = new PointF(((pointTL0.x+pointTL1.x)/2)-0.015f,((pointTL0.y+pointTL1.y)/2)-0.015f);

                PointF pointTR0 = new PointF(((pRight.x*(1f/3f))+(pTop.x*(2f/3f))),((pRight.y*(1f/3f))+(pTop.y*(2f/3f))));
                PointF pointTR1 = new PointF(((pRight.x*(2f/3f))+(pTop.x*(1f/3f))),((pRight.y*(2f/3f))+(pTop.y*(1f/3f))));
                PointF pointTR2 = new PointF(((pointTR0.x+pointTR1.x)/2)+0.015f,((pointTR0.y+pointTR1.y)/2)-0.015f);

                PointF pointBL0 = new PointF(((pLeft.x*(1f/3f))+(pBot.x*(2f/3f))),((pLeft.y*(1f/3f))+(pBot.y*(2f/3f))));
                PointF pointBL1 = new PointF(((pLeft.x*(2f/3f))+(pBot.x*(1f/3f))),((pLeft.y*(2f/3f))+(pBot.y*(1f/3f))));
                PointF pointBL2 = new PointF(((pointBL0.x+pointBL1.x)/2)-0.01f,((pointBL0.y+pointBL1.y)/2)+0.01f);

                PointF pointBR0 = new PointF(((pRight.x*(1f/3f))+(pBot.x*(2f/3f))),((pRight.y*(1f/3f))+(pBot.y*(2f/3f))));
                PointF pointBR1 = new PointF(((pRight.x*(2f/3f))+(pBot.x*(1f/3f))),((pRight.y*(2f/3f))+(pBot.y*(1f/3f))));
                PointF pointBR2 = new PointF(((pointBR0.x+pointBR1.x)/2)+0.01f,((pointBR0.y+pointBR1.y)/2)+0.01f);

                PointF pointMid = new PointF(((pLeft.x+pRight.x)/2),((pTop.y+pBot.y)/2));

                PointView pTL0 = null;
                PointView pTL1 = null;
                PointView pTL2 = null;

                PointView pBL0 = null;
                PointView pBL1 = null;
                PointView pBL2 = null;

                PointView pBR0 = null;
                PointView pBR1 = null;
                PointView pBR2 = null;

                PointView pTR0 = null;
                PointView pTR1 = null;
                PointView pTR2 = null;

                PointView pMid = null;



                if(doCheck(pointTL0, bp,true)){
                    pTL0 = new PointView(getActivity(), null,pointTL0.x*screenWidth,pointTL0.y*screenHeight,Color.GREEN);
                } else {
                    pTL0 = new PointView(getActivity(), null,pointTL0.x*screenWidth,pointTL0.y*screenHeight,Color.RED);
                }

                if(doCheck(pointTL1, bp,true)){
                    pTL1 = new PointView(getActivity(), null,pointTL1.x*screenWidth,pointTL1.y*screenHeight,Color.GREEN);
                } else {
                    pTL1 = new PointView(getActivity(), null,pointTL1.x*screenWidth,pointTL1.y*screenHeight,Color.RED);
                }

                if(doCheck(pointTL2, bp,false)){
                    pTL2 = new PointView(getActivity(), null,pointTL2.x*screenWidth,pointTL2.y*screenHeight,Color.GREEN);
                } else {
                    pTL2 = new PointView(getActivity(), null,pointTL2.x*screenWidth,pointTL2.y*screenHeight,Color.RED);
                }

                if(doCheck(pointTR0, bp,true)){
                    pTR0 = new PointView(getActivity(), null,pointTR0.x*screenWidth,pointTR0.y*screenHeight,Color.GREEN);
                } else {
                    pTR0 = new PointView(getActivity(), null,pointTR0.x*screenWidth,pointTR0.y*screenHeight,Color.RED);
                }

                if(doCheck(pointTR1, bp,true)){
                    pTR1 = new PointView(getActivity(), null,pointTR1.x*screenWidth,pointTR1.y*screenHeight,Color.GREEN);
                } else {
                    pTR1 = new PointView(getActivity(), null,pointTR1.x*screenWidth,pointTR1.y*screenHeight,Color.RED);
                }

                if(doCheck(pointTR2, bp,false)){
                    pTR2 = new PointView(getActivity(), null,pointTR2.x*screenWidth,pointTR2.y*screenHeight,Color.GREEN);
                } else {
                    pTR2 = new PointView(getActivity(), null,pointTR2.x*screenWidth,pointTR2.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBL0, bp,true)){
                    pBL0 = new PointView(getActivity(), null,pointBL0.x*screenWidth,pointBL0.y*screenHeight,Color.GREEN);
                } else {
                    pBL0 = new PointView(getActivity(), null,pointBL0.x*screenWidth,pointBL0.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBL1, bp,true)){
                    pBL1 = new PointView(getActivity(), null,pointBL1.x*screenWidth,pointBL1.y*screenHeight,Color.GREEN);
                } else {
                    pBL1 = new PointView(getActivity(), null,pointBL1.x*screenWidth,pointBL1.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBL2, bp,false)){
                    pBL2 = new PointView(getActivity(), null,pointBL2.x*screenWidth,pointBL2.y*screenHeight,Color.GREEN);
                } else {
                    pBL2 = new PointView(getActivity(), null,pointBL2.x*screenWidth,pointBL2.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBR0, bp,true)){
                    pBR0 = new PointView(getActivity(), null,pointBR0.x*screenWidth,pointBR0.y*screenHeight,Color.GREEN);
                } else {
                    pBR0 = new PointView(getActivity(), null,pointBR0.x*screenWidth,pointBR0.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBR1, bp,true)){
                    pBR1 = new PointView(getActivity(), null,pointBR1.x*screenWidth,pointBR1.y*screenHeight,Color.GREEN);
                } else {
                    pBR1 = new PointView(getActivity(), null,pointBR1.x*screenWidth,pointBR1.y*screenHeight,Color.RED);
                }

                if(doCheck(pointBR2, bp,false)){
                    pBR2 = new PointView(getActivity(), null,pointBR2.x*screenWidth,pointBR2.y*screenHeight,Color.GREEN);
                } else {
                    pBR2 = new PointView(getActivity(), null,pointBR2.x*screenWidth,pointBR2.y*screenHeight,Color.RED);
                }

                if(doCheck(pointMid, bp,true)){
                    pMid = new PointView(getActivity(), null,pointMid.x*screenWidth,pointMid.y*screenHeight,Color.GREEN);
                } else {
                    pMid = new PointView(getActivity(), null,pointMid.x*screenWidth,pointMid.y*screenHeight,Color.RED);
                }

                pvList.add(pTL0);
                pvList.add(pTL1);
                pvList.add(pTL2);

                pvList.add(pTR0);
                pvList.add(pTR1);
                pvList.add(pTR2);

                pvList.add(pBL0);
                pvList.add(pBL1);
                pvList.add(pBL2);

                pvList.add(pBR0);
                pvList.add(pBR1);
                pvList.add(pBR2);

                pvList.add(pMid);
            }
        }

        final Handler handler = new Handler();
        final int delay = 80; //milliseconds
        final ArrayList<PointView> pvFinal = pvList;
        handler.postDelayed(new Runnable(){
            int currentIdx = 0;
            public void run(){
                if(currentIdx < pvFinal.size()){
                    bodyConstraintLayout.addView(pvFinal.get(currentIdx));
                    currentIdx++;
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }

    private boolean doCheck(PointF pt, EBodyPart part, boolean expectedR){
        if(expectedR){ //I.e This should pass
            return pt.x >= part.getP1().x && pt.x<=part.getP2().x && pt.y >= part.getP1().y && pt.y <= part.getP2().y;
        } else { //This should not pass
            return !(pt.x >= part.getP1().x && pt.x<=part.getP2().x && pt.y >= part.getP1().y && pt.y <= part.getP2().y);
        }
    }

}
