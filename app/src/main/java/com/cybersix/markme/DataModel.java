package com.cybersix.markme;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class DataModel {
    private static DataModel instance = null;
    private ArrayList<ProblemModel> problems;
    private ProblemModel selectedProblem;

    private DataModel(){
        problems = new ArrayList<ProblemModel>();
    }

    public static DataModel getInstance(){
        if(instance == null){
            instance =  new DataModel();
        }
        return instance;
    }

    public void setSelectedProblem(int index){
        selectedProblem = problems.get(index);
    }

    public ProblemModel getSelectedProblem(){
        return selectedProblem;
    }

    public ArrayList<ProblemModel> getProblems() {
        return problems;
    }

    public void createNewProblem(String title, String description) {
        try {
            ProblemModel newProblem = new ProblemModel(title, description);
            newProblem.addRecord(new RecordModel("A","b"));
            // add the problem to the list of problems
            instance.problems.add(newProblem);
            // also add it to the server
            new ElasticSearchIOController.AddProblemTask().execute(newProblem);
        }
        catch (Exception e) {
            // display an error that the problem has too long of a getTitle
            e.printStackTrace();
        }

    }

    public void editProblem(int index, String newTitle, String newDescription) {
        // To find the problem, we compare the date as the date should be unique enough.
        ProblemModel problem = instance.problems.get(index);
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
        UserProfileController userInstance = UserProfileController.getInstance();
        try {
            instance.problems = new ElasticSearchIOController.GetProblemTask().execute(userInstance.user.getUserID()).get();
            for(ProblemModel p: instance.problems){
                ArrayList<RecordModel> rm = new ElasticSearchIOController.GetRecordTask().execute(p.getProblemID()).get();
                p.addRecords(rm);
            }
            Log.d("Jose-Problems", "The system successfully got problems from userID: " +
                    userInstance.user.getUserID());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RecordModel> getSelectedProblemRecords(){
        return selectedProblem.getRecords();
    }

    public void addSelectedProblemRecordPhoto(Bitmap b, int idx){
        try{
            selectedProblem.getRecord(idx).addPhoto(b);
        } catch (TooManyPhotosException e){
            Log.d("Warning", "Too many photos. Photo not added");
        } catch (PhotoTooLargeException e){
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
        new ElasticSearchIOController.AddRecordTask().execute(ProblemController.getInstance().getSelectedProblem());
        Log.d("Jose_CreateRecord", "Record successfully created");
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
            Log.d("Jose_EditRecord","Successfully edited record");
        }
        catch (Exception e) {
            // display an error for the description
            String message = e.getMessage();
            Log.d("Jose_EditRecord", message);
        }
    }

    public void saveRecordChanges(String title, String desc, String comment, BodyLocation bl, int idx){
        selectedProblem.getRecord(idx).setTitle(title);
        selectedProblem.getRecord(idx).setDescription(desc);
        selectedProblem.getRecord(idx).setBodyLocation(bl);
        selectedProblem.getRecord(idx).setComment(comment);
    }

    public void addRecordLocation(LatLng loc, int idx){
        selectedProblem.getRecord(idx).setMapLocation(loc);
    }

    public ArrayList<RecordModel> getAllRecords() {
        ArrayList<RecordModel> rm = new ArrayList<>();
        for(ProblemModel pm: problems){
            rm.addAll(pm.getRecords());
        }
        return rm;
    }


}
