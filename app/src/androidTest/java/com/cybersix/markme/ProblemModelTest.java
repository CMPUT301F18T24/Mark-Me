package com.cybersix.markme;

import org.junit.Test;
import org.junit.runner.Description;

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
        assertTrue(false);
    }

    @Test
    public void testGetRecord() {
        assertTrue(false);
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
        assertTrue(false);
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
        assertTrue(false);
    }

    @Test
    public void testDateStarted() {
        assertTrue(false);
    }
}