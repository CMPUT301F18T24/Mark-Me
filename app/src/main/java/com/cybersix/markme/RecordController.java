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
 * @see com.cybersix.markme.RecordModel
 */
package com.cybersix.markme;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class RecordController {
    // set up the controller instance with lazy construction
    private static RecordController instance = null;
    public ArrayList<RecordModel> records;
    public ArrayList<RecordModel> selectedProblemRecords;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected RecordController() {
        records = new ArrayList<RecordModel>();
    }

    // Lazy construction of instance.
    public static RecordController getInstance() {
        if (instance == null) {
            instance = new RecordController();
            loadFakeData();
        }
        return instance;
    }

    // TODO: This function will be in charge of filling up some fake record data for the views
    private static void loadFakeData() {
        // This will fill up the list of records with fake records. There will be around 30 of them
        Location l = new Location("Test Body Location");
        l.setLatitude(53.5232);
        l.setLongitude(-113.5263);
        for (int i = 0; i < 30; i++) {
            String title = "Fake Record Title " + Integer.toString(i);
            String description = "Fake record descriptions for title " + Integer.toString(i);
            instance.createNewRecord(title, description, null, l, new BodyLocation(EBodyPart.LEFTARM));
        }
    }

    /**
     * This function will create a new record to add to the associated list of records to the controller
     * @param title the title of the record
     * @param description the description of the record
     * @param photos the list of photos to add for the record (optional)
     * @param bodyLocation the location on the body of the record (optional)
     */
    public RecordModel createNewRecord(String title, String description, ArrayList<Bitmap> photos, Location location,
                                BodyLocation bodyLocation) {
        // This will need to be modified as soon as the gelocation stuff is handled
        //GeoLocationRecord location = null
        // expect bodylocation to be null sometimes

        RecordModel record = new RecordModel(title, description);
        if (photos != null) {
            for (Bitmap photo : photos) {
                try {
                    record.addPhoto(photo);
                }
                catch (Exception e) {
                    // display unable to save the photos
                    String message = e.getMessage();
                    return null;
                }
            }
        }
        // set the body location
        if (bodyLocation != null) {
            record.setBodyLocation(bodyLocation);
        }
        // set the map location
        if (location != null) {
            record.setMapLocation(location);
        }
        record.setTimestamp(new Date());

        // finally add the record to the record list
        records.add(record);
        Log.d("Jose_CreateRecord", "Record successfully created");
        return record;

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
                           BodyLocation newBodyLocation, Location newLocation, String newComment) {

        // edit the record information
        RecordModel record = instance.records.get(index);
        // set the new attributes
        try {
            if (newTitle != null || newTitle != "") {
                record.setTitle(newTitle);
            }
            if (newDescription != null || newDescription != ""){
                record.setDescription(newDescription);
            }
            if (newPhotos != null) {
                for (Bitmap photo : newPhotos) {
                    record.addPhoto(photo);
                }
            }
            if (newBodyLocation != null) {
                record.setBodyLocation(newBodyLocation);
            }
            if (newLocation != null) {
                record.setMapLocation(newLocation);
            }
            if (newComment != null || newComment != "") {
                record.setComment(newComment);
            }
            // we are done. Display a complete message
            // TODO: implement a complete message for now put it in the log
            Log.d("Jose_EditRecord","Successfully edited record");
        }
        catch (Exception e) {
            // display an error for the description
            String message = e.getMessage();
            Log.d("Jose_EditRecord", message);
        }
    }

    public ArrayList<RecordModel> loadRecordData(ProblemModel problemModel) {

        return problemModel.getRecords();
    }


    public void saveRecordData(ArrayList<RecordModel> records) {

    }

    public void saveRecordChanges(String title, String desc, String comment, BodyLocation bl, int idx){
        selectedProblemRecords.get(idx).setTitle(title);
        selectedProblemRecords.get(idx).setDescription(desc);
        selectedProblemRecords.get(idx).setBodyLocation(bl);
        selectedProblemRecords.get(idx).setComment(comment);
        ProblemController.getInstance().UpdateSelectedProblemRecord(selectedProblemRecords.get(idx),idx);
    }

    public void addRecordPhoto(Bitmap b, int idx){
        try{
            selectedProblemRecords.get(idx).addPhoto(b);
            ProblemController.getInstance().AddSelectedProblemRecordPhoto(b,idx);
        } catch (TooManyPhotosException e){
            Log.d("Warning", "Too many photos. Photo not added");
        } catch (PhotoTooLargeException e){
            Log.d("Warning", "Photo too large. Photo not added");
        }
    }

}
