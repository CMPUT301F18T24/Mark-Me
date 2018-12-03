/**
 * CMPUT 301 Team 24
 *
 * This controller will be in charge of the maintenance of the list of records to a problem for
 * a user
 *
 * Version 0.1
 *
 * Date: 2018-11-14
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.model.RecordModel
 */
package com.cybersix.markme.controller;

import android.graphics.Bitmap;

import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.BodyLocation;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.model.RecordModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RecordController {
    // set up the controller instance with lazy construction
    private static RecordController instance = null;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected RecordController() {
        DataModel.getInstance();
    }

    // Lazy construction of instance.
    public static synchronized RecordController getInstance() {
        if (instance == null) {
            instance = new RecordController();
            //loadFakeData();
        }
        return instance;
    }

    // TODO: This function will be in charge of filling up some fake record data for the views
    private static void loadFakeData() {
        // This will fill up the list of records with fake records. There will be around 30 of them
        LatLng l = new LatLng(53.5232,-113.5263);
        for (int i = 0; i < 1; i++) {
            String title = "Fake Record Title " + Integer.toString(i);
            String description = "Fake record descriptions for title " + Integer.toString(i);
            instance.createNewRecord(title, description, null, l, new BodyLocation(EBodyPart.UNLISTED));
        }
    }

    /**
     * This function will create a new record to add to the associated list of records to the controller
     * @param title the getTitle of the record
     * @param description the description of the record
     * @param photos the list of photos to add for the record (optional)
     * @param bodyLocation the location on the body of the record (optional)
     */
    public RecordModel createNewRecord(String title, String description, ArrayList<Bitmap> photos, LatLng location,
                                BodyLocation bodyLocation) {
        // This will need to be modified as soon as the gelocation stuff is handled
        //GeoLocationRecord location = null
        // expect bodylocation to be null sometimes

       RecordModel r = DataModel.getInstance().createNewRecord(title,description,photos,location,bodyLocation);
       return r;
    }

    /**
     * This function will edit the record in index i with any of the following fields
     * @param index
     * @param newTitle
     * @param newDescription
     * @param newPhotos
     * @param newBodyLocation
     * @param newLocation
     * @param newComment
     */
    public void editRecord(int index, String newTitle, String newDescription, ArrayList<Bitmap> newPhotos,
                           BodyLocation newBodyLocation, LatLng newLocation, String newComment) {
        DataModel.getInstance().editRecord(index,newTitle,newDescription,newPhotos,newBodyLocation,newLocation,newComment);
    }


    public void saveRecordChanges(String title, String desc, String comment, BodyLocation bl, int idx){
        DataModel.getInstance().saveRecordChanges(title,desc,comment,bl,idx);
    }

    public ArrayList<RecordModel> getSelectedProblemRecords(){
        return DataModel.getInstance().getSelectedProblemRecords();
    }

    public void addRecordPhoto(Bitmap b, int idx){
        DataModel.getInstance().addSelectedProblemRecordPhoto(b,idx);
    }

    public void addSelectedProblemRecordLabel(String label, int idx){
        DataModel.getInstance().addSelectedProblemRecordLabel(label, idx);
    }

    public String getSelectedProblemRecordLabel(int idx){
       return DataModel.getInstance().getSelectedProblemRecordLabel(idx);
    }

    public void addRecordLocation(LatLng loc, int idx){
        DataModel.getInstance().addRecordLocation(loc,idx);
    }

    public ArrayList<RecordModel> getAllRecords(){
        return DataModel.getInstance().getAllRecords();
    }

}
