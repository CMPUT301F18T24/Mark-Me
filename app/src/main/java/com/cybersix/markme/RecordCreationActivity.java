package com.cybersix.markme;

import android.content.Intent;
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
                recordController.createNewRecord(title,desc,null,null,bl);
                finish();
            }
        });

        buttonCancelRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recordBodyLocation.setText("Body Location: " + selectedPart.toString());

    }
}
