package com.cybersix.markme;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserProfileControllerTest {

    @Test
    public void testAddUniqueUser() {

        String userID = "vpTest123";
        String email = "vishal@email.com";
        String pass = "MyFirstPassword";
        String phone = "780-123-4567";
        String userType = "patient";

        try {

            UserProfileController controller = new UserProfileController();

            // Add our user to the usermodel.
            assertTrue(controller.addUser(userID, email, pass, phone, userType));

        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testCheckUser() {

        String userID = "vpTest123";
        String email = "vishal@email.com";
        String pass = "MyFirstPassword";
        String phone = "780-123-4567";
        String userType = "patient";

        try {

            UserProfileController controller = new UserProfileController();

            // Add our user to the usermodel.
            assertTrue(controller.addUser(userID, email, pass, phone, userType));

            // Check if entering valid pass and username works.
            assertTrue(controller.isUserValid(userID, pass));

        } catch (Exception e) {
            fail();
        }


    }

}
