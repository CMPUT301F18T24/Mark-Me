/**
 * Jose: This is the main activity that will display all of the records' information that has
 *       been selected by the user.
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @editor Curtis Goud
 * @see com.cybersix.markme.fragment.RecordListFragment
 */
package com.cybersix.markme.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.model.BodyLocation;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.actvity.MapSelectActivity;
import com.cybersix.markme.R;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.actvity.LiveCameraActivity;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.RecordController;
import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_OK;

public class RecordInfoFragment extends Fragment {

    private EditText recordTitleEdit;
    private EditText editTextDescription;
    private EditText editTextComment;
    private Spinner bodyLocationSpinner;
    private Button buttonSave;
    private Button viewLocation;
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
        return inflater.inflate(R.layout.fragment_record_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        recordIdx = b.getInt(RecordListFragment.EXTRA_RECORD_INDEX,-1);
        //We have a selected problem record
        if(recordIdx >= 0){
            selectedRecord = RecordController.getInstance().getSelectedProblemRecords().get(recordIdx);
        } else {
            //We have a map record
            recordIdx = b.getInt("MapRecordIdx",-1);
            selectedRecord = RecordController.getInstance().getSelectedProblemRecords().get(recordIdx);
        }
        initAttributes();
        setListeners();
    }

    private void initAttributes(){
        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText(R.string.recorf_info);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().setSelectedItem(R.id.body);
            }
        });

        recordTitleEdit = getActivity().findViewById(R.id.recordTitleEdit);
        editTextDescription = getActivity().findViewById(R.id.editTextDescription);
        addPhoto = getActivity().findViewById(R.id.buttonAddPhoto);
        viewLocation = getActivity().findViewById(R.id.buttonViewLocation);
        viewPhotos = getActivity().findViewById(R.id.buttonViewPhotos);
        bodyLocationSpinner = getActivity().findViewById(R.id.bodyLocationSpinner);
        editTextComment = getActivity().findViewById(R.id.editTextComment);
        //TODO: Test this here && Reactivate this check during integration
        if(((MainActivity) getActivity()).getUser().getUserType() == "CareProvider"){
            editTextComment.setEnabled(true);
        } else {
            editTextComment.setEnabled(false);
        }
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
                i.putExtra(LiveCameraActivity.OVERLAY_RESOURCE_ID,selectedRecord.getBodyLocation().getBodyPart().getOutlineDrawable());
                startActivityForResult(i, REQUEST_CODE_PHOTO);
            }
        });

        viewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(FullGalleryFragment.GALLERY_MODE, recordIdx);
                NavigationController.getInstance().switchToFragment(FullGalleryFragment.class, bundle);
            }
        });

        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MapSelectActivity.class);
                i.putExtra("startLoc",selectedRecord.getMapLocation());
                startActivityForResult(i, REQUEST_CODE_MAP);
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

    private void addRecordLocation(LatLng loc){
        recordController.addRecordLocation(loc,recordIdx);
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
            if(resultCode == RESULT_OK){
                LatLng loc = (LatLng) data.getParcelableExtra("locations");
                LatLng ln = new LatLng(loc.latitude,loc.longitude);
                addRecordLocation(ln);
            }
        }
    }
}
