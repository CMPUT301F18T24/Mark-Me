/**
 * Jose: This is the main activity that will display all of the records' information that has
 *       been selected by the user
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 */
package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class RecordInfoActivity extends AppCompatActivity {

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

    private int recordIdx = 0;
    //Below vars are used in async activity data returns
    private final static int REQUEST_CODE_PHOTO = 1;
    private final static int REQUEST_CODE_MAP = 2;



    // record activity will be linked with the photo gallery and being able to add photos, but the
    // user should be able to view all of the information

    // TODO: the "feebackButton" view is only visible to the care provider. Make sure there is
    // TODO: Add picture, Add Location
    //       a case for checking who the user is
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_info);
        Intent i = getIntent();
        recordIdx = i.getIntExtra("RecordIdx",0);
        selectedRecord = recordController.selectedProblemRecords.get(recordIdx);
        initAttributes();
        setListeners();
    }

    private void initAttributes(){
        recordTitleEdit = findViewById(R.id.recordTitleEdit);
        editTextDescription = findViewById(R.id.editTextDescription);
        addPhoto = findViewById(R.id.buttonAddPhoto);
        viewPhotos = findViewById(R.id.buttonViewPhotos);
        bodyLocationSpinner = findViewById(R.id.bodyLocationSpinner);
        textViewComment = findViewById(R.id.commentTextView);
        editTextComment = findViewById(R.id.editTextComment);
        /*TODO: Test this here && Reactivate this check during integration
        if(UserProfileController.getInstance().user.getUserType().toLowerCase() == "care provider"){
            editTextComment.setEnabled(true);
        } else {
            editTextComment.setEnabled(false);
        }
        */
        buttonSave = findViewById(R.id.buttonSaveChanges);
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
        bodyLocationSpinner.setAdapter(new ArrayAdapter<EBodyPart>(this,android.R.layout.simple_list_item_1,EBodyPart.values()));
        bodyLocationSpinner.setSelection(selectedRecord.getBodyLocation().getBodyPart().ordinal());

    }

    private void setListeners(){
        //TODO: Setup view photos and map button
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create overlays for screen and send in intent
                Intent i = new Intent(RecordInfoActivity.this,LiveCameraActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
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


}
