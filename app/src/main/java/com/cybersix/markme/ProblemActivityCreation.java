package com.cybersix.markme;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProblemActivityCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_creation);

        // get the display metrics for the edit popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int activity_width = dm.widthPixels;
        int activity_height = dm.heightPixels;

        // set the layout width and height dimensions
        getWindow().setLayout((int) (activity_width*0.9), (int) (activity_height*0.9));

        // set up the layout parameters for consistency
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        // set up the buttons
        Button saveButton = (Button) findViewById(R.id.problemSaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The system will now save the information for the problem, create a new problem,
                // and go back to the main activity
                saveProblem();
            }
        });
    }

    private void saveProblem() {
        // saves the information of the problem
        // TODO: leave out the notification stuff for now as that is a WOW factor
        EditText problemTitle = (EditText) findViewById(R.id.problemTitleText);
        EditText problemDescription = (EditText) findViewById(R.id.problemDescText);
        // TODO: use this time setup
        EditText problemNotifyTime = (EditText) findViewById(R.id.problemNotifyTime);

        ProblemController instance = ProblemController.getInstance();
        instance.createNewProblem(problemTitle.getText().toString(), problemDescription.getText().toString());

        // build the alert dialog that will show that the problem was saved
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Problem Created");
        builder.setMessage("The problem has been created and is now saved");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
