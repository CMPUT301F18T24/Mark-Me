/**
 * This is all of the unit tests for the problem controller
 */
package com.cybersix.markme;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class ProblemControllerTest {

    @Test
    public void testCreateNewProblem() {
        // This test will ensure that a new problem has been saved properly
        ProblemController testController = new ProblemController();
        String testTitle = "Test";
        String testDescr = "Test Description";
        Date testDate = new Date();

        try {
            // Call the create problem function
            testController.createNewProblem(testTitle, testDescr);

            // Get all of the problems. (In this case there should only be one)
            ArrayList<ProblemModel> testProblems = testController.getProblems();
            ProblemModel resultProblem = testProblems.get(testProblems.size()-1);
            assertEquals(resultProblem.getDateStarted(), testDate);
            assertEquals(resultProblem.getTitle(), testTitle);
            assertEquals(resultProblem.getDescription(), testDescr);
        }
        catch (Exception e){
            assert(false);
        }



    }

    @Test
    public void testEditProblem() {
        // This test will ensure that an existing problem has been edited properly
        ProblemController testController = new ProblemController();
        // First problem
        String testTitle1 = "Test";
        String testDescr1 = "Test Description";
        //Date testDate1 = new Date();
        // Second problem
        String testTitle2 = "Test 2";
        String testDescr2 = "Test Description 2";
        //Date testDate2 = new Date();
        // Edit second problem
        String testTitleEdit = "Test 2 Edit";
        String testDescrEdit = "Test Description 2 Edit";

        // Add the new problems to the controller
        try {
            testController.createNewProblem(testTitle1, testDescr1);
            testController.createNewProblem(testTitle2, testDescr2);
        }
        catch (Exception e) {
            assert(false);
        }
        try {
            // test the edit function
            testController.editProblem(1, testTitleEdit, testDescrEdit);
            // get the problems and compare
            ArrayList<ProblemModel> testProblems = testController.getProblems();
            ProblemModel testProblem = testProblems.get(1);
            assertEquals(testProblem.getTitle(), testTitleEdit);
            assertEquals(testProblem.getDescription(), testDescrEdit);
        }
        catch  (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testLoadProblemData() {
        // This test will ensure that the correct problems that were loaded to the cloud have been
        // received
        fail("Implementation needed");
        //save for elasticsearch
    }

    @Test
    public void testSaveProblemData(){
        // This test will ensure that the problem data will save to the elastic search cloud
        fail("Implementation needed");
        // save for elastic search
    }
}
