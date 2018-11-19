package com.cybersix.markme;

import org.junit.Test;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ProblemModelTest {

    @Test
    public void testCreateProblem() {
        try {
            ProblemModel problem = new ProblemModel(null, null);
            fail("We should have gotton a null pointer exception");
        } catch (NullPointerException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("We should have gotten the null pointer exception");
        }
    }

    @Test
    public void testAddRecord() {
        RecordModel record = new RecordModel("","");
        ProblemModel problem = null;
        try {
            problem = new ProblemModel("", "");
        } catch (Exception e) {
            fail();
        }

        problem.addRecord(record);
        assertEquals(record, problem.getRecord(0));
    }

    @Test
    public void testGetRecord() {
        RecordModel record = new RecordModel("","");
        ProblemModel problem = null;
        try {
            problem = new ProblemModel("", "");
        } catch (Exception e) {
            fail();
        }

        assertEquals("first element should not exist", problem.getRecord(0), null);

        problem.addRecord(record);
        assertEquals("first element should exist", problem.getRecord(0), record);
        assertEquals("second element should not exist", problem.getRecord(1), null);
    }

    @Test
    public void testGetRecords() {
        // tests that all records are returned
        ProblemModel problem = null;
        try {
            problem = new ProblemModel("", "");
        } catch (Exception e) {
            fail();
        }

        for (int i = 0; i < 3; i++) {
            RecordModel record = new RecordModel(Integer.toString(i), Integer.toString(i));
            problem.addRecord(record);
        }
        ArrayList<RecordModel> records = problem.getRecords();

        int i = 0;
        for (RecordModel record : records) {
            assertEquals(record.getTitle(), Integer.toString(i));
            assertEquals(record.getDescription(), Integer.toString(i));
            i++;
        }
    }

    @Test
    public void testRemoveRecord() {
        RecordModel record = new RecordModel("","");
        ProblemModel problem = null;
        try {
            problem = new ProblemModel("", "");
        } catch (Exception e) {
            fail();
        }

        try {
            problem.removeRecord(0);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }

        problem.addRecord(record);
        assertEquals(problem.getRecord(0), record);

        problem.addRecord(record);
        assertTrue(problem.removeRecord(record));

        problem.addRecord(record);
        assertFalse(problem.removeRecord(new RecordModel("","")));
    }

    @Test
    public void testGetTitle() {
        String title = "less than 30 characters";
        String description = "less than 300 characters";
        assertTrue(title.length() <= ProblemModel.MAX_TITLE_LENGTH);

        try {
            ProblemModel problem = new ProblemModel(title, description);
            assertEquals(title, problem.getTitle());
        } catch (Exception e) {
            fail("We should have been able to get the same getTitle from the ProblemModel");
        }
    }

    @Test
    public void testSetTitle() {
        String title = "getTitle";
        String description = "description";
        ProblemModel problem = null;
        try {
            problem = new ProblemModel(title, description);
            title = "less than 30 chars";
            assertTrue(title.length() <= ProblemModel.MAX_TITLE_LENGTH);
            problem.setTitle(title);
            assertTrue(true);
        } catch (Exception e) {
            fail("Setting a character less than MAX_TITLE_LENGTH did not work.");
        }

        String longTitle = "";
        for (int i = 0; i < ProblemModel.MAX_TITLE_LENGTH + 1; i++) {
            longTitle += ".";
        }
        assertTrue(longTitle.length() > ProblemModel.MAX_TITLE_LENGTH);

        try {
            problem.setTitle(longTitle);
            assertTrue(false);
        } catch (TitleTooLongException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("The getTitle was too long, thus we should have gotten a TitleTooLongException");
        }
    }

    @Test
    public void testGetDescription() {
        String title = "less 30 characters";
        String description = "less than 300 characters";
        assertTrue(description.length() <= ProblemModel.MAX_DESCRIPTION_LENGTH);

        try {
            ProblemModel problem = new ProblemModel(title, description);
            assertEquals(description, problem.getDescription());
        } catch (Exception e) {
            fail("No exception should have occured.");
        }
    }

    @Test
    public void testSetDescription() {
        String title = "getTitle";
        String description = "description";
        ProblemModel problem = null;
        try {
            problem = new ProblemModel(title, description);
            description = "less than 300 chars";
            assertTrue(description.length() <= ProblemModel.MAX_TITLE_LENGTH);
            problem.setDescription(title);
            assertTrue(true);
        } catch (Exception e) {
            fail("No exception should have occurred");
        }

        String longDescr = "";
        for (int i = 0; i < ProblemModel.MAX_DESCRIPTION_LENGTH + 1; i++) {
            longDescr += ".";
        }
        assertTrue(longDescr.length() > ProblemModel.MAX_DESCRIPTION_LENGTH);

        try {
            problem.setDescription(longDescr);
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(true);
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testGetDateStarted() {
        String title = "getTitle";
        String description = "description";
        try {
            ProblemModel problem = new ProblemModel(title, description);
            // NOTE: Only checks for the same "yyyy-mm-dd", might not be sufficient enough
            assertEquals(problem.getDateStarted(), new Date());
        } catch (Exception e) {
            fail("No exception should have occurred");
        }
    }
}