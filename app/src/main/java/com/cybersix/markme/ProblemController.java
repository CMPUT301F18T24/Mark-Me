/**
 * CMPUT 301 Team 24
 *
 * This controller will be in charge of the handling of the problem information between multiple
 * views
 *
 * Version 0.1
 *
 * Date: 2018-11-14
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.ProblemModel
 * @see com.cybersix.markme.ProblemListFragment
 */
package com.cybersix.markme;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ProblemController {
    // set up the controller instance with lazy construction
    private static ProblemController instance = null;
    private static ProblemModel selectedProblem = null;
    private UserProfileController userInstance;
    public ArrayList<ProblemModel> problems;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected ProblemController() {
        problems = new ArrayList<ProblemModel>();
    }

    // Lazy construction of instance.
    public static ProblemController getInstance() {
        if (instance == null) {
            instance = new ProblemController();
            //uploadFakeData();
        }
        return instance;
    }

    // TODO: THIS IS THE FAKE DATA UPDLOAD FOR THE LIST OF PROBLEMS
    private static void uploadFakeData() {
        // This function will just load fake problems to the data
        for (int i = 0; i < 10; i++) {
            String title = "Fake Title " + Integer.toString(i);
            String Description = "This is a fake description for getTitle " + Integer.toString(i);
            instance.createNewProblem(title, Description);
        }
    }

    // set up all of the necessary public functions that this controller will go through

    /**
     * This function will create a new problem for the user that called it and will add it
     * to the problem list.
     *
     * @param title the getTitle of the problem
     * @param description the description of the problem
     * @author Jose Ramirez
     */
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

    /**
     * This function will edit one of the selected problems
     * @param index the problem index to edit
     * @param newTitle the new getTitle for the edit
     * @param newDescription the new description for the edit
     */
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

    /**
     * This function will load the problems based on the user that called this. The user information
     * is stored on the user profile controller
     * @return will return a list of problems that the user has
     */
    public void loadProblemData() {
        // TODO: test this works
        userInstance = UserProfileController.getInstance();
        try {
            instance.problems = new ElasticSearchIOController.GetProblemTask().execute(userInstance.user.getUserID()).get();

            // TODO: This is a temporary fix for the null error given with problem records from the
            // TODO: server
            for (ProblemModel problem : instance.problems) {
                if (problem.getRecords() == null){
                    problem.initializeRecordModel();
                }
            }
            Log.d("Jose-Problems", "The system successfully got problems from userID: " +
                    userInstance.user.getUserID());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will save all of the problems that the user has changed. This will be uploaded
     * from the cloud using elastic search
     * @param problems the list of problems to save
     */
    public void saveProblemData(ArrayList<ProblemModel> problems) {
        /*
        TODO: will need to add elastic search functionality. For now it will just update the current
        list of problems. THIS IS SPECIFICALLY FOR EDITS
         */
        instance.problems = problems;
    }

    public void setSelectedProblem(int index){
        selectedProblem = problems.get(index);
        //Fill record controllers selected records when new selected problem is set
        RecordController.getInstance().selectedProblemRecords = getSelectedProblemRecords();
    }

    public ArrayList<RecordModel> getSelectedProblemRecords(){
        // using the selected problem's problem ID, we get all of the records
        selectedProblem.setRecords(RecordController.getInstance().loadRecordData(selectedProblem));
        return selectedProblem.getRecords();
    }

    public ProblemModel getSelectedProblem(){
        return selectedProblem;
    }

    public void UpdateSelectedProblemRecord(RecordModel rm, int idx){
        selectedProblem.getRecord(idx).setTitle(rm.getTitle());
        selectedProblem.getRecord(idx).setDescription(rm.getDescription());
        selectedProblem.getRecord(idx).setBodyLocation(rm.getBodyLocation());
        selectedProblem.getRecord(idx).setComment(rm.getComment());
        selectedProblem.getRecord(idx).setMapLocation(rm.getMapLocation());
    }

    public void AddSelectedProblemRecordPhoto(Bitmap b, int idx){
        try{
            selectedProblem.getRecord(idx).addPhoto(b);
        } catch (TooManyPhotosException e){
            Log.d("Warning", "Too many photos. Photo not added");
        } catch (PhotoTooLargeException e){
            Log.d("Warning", "Photo too large. Photo not added");
        }
    }

}
