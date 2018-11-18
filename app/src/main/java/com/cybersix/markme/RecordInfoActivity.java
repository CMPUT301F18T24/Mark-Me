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
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class RecordInfoActivity extends AppCompatActivity {

    private EditText recordTitleEdit;
    private EditText editTextDescription;
    private TextView textViewComment;
    private Spinner bodyLocationSpinner;
    private ConstraintLayout photoLayout;


    private RecordModel selectedRecord;
    private RecordController recordController = RecordController.getInstance();

    // record activity will be linked with the photo gallery and being able to add photos, but the
    // user should be able to view all of the information

    // TODO: the "feebackButton" view is only visible to the care provider. Make sure there is
    //       a case for checking who the user is
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_info);
        Intent i = getIntent();
        int recordIdx = i.getIntExtra("RecordIdx",0);
        selectedRecord = recordController.selectedProblemRecords.get(recordIdx);
        initAttributes();
    }

    private void initAttributes(){
        recordTitleEdit = findViewById(R.id.recordTitleEdit);
        editTextDescription = findViewById(R.id.editTextDescription);
        bodyLocationSpinner = findViewById(R.id.bodyLocationSpinner);
        textViewComment = findViewById(R.id.commentTextView);
        photoLayout = findViewById(R.id.photoLayout);

        recordTitleEdit.setText(selectedRecord.getTitle());
        editTextDescription.setText(selectedRecord.getDescription());
        if(selectedRecord.getComment() != null && selectedRecord.getComment() != ""){
            textViewComment.setText(selectedRecord.getComment());
        }
        bodyLocationSpinner.setAdapter(new ArrayAdapter<EBodyPart>(this,android.R.layout.simple_list_item_1,EBodyPart.values()));
        bodyLocationSpinner.setSelection(selectedRecord.getBodyLocation().getBodyPart().ordinal());
        int photoCount = photoLayout.getChildCount();
        for(int i=0; i<photoCount; i++){
            View v = photoLayout.getChildAt(i);
            //Ensure we only get the views we want
            if(v instanceof ImageView){
                //Set click listeners to add and remove photos

            }
        }

    }
}
