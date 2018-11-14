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
 */
package com.cybersix.markme;

import java.util.ArrayList;
import java.util.Date;

public class problemController {
    // set up the controller instance with lazy construction
    private static problemController instance = null;
    public ArrayList<ProblemModel> problems;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected problemController() {
        problems = new ArrayList<ProblemModel>();
    }

    // Lazy construction of instance.
    public static problemController getInstance() {
        if (instance == null) {
            instance = new problemController();
        }
        return instance;
    }

    // set up all of the necessary public functions that this controller will go through

    /**
     * This function will create a new problem for the user that called it and will add it
     * to the problem list and then will be synced with the cloud
     *
     * @param title the title of the problem
     * @param description the description of the problem
     * @param started the initial starting date of the problem
     * @author Jose Ramirez
     */
    public static void createNewProblem(String title, String description, Date started) {

    }

    /**
     * This function will edit one of the selected problems
     * @param problem the problem to edit
     */
    public static void editProblem(ProblemModel problem) {

    }

    /**
     * This function will load the problems based on the user that called this. The user information
     * is stored on the user profile controller
     * @return will return a list of problems that the user has
     */
    public static ArrayList<ProblemModel> loadProblemData() {

        return null;
    }

    /**
     * This function will save all of the problems that the user has changed. This will be uploaded
     * from the cloud using elastic search
     * @param problems the list of problems to save
     */
    public static void saveProblemData(ArrayList<ProblemModel> problems) {

    }

}
