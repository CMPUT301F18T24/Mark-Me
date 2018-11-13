package com.cybersix.markme;

public class UserProfileController {

    private static UserProfileController instance = null;
    private UserModel user;

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

    // Attempts to add a user to the UserModel.
    // Inputs: userID, email, phone, password - User information
    //         userType - The type of the user.
    // Outputs: Returns true if added user was successful, false otherwise.
    public Boolean addUser(String userID, String email, String phone, String password, String userType) {

        try {
            user.setUserID(userID);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setUserType(userType);
            return true;
        } catch (Exception e) { // TODO: Can we handle specific exceptions?
            return false;
        }

    }

    // Checks if the userID and corresponding password exists.
    // TODO: This should search the elasticsearch database for the user.
    // This method is a stub, the current implementation helps test user registration.
    public Boolean isUserValid(String username, String password) {

        if (this.user.getUserID().equals(username) && this.user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
