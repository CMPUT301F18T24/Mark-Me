/**
 * CMPUT 301 Team 24
 *
 * This list fragment will display all of the possible records associated to a problem that the user
 * has selected.
 *
 * Version 0.1
 *
 * Date: 2018-11-12
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @editor Curtis Goud
 * @see com.cybersix.markme.model.RecordModel
 * @see com.cybersix.markme.actvity.RecordCreationActivity
 */
package com.cybersix.markme.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.cybersix.markme.R;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.controller.RecordController;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListFragment extends ListFragment {
    public static final String EXTRA_RECORD_INDEX = "RecordIdx";
    private ArrayAdapter<RecordModel> recordListAdapter;
    private RecordController recordController = RecordController.getInstance();
    private ArrayList<RecordModel> recordsToDisplay = new ArrayList<>();
    private EBodyPart selectedPart;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        //Get selected part from intent
        selectedPart = (EBodyPart) args.getSerializable(BodyFragment.EXTRA_SELECTED_PART);
        ProblemModel problemModel = ProblemController.getInstance().getSelectedProblem();

        getTitle().setText(problemModel.getTitle()); // Title should be title of problem
        getDetails().setText(problemModel.getDescription()); // Detail should be problem description
        getReturnButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(ProblemListFragment.class);
            }
        });

        getTitle().setVisibility(View.VISIBLE);
        getDetails().setVisibility(View.VISIBLE);
        getAddButton().setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.fragment_list_bodyImage).setVisibility(View.GONE);
        if(selectedPart == null){
            recordsToDisplay = recordController.getSelectedProblemRecords();
        } else {
            //Below vars are for displaying the picture to label
            boolean photoExists = false;
            int photoIdx = 0;
            int i = 0;
            for(RecordModel r:recordController.getSelectedProblemRecords()){
                if(r.getBodyLocation().getBodyPart().equals(selectedPart)){
                    recordsToDisplay.add(r);
                    if(!photoExists && r.getPhotos() != null && r.getPhotos().size() > 0){
                        photoExists = true;
                        photoIdx = i;
                    }
                }
                i++;
            }
            if(photoExists){
                final int idx = photoIdx;
                getTitle().setVisibility(View.GONE);
                getDetails().setText(recordController.getSelectedProblemRecords().get(photoIdx).getLabel());
                ImageView img = getActivity().findViewById(R.id.fragment_list_bodyImage);
                img.setVisibility(View.VISIBLE);
                byte[] photo = recordController.getSelectedProblemRecords().get(photoIdx).getPhotos().get(0);
                Bitmap b = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                img.setImageBitmap(b);
                img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Please enter a label");

                        // Set up the input
                        final EditText input = new EditText(getActivity());
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO: Save to server/pull from server
                                String label = input.getText().toString();
                                getDetails().setVisibility(View.VISIBLE);
                                getDetails().setText(label);
                                RecordController.getInstance().addSelectedProblemRecordLabel(label,idx);
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                        return true;
                    }
                });
            }
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                int recordIndex = 0;
                for(int i =0; i<recordController.getSelectedProblemRecords().size(); i++){
                    if(recordController.getSelectedProblemRecords().get(i).equals(recordsToDisplay.get(position))){
                        recordIndex = i;
                    }
                }
                b.putInt(RecordListFragment.EXTRA_RECORD_INDEX, recordIndex);
                NavigationController.getInstance().switchToFragment(RecordInfoFragment.class, b);
            }
        });

         DataModel.getInstance().setOnRecordReady(update);
    }

    @Override
    public void onResume() {
        super.onResume();
//        update.run();
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            // this function will update the records to display onto the list everytime the fragemnent
            // is called
            // set the adapter for the list activity
            recordsToDisplay = new ArrayList<RecordModel>();
            if(selectedPart == null){
                recordsToDisplay = recordController.getSelectedProblemRecords();
            } else {
                for(RecordModel r:recordController.getSelectedProblemRecords()){
                    if(r.getBodyLocation().getBodyPart().equals(selectedPart)){
                        recordsToDisplay.add(r);
                    }
                }
            }
            recordListAdapter = new ArrayAdapter<RecordModel>(getActivity(), R.layout.list_item, recordsToDisplay);
            getListView().setAdapter(recordListAdapter);
            recordListAdapter.notifyDataSetChanged();
        }
    };
}
