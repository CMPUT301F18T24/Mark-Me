package com.cybersix.markme;

import com.cybersix.markme.controller.UserProfileController;
import com.cybersix.markme.model.UserModel;
import com.cybersix.markme.observer.UserObserver;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserProfileControllerTest {

    @Test
    public void testGetInstance() {
        UserProfileController controller = new UserProfileController(new UserModel());
        assertNotEquals(controller, null);
    }

    @Test
    public void testEditContactInformation() throws UserModel.UsernameTooShortException {

        String userID = "vpTest123";
        String email = "vishal@email.com";
        String pass = "MyFirstPassword";
        String phone = "780-123-4567";
        String userType = "patient";

        String newEmail = "patel@email.com";
        String newPhone = "097-321-7654";

        try {

            UserProfileController controller = new UserProfileController(new UserModel(userID));

            // Try editing the contact information
//            assertTrue(controller.modifyModel(new UserObserver(controller)).);
            // Check that changing contact info was successful.
//            assertEquals(newEmail, controller.user.getEmail());
//            assertEquals(newPhone, controller.user.getPhone());
            fail("");
        } catch (Exception e) {
            fail();
        }

    }

}
