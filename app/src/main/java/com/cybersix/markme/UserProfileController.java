package com.cybersix.markme;

import android.util.Log;

public class UserProfileController {

    private static UserProfileController instance = null;
    public UserModel user; // TODO: Should this be public?

    // Is the controller a singleton, or is the model a singleton?
    protected UserProfileController() {
        user = new UserModel();
    }

    // Lazy construction of instance.
    public static UserProfileController getInstance() {
        if (instance == null) {
            instance = new UserProfileController();
        }
        return instance;
    }

    // Attempts to change a user's contact information.
    // Inputs: email, phone - Contact information
    // Outputs: Returns true if contact information was successfully changed, false otherwise.
    public Boolean editContactInformation(String email, String phone) {
        try {
            user.setEmail(email);
            user.setPhone(phone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Attempts to add a user to the UserModel.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the user.
    // Outputs: Returns true if added user was successful, false otherwise.
    // TODO: This should save to the elastic search database.
    public Boolean addUser(String userID, String email, String phone, String password, String userType) {

        // Stub: Save new user to the database instead of setting the user model.
        // Also check if user exists before saving to the database. Return false if it already esists.

        try {
            user.setUserID(userID);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setUserType(userType);
            return true;
        } catch (Exception e) { // TODO: Can we handle specific exceptions?
            Log.d("Vishal_UserProfileCont", e.toString());
            return false;
        }

    }

    // Checks if the userID and corresponding password exists.
    // TODO: This should search the elasticsearch database for the user.
    // This method is a stub, the current implementation helps test user registration.
    public Boolean isUserValid(String username, String password) {

        // Stub: Search elasticSearch database for a user....

        // If the elastic search returned a result. Then confirm the password matches.
        // Note: Since we are searching by usernames exactly then we don't need to compare userIDs
        // a second time.
        if (this.user.getUserID() != null) {
            if (this.user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

}
