package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import android.os.Bundle;

public class UserModel {
    public static final String USERID = "USERID";
    private String userID;
    private String email;
    private String phone;
    private String password;
    private String userType;

    public static final int MINIMUM_USERID_LENGTH = 8;

    // Need a default constructor to compile with Patient/Care Provider inheritance.
    public UserModel() { }

    /*added a constructor*/
    public UserModel(String userID, String password) throws UserIDTooShortException {
        if (userID.length() < MINIMUM_USERID_LENGTH)
            throw new UserIDTooShortException();

        this.userID = userID;
        this.password = password;
    }

    public void setUserID(String userID) { this.userID = userID; }
    public String getUserID() {
        return userID;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType(){

        return this.userType;
    }

    public void setEmail(String email) throws InvalidEmailAddressException {
        if (!isValidEmail(email))
            throw new InvalidEmailAddressException();

        this.email = email;
    }

    static public boolean isValidEmail(String email) {
        if (email == null || !email.contains("@"))
            return false;

        return true;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPhone(String phone) throws InvalidPhoneNumberException {
        if (!isValidPhone(phone))
            throw new InvalidPhoneNumberException();

        this.phone = phone;
    }

    static public boolean isValidPhone(String phone) {
        if (phone == null)
            return false;

        String[] phoneSegments = phone.split("-");
        if (phoneSegments.length != 3)
            return false;

        if (phoneSegments[0].length() != 3 || phoneSegments[1].length() != 3
                || phoneSegments[2].length() != 4)
            return false;

        for (String segment: phoneSegments) {
            try {
                Integer.parseInt(segment);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

}

class UserIDTooShortException extends Exception {}
class InvalidEmailAddressException extends Exception {}
class InvalidPhoneNumberException extends Exception {}

