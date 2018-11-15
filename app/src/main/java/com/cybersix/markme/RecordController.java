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

import java.util.ArrayList;

public class RecordController {
    // set up the controller instance with lazy construction
    private static RecordController instance = null;
    public ArrayList<RecordModel> records;

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
            uploadFakeData();
        }
        return instance;
    }

    // TODO: This function will be in charge of filling up some fake record data for the views
    private static void uploadFakeData() {
        // This will fill up the list of records with fake records. There will be around 30 of them
    }

    public void createNewRecord(String title, String description, ArrayList<Bitmap> photos,
                                BodyLocation bodyLocation) {
        // This will need to be modified as soon as the gelocation stuff is handled
        //GeoLocationRecord location = null
        // expect bodylocation to be null sometimes

    }

    public ArrayList<RecordModel> loadRecordData(ProblemModel problemModel) {

        return problemModel.getRecords();
    }

    public void editRecord(int index, RecordModel editRecord) {

    }

    public void saveRecordData(ArrayList<RecordModel> records) {

    }

}
