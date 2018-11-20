/**
 * Jose: This is the main activity that will display all of the records' information that has
 *       been selected by the user
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

public class RecordInfoActivity extends Fragment {

    private EditText recordTitleEdit;
    private EditText editTextDescription;
    private EditText editTextComment;
    private TextView textViewComment;
    private Spinner bodyLocationSpinner;
    private Button buttonSave;
    private Button viewPhotos;
    private Button addPhoto;

    private RecordModel selectedRecord;
    private RecordController recordController = RecordController.getInstance();

    private int recordIdx = -1;
    //Below vars are used in async activity data returns
    private final static int REQUEST_CODE_PHOTO = 1;
    private final static int REQUEST_CODE_MAP = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_record_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        recordIdx = b.getInt(RecordListFragment.EXTRA_RECORD_INDEX,-1);
        //We have a selected problem record
        if(recordIdx >= 0){
            selectedRecord = ProblemController.getInstance().getSelectedProblem().getRecord(recordIdx);
        } else {
            //We have a map record
            recordIdx = b.getInt("MapRecordIdx",-1);
            selectedRecord = RecordController.getInstance().records.get(recordIdx);
        }
        initAttributes();
        setListeners();
    }


    // record activity will be linked with the photo gallery and being able to add photos, but the
    // user should be able to view all of the information

    // TODO: the "feebackButton" view is only visible to the care provider. Make sure there is
    // TODO: Add picture, Add Location
    //       a case for checking who the user is

    private void initAttributes(){
        recordTitleEdit = getActivity().findViewById(R.id.recordTitleEdit);
        editTextDescription = getActivity().findViewById(R.id.editTextDescription);
        addPhoto = getActivity().findViewById(R.id.buttonAddPhoto);
        viewPhotos = getActivity().findViewById(R.id.buttonViewPhotos);
        bodyLocationSpinner = getActivity().findViewById(R.id.bodyLocationSpinner);
        textViewComment = getActivity().findViewById(R.id.commentTextView);
        editTextComment = getActivity().findViewById(R.id.editTextComment);
        /*TODO: Test this here && Reactivate this check during integration
        if(UserProfileController.getInstance().user.getUserType().toLowerCase() == "care_provider"){
            editTextComment.setEnabled(true);
        } else {
            editTextComment.setEnabled(false);
        }
        */
        buttonSave = getActivity().findViewById(R.id.buttonSaveChanges);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecordEdits();
            }
        });



        recordTitleEdit.setText(selectedRecord.getTitle());
        editTextDescription.setText(selectedRecord.getDescription());
        if(selectedRecord.getComment() != null && selectedRecord.getComment() != ""){
            editTextComment.setText(selectedRecord.getComment());
        }
        bodyLocationSpinner.setAdapter(new ArrayAdapter<EBodyPart>(getActivity(),android.R.layout.simple_list_item_1,EBodyPart.values()));
        bodyLocationSpinner.setSelection(selectedRecord.getBodyLocation().getBodyPart().ordinal());
    }

    private void setListeners(){
        //TODO: Setup view photos and map button
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create overlays for screen and send in intent
                Intent i = new Intent(getActivity(),LiveCameraActivity.class);
                // getIntent().putExtra()
                startActivityForResult(i, REQUEST_CODE_PHOTO);
            }
        });
    }

    private void saveRecordEdits(){
        String title = recordTitleEdit.getText().toString();
        String desc = editTextDescription.getText().toString();
        String comment = editTextComment.getText().toString();
        EBodyPart bodyPart = (EBodyPart) bodyLocationSpinner.getSelectedItem();
        recordController.saveRecordChanges(title,desc,comment,new BodyLocation(bodyPart), recordIdx);
    }

    private void addRecordPicture(Bitmap photo){
        recordController.addRecordPhoto(photo,recordIdx);
    }

    private void addRecordLocation(){
        //TODO Send to map, return with lat/long
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE_PHOTO){
            if(resultCode == RESULT_OK){
                byte[] photo = data.getByteArrayExtra("image");
                Bitmap photoMap = BitmapFactory.decodeByteArray(photo,0, photo.length);
                addRecordPicture(photoMap);
            }
        }
        else if(requestCode == REQUEST_CODE_MAP){

        }
    }

    private void newLocationAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Record Added!");
        builder.setMessage("Would you like to add a Photo or Location to the Record?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }


}
