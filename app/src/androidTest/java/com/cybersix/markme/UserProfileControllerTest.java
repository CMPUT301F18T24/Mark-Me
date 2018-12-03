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
        } catch (Exception e) {
            fail();
        }

    }

}
