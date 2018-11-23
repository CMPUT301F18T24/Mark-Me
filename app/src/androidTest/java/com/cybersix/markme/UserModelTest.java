package com.cybersix.markme;

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
            assertEquals(userID, userModel.getmUsername());
            assertEquals(pass, userModel.getmPassword());

        } catch (UsernameTooShortException e) {
            fail();
        }

    }

    @Test
    public void testMinimumUserIDLength() {

        // Ensure userID is at least 8 characters.
        try {
            String userID = "12345678";
            String pass = "dorsaMaster";
            UserModel userModel = new UserModel(userID, pass);

            // Ensure the userID and password were created.
            assertEquals(userID, userModel.getmUsername());
            assertEquals(pass,userModel.getmPassword());
        } catch (UsernameTooShortException e) {
            fail();
        }

        // Also, try with less than 8 characters.
        try {
            String userID = "1234567";
            String pass = "dorsaMaster";
            UserModel userModel = new UserModel(userID, pass);

            fail(); // Shouldn't get this far
        } catch (UsernameTooShortException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testGetPasswordHash() {
        // Note: Since we have not implemented encryption algorithms, we cannot test password
        // hashes yet.
        fail("Not implemented");
    }

    @Test
    public void testSetEmail() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmEmail("joseph@world.com");
        } catch (UsernameTooShortException | InvalidEmailAddressException e) {
            fail();
        }

        // Try with an invalid email format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmEmail("josephgibberish.ca");
            fail(); // Shouldn't get this far.
        } catch (UsernameTooShortException e) {
            fail();
        } catch (InvalidEmailAddressException e){
            assertTrue(true); // Correctly caught the invalid email.
        }

    }

    @Test
    public void testGetEmail() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmEmail("joseph@world.com");
            assertEquals("joseph@world.com", userModel.getmEmail());
        } catch (UsernameTooShortException | InvalidEmailAddressException e) {
            fail();
        }

    }


    @Test
    public void testGetPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmPhone("123-456-7890");
            assertEquals("123-456-7890", userModel.getmPhone());
        } catch (UsernameTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

    }

    @Test
    public void testSetPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmPhone("123-456-7890");
        } catch (UsernameTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

        // Try with an invalid phone format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setmPhone("38-124-4219");
            fail(); // Shouldn't get this far.
        } catch (UsernameTooShortException e) {
            fail();
        } catch (InvalidPhoneNumberException e){
            assertTrue(true); // Correctly caught the invalid phone number.
        }

    }

    @Test
    public void testEditUserModel() {
        try {
            // Setup model.
            UserModel userModel = new UserModel("12345678","warudo");
            userModel.setmEmail("joseph@joestar.com");
            userModel.setmPhone("123-456-7890");

            // Edit the model.
            userModel.setmEmail("dio@oregano.com");
            userModel.setmPhone("098-456-1234");
            userModel.setmUsername("mynewusername");
            userModel.setmPassword("itwasmyallalong");

            // Check if they're the same.
            assertEquals(userModel.getmEmail(), "dio@oregano.com");
            assertEquals(userModel.getmPhone(), "098-456-1234");
            assertEquals(userModel.getmPassword(), "itwasmyallalong");
            assertEquals(userModel.getmUsername(), "mynewusername");


        } catch (UsernameTooShortException | InvalidEmailAddressException |
                 InvalidPhoneNumberException e) {
            fail();
        }

    }

}