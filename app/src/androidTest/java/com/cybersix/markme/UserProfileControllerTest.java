package com.cybersix.markme;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserProfileControllerTest {

    @Test
    public void testGetInstance() {
        UserProfileController controller = UserProfileController.getInstance();
        assertNotEquals(controller, null);
    }

    @Test
    public void testAddUniqueUser() {

        String userID = "vpTest123";
        String email = "vishal@email.com";
        String pass = "MyFirstPassword";
        String phone = "780-123-4567";
        String userType = "patient";

        try {

            UserProfileController controller = UserProfileController.getInstance();

            // Add our user to the usermodel.
            assertTrue(controller.addUser(userID, email, pass, phone, userType));

            // Check that adding user was successful.
            assertEquals(userID, controller.user.getUserID());
            assertEquals(email, controller.user.getEmail());
            assertEquals(pass, controller.user.getPassword());
            assertEquals(phone, controller.user.getPhone());
            assertEquals(userType, controller.user.getUserType());

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

            UserProfileController controller = UserProfileController.getInstance();

            // Add our user to the usermodel.
            assertTrue(controller.addUser(userID, email, pass, phone, userType));

            // Check if entering valid pass and username works.
            assertTrue(controller.isUserValid(userID, pass));

        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testEditContactInformation() {

        String userID = "vpTest123";
        String email = "vishal@email.com";
        String pass = "MyFirstPassword";
        String phone = "780-123-4567";
        String userType = "patient";

        String newEmail = "patel@email.com";
        String newPhone = "097-321-7654";

        try {

            UserProfileController controller = UserProfileController.getInstance();

            // Add our user to the usermodel.
            controller.addUser(userID, email, pass, phone, userType);

            // Try editing the contact information
            assertTrue(controller.editContactInformation(newEmail, newPhone));

            // Check that changing contact info was successful.
            assertEquals(newEmail, controller.user.getEmail());
            assertEquals(newPhone, controller.user.getPhone());

        } catch (Exception e) {
            fail();
        }

    }

}
