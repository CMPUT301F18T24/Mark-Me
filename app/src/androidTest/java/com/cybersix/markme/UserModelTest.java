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
            assertEquals(userID, userModel.getUsername());
            assertEquals(pass, userModel.getPassword());

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
            assertEquals(userID, userModel.getUsername());
            assertEquals(pass,userModel.getPassword());
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
        fail();
    }

    @Test
    public void testSetEmail() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setEmail("joseph@world.com");
        } catch (UsernameTooShortException | InvalidEmailAddressException e) {
            fail();
        }

        // Try with an invalid email format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setEmail("josephgibberish.ca");
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
            userModel.setEmail("joseph@world.com");
            assertEquals("joseph@world.com", userModel.getEmail());
        } catch (UsernameTooShortException | InvalidEmailAddressException e) {
            fail();
        }

    }


    @Test
    public void testGetPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("123-456-7890");
            assertEquals("123-456-7890", userModel.getPhone());
        } catch (UsernameTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

    }

    @Test
    public void testSetPhoneNumber() {

        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("123-456-7890");
        } catch (UsernameTooShortException | InvalidPhoneNumberException e) {
            fail();
        }

        // Try with an invalid phone format
        try {
            UserModel userModel = new UserModel("12345678","dorsaMaster");
            userModel.setPhone("38-124-4219");
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
            userModel.setEmail("joseph@joestar.com");
            userModel.setPhone("123-456-7890");

            // Edit the model.
            userModel.setEmail("dio@oregano.com");
            userModel.setPhone("098-456-1234");
            userModel.setUsername("mynewusername");
            userModel.setPassword("itwasmyallalong");

            // Check if they're the same.
            assertEquals(userModel.getEmail(), "dio@oregano.com");
            assertEquals(userModel.getPhone(), "098-456-1234");
            assertEquals(userModel.getPassword(), "itwasmyallalong");
            assertEquals(userModel.getUsername(), "mynewusername");


        } catch (UsernameTooShortException | InvalidEmailAddressException |
                 InvalidPhoneNumberException e) {
            fail();
        }

    }

}