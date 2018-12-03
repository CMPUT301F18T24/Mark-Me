/**
 * CMPUT 301 Team 24
 *
 * This is the data model that will hold the current instances of the selected patient, all of the
 * patients problems/records, and the selected problems. This is to be used with the record and problem
 * controllers for better data organization and management
 *
 * Version 0.1
 *
 * Date: 2018-11-25
 *
 * Copyright Notice
 * @author Curtis Goud
 * @see com.cybersix.markme.controller.RecordController
 * @see com.cybersix.markme.controller.ProblemController
 */
package com.cybersix.markme.model;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.io.GeneralIO;
import com.cybersix.markme.io.OnTaskComplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class DataModel {
    private static DataModel instance = null;
    private Patient selectedPatient = null;
    private ProblemModel selectedProblem = null;
    private GeneralIO io = GeneralIO.getInstance();
    public static final Runnable emptyRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    private Runnable onPatientSelected = emptyRunnable;

    private Runnable onRecordReady = emptyRunnable;

    private DataModel(){

    }

    public static DataModel getInstance(){
        if(instance == null){
            instance =  new DataModel();
        }
        return instance;
    }

    public void setSelectedProblem(int index){
        selectedProblem = selectedPatient.getProblems().get(index);
        io.getRecords(selectedProblem, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                ArrayList<RecordModel> records = (ArrayList<RecordModel>) result;
                selectedProblem.setRecords(records);
                Log.i("DataModel", records.size() + "");
                try {
                    onRecordReady.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setOnRecordReady(Runnable onRecordReady) {
        this.onRecordReady = onRecordReady;
    }

    public ProblemModel getSelectedProblem(){
        return selectedProblem;
    }

    public ArrayList<ProblemModel> getProblems() {
        return selectedPatient.getProblems();
    }

    public void createNewProblem(ProblemModel newProblem) {
        try {
            // add the problem to the list of problems
            selectedPatient.addProblem(newProblem);
            // also add it to the server
            io.addProblem(newProblem, GeneralIO.emptyHandler);
        }
        catch (Exception e) {
            // display an error that the problem has too long of a getTitle
            e.printStackTrace();
        }

    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(final Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
        io.getProblems(selectedPatient, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                ArrayList<ProblemModel> problems = (ArrayList<ProblemModel>) result;
                if (problems.isEmpty()) {
                    selectedPatient.setProblems(problems);
                }
                try {
                    onPatientSelected.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setOnPatientSelected(Runnable onPatientSelected) {
        this.onPatientSelected = onPatientSelected;
    }

    public void editProblem(int index, String newTitle, String newDescription) {
        // To find the problem, we compare the date as the date should be unique enough.
        ProblemModel problem = selectedPatient.getProblems().get(index);
        // set the new getTitle and description
        try {
            // does references work here? Testing will check
            problem.setTitle(newTitle);
            problem.setDescription(newDescription);
            // then we are done. Display a successful edit

        }
        catch (Exception e) {
            // diaplay and error message that the problem could not be edited
            String message = e.getMessage();
            // TODO: talk to the group about how to display the error message
        }
    }

    public void loadProblemData(){
        // TODO: test this works
//        UserProfileController userInstance = UserProfileController();
        try {
//            instance.problems = new ElasticSearchIOController.GetProblemTask().execute(userInstance.user.getUserId()).get();
            for(ProblemModel p: selectedPatient.getProblems()){
                ArrayList<RecordModel> rm = p.getRecords();
                p.addRecords(rm);
            }
//                    userInstance.user.getUserId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RecordModel> getSelectedProblemRecords(){
        try {
            return selectedProblem.getRecords();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addSelectedProblemRecordPhoto(Bitmap b, int idx){
        try{
            selectedProblem.getRecord(idx).addPhoto(b);
            io.addRecord(selectedProblem.getRecord(idx), GeneralIO.emptyHandler);
        } catch (RecordModel.TooManyPhotosException e){
            Log.d("Warning", "Too many photos. Photo not added");
        } catch (RecordModel.PhotoTooLargeException e){
            Log.d("Warning", "Photo too large. Photo not added");
        }
    }

    public RecordModel createNewRecord(String title, String description, ArrayList<Bitmap> photos, LatLng location,
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
        // instance.records.add(record); dont need this since we just update the selected problem
        instance.selectedProblem.addRecord(record);
        io.addRecord(record, GeneralIO.emptyHandler);
        return record;
    }

    public void editRecord(int index, String newTitle, String newDescription, ArrayList<Bitmap> newPhotos,
                           BodyLocation newBodyLocation, LatLng newLocation, String newComment) {

        // edit the record information
        RecordModel record = instance.getSelectedProblem().getRecord(index);
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
        }
        catch (Exception e) {
            // display an error for the description
            String message = e.getMessage();
        }

        io.addRecord(record, GeneralIO.emptyHandler);
    }

    public void saveRecordChanges(String title, String desc, String comment, BodyLocation bl, int idx){
        selectedProblem.getRecord(idx).setTitle(title);
        selectedProblem.getRecord(idx).setDescription(desc);
        selectedProblem.getRecord(idx).setBodyLocation(bl);
        selectedProblem.getRecord(idx).setComment(comment);
        io.addRecord(selectedProblem.getRecord(idx), GeneralIO.emptyHandler);
    }

    public void addRecordLocation(LatLng loc, int idx){
        selectedProblem.getRecord(idx).setMapLocation(loc);
        io.addRecord(selectedProblem.getRecord(idx), GeneralIO.emptyHandler);
    }

    public ArrayList<RecordModel> getAllRecords() {
        ArrayList<RecordModel> rm = new ArrayList<>();
        for(ProblemModel pm: selectedPatient.getProblems()){
            rm.addAll(pm.getRecords());
        }
        return rm;
    }


}
