package com.cybersix.markme.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.cybersix.markme.io.ElasticSearchIO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class DataModel {
    private static DataModel instance = null;
    private Patient selectedPatient;
    private ProblemModel selectedProblem;
    private ElasticSearchIO io = ElasticSearchIO.getInstance();

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

        selectedProblem.setRecords( io.getRecords(selectedProblem) );
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
            io.addProblem(newProblem);
        }
        catch (Exception e) {
            // display an error that the problem has too long of a getTitle
            e.printStackTrace();
        }

    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;

        this.selectedPatient.setProblems( io.getProblems(this.selectedPatient) );
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
            Log.d("Jose-Problems", "The system successfully got problems from userID: "); //+
//                    userInstance.user.getUserId());
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
            io.addRecord(selectedProblem.getRecord(idx));
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
        io.addRecord( record );
//        new ElasticSearchIOController.AddRecordTask().execute(ProblemController.getInstance().getSelectedProblem());
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

        io.addRecord(record);
    }

    public void saveRecordChanges(String title, String desc, String comment, BodyLocation bl, int idx){
        selectedProblem.getRecord(idx).setTitle(title);
        selectedProblem.getRecord(idx).setDescription(desc);
        selectedProblem.getRecord(idx).setBodyLocation(bl);
        selectedProblem.getRecord(idx).setComment(comment);

        io.addRecord(selectedProblem.getRecord(idx));
    }

    public void addRecordLocation(LatLng loc, int idx){
        selectedProblem.getRecord(idx).setMapLocation(loc);
    }

    public ArrayList<RecordModel> getAllRecords() {
        ArrayList<RecordModel> rm = new ArrayList<>();
        for(ProblemModel pm: selectedPatient.getProblems()){
            rm.addAll(pm.getRecords());
        }
        return rm;
    }


}
