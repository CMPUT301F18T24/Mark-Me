package com.cybersix.markme;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserModelTest {

    @Test
    public void testCreateAndGetUserModel() {

        String userID = "Dorsa1234";
        String pass = "dorsaMaster";

        try {

            UserModel userModel = new UserModel(userID,pass);

            // Ensure the userID and password were created.
            assertEquals(userID, userModel.getUserID());
            assertEquals(pass, userModel.getPassword());

        } catch (UserIDTooShortException e) {

        }

    }

    @Test
    public void testMinimumUserIDLength() {

        // Ensure userID is at least 8 characters.
        try {
            String userID = "12345678";
            String pass = "dorsaMaster";
            UserModel userModel = new UserModel(userID,pass);

            // Ensure the userID and password were created.
            assertEquals(userID, userModel.getUserID());
            assertEquals(pass,userModel.getPassword());
        } catch (UserIDTooShortException e) {
            fail();
        }

        // Also, try with less than 8 characters.
        try {
            String userID = "1234567";
            String pass = "dorsaMaster";
            UserModel userModel = new UserModel(userID,pass);

            fail(); // Shouldn't get this far
        } catch (UserIDTooShortException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testGetPasswordHash() {
        // Note: Since we have not implemented encryption algorithms, we cannot test password
        // hashes yet.
        assertTrue(true);

    }

    @Test
    public void testAddEmail() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setEmail("joseph@world.com");
        } catch (UserIDTooShortException | InvalidEmailAddressException e) {
            fail();
        }

        // Try with an invalid email format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setEmail("josephgibberish.ca");
            fail(); // Shouldn't get this far.
        } catch (UserIDTooShortException e) {
            fail();
        } catch (InvalidEmailAddressException e){
            assertTrue(true); // Correctly caught the invalid email.
        }

    }

    @Test
    public void testGetEmail() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setEmail("joseph@world.com");
            assertEquals("joseph@world.com", userModel.getEmail());
        } catch (UserIDTooShortException | InvalidEmailAddressException e) {
            fail();
        }

    }

    @Test
    public void testAddPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("123-456-7890");
        } catch (UserIDTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

        // Try with an invalid phone format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("38-124-4219");
            fail(); // Shouldn't get this far.
        } catch (UserIDTooShortException e) {
            fail();
        } catch (InvalidPhoneNumberException e){
            assertTrue(true); // Correctly caught the invalid phone number.
        }

    }

    @Test
    public void testGetPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("123-456-7890");
            assertEquals("joseph@world.com", userModel.getEmail());
        } catch (UserIDTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

    }

}