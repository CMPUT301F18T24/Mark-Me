package com.cybersix.markme;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserModelTest {

    private static UserModel userModel;

    @BeforeClass
    public static void setUp() throws Exception {
        userModel = new UserModel("Dorsa","dorsaMaster");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        //
    }


    @Test
    public void getUserID() {
        assertEquals(userModel.userID,userModel.getUserID());
    }

    @Test
    public void passwordExist() {
        assertTrue(userModel.passwordExist());
    }

    @Test
    public void useridExist() {
        assertNotNull(userModel.userID);
    }
}