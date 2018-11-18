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
import android.widget.Button;
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
    private Button buttonSave;

    private RecordModel selectedRecord;
    private RecordController recordController = RecordController.getInstance();

    private int recordIdx = 0;

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
    }

    private void initAttributes(){
        recordTitleEdit = findViewById(R.id.recordTitleEdit);
        editTextDescription = findViewById(R.id.editTextDescription);
        bodyLocationSpinner = findViewById(R.id.bodyLocationSpinner);
        textViewComment = findViewById(R.id.commentTextView);
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
            textViewComment.setText(selectedRecord.getComment());
        }
        bodyLocationSpinner.setAdapter(new ArrayAdapter<EBodyPart>(this,android.R.layout.simple_list_item_1,EBodyPart.values()));
        bodyLocationSpinner.setSelection(selectedRecord.getBodyLocation().getBodyPart().ordinal());


    }

    private void saveRecordEdits(){
        String title = recordTitleEdit.getText().toString();
        String desc = recordTitleEdit.getText().toString();
        EBodyPart bodyPart = (EBodyPart) bodyLocationSpinner.getSelectedItem();
        recordController.saveRecordChanges(title,desc,new BodyLocation(bodyPart), recordIdx);
    }

    private void addRecordPicture(){

    }
}
