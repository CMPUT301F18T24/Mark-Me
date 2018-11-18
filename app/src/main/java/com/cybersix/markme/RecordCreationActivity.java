package com.cybersix.markme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecordCreationActivity extends AppCompatActivity {

    EBodyPart selectedPart = null;
    private EditText recordAddTitleText;
    private EditText recordAddDescText;
    private TextView recordBodyLocation;
    private Button buttonCancelRecord;
    private Button buttonAddRecord;
    private RecordController recordController = RecordController.getInstance();
    private ProblemController problemController = ProblemController.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_creation);
        Intent prvIntent = getIntent();
        selectedPart = (EBodyPart) prvIntent.getSerializableExtra("BodyPart");
        recordBodyLocation = (TextView) findViewById(R.id.recordBodyLocation);
        recordAddTitleText = (EditText) findViewById(R.id.recordAddTitleText);
        recordAddDescText = (EditText) findViewById(R.id.recordAddDescText);

        buttonAddRecord = (Button) findViewById(R.id.buttonAddRecord);
        buttonCancelRecord = (Button) findViewById(R.id.buttonCancelRecord);

        buttonAddRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title = recordAddTitleText.getText().toString();
                String desc = recordAddDescText.getText().toString();
                BodyLocation bl = new BodyLocation(selectedPart);
                RecordModel record = recordController.createNewRecord(title,desc,null,null,bl);
                problemController.getSelectedProblem().addRecord(record);
                newRecordAlert();
            }
        });

        buttonCancelRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecordCreationActivity.this, BodyActivity.class);
                startActivity(i);
                finish();
            }
        });


        if(selectedPart != null){
            recordBodyLocation.setText("Body Location: " + selectedPart.toString());
        } else {
            recordBodyLocation.setText("Body Location: Unlisted");

        }

    }

    private void newRecordAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Record Added!");
        builder.setMessage("Would you like to add a Photo or Location to the Record?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Send to edit records instead of prev screen
                Intent i = new Intent(RecordCreationActivity.this, RecordInfoActivity.class);
                i.putExtra("RecordIdx",problemController.getSelectedProblemRecords().size() - 1);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(RecordCreationActivity.this, BodyActivity.class);
                startActivity(i);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }

}
