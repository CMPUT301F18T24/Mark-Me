package com.cybersix.markme;

import org.junit.Test;
import org.junit.runner.Description;

import java.util.Date;

import static org.junit.Assert.*;

public class ProblemModelTest {
    @Test
    public void createProblem() {
        try {
            ProblemModel problem = new ProblemModel(null, null);
            assertTrue(false);
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddRecord() {
        RecordModel record = new RecordModel();
        ProblemModel problem = null;
        try {
            problem = new ProblemModel("", "");
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
        }

        problem.addRecord(record);
        assertEquals(record, problem.getRecord(0));
    }

    @Test
    public void testGetRecord() {

    }

    @Test
    public void testGetTitle() {
        String title = "less than 30 characters";
        String description = "less than 300 characters";
        assertTrue(title.length() <= ProblemModel.MAX_TITLE_LENGTH);

        try {
            ProblemModel problem = new ProblemModel(title, description);
            assertEquals(title, problem.getTitle());
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testSetTitle() {
        String title = "title";
        String description = "description";
        ProblemModel problem = null;
        try {
            problem = new ProblemModel(title, description);
            title = "less than 30 chars";
            assertTrue(title.length() <= ProblemModel.MAX_TITLE_LENGTH);
            problem.setTitle(title);
            assertTrue(true);
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
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
        } catch (NullPointerException e) {
            assertTrue(false);
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
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testSetDescription() {
        String title = "title";
        String description = "description";
        ProblemModel problem = null;
        try {
            problem = new ProblemModel(title, description);
            description = "less than 300 chars";
            assertTrue(description.length() <= ProblemModel.MAX_TITLE_LENGTH);
            problem.setDescription(title);
            assertTrue(true);
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
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
            assertTrue(false);
        }
    }

    @Test
    public void testGetDateStarted() {
        String title = "title";
        String description = "description";
        try {
            ProblemModel problem = new ProblemModel(title, description);
            // NOTE: Only checks for the same "yyyy-mm-dd", might not be sufficient enough
            assertEquals(problem.getDateStarted(), new Date());
        } catch (TitleTooLongException e) {
            assertTrue(false);
        } catch (DescriptionTooLongException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(false);
        }
    }
}