package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import android.os.Bundle;

import java.util.Observable;

public class UserModel extends Observable {
    public static final String USERID = "USERID";
    private String userID = null;
    private String email = null;
    private String phone = null;
    private String password = null;
    private String userType = null;

    public static final int MINIMUM_USERID_LENGTH = 8;

    // Need a default constructor to compile with Patient/Care Provider inheritance.
    public UserModel() { }

    /*added a constructor*/
    public UserModel(String userID, String password) throws UserIDTooShortException {
        setUserID(userID);

        this.password = password;
    }

    public void setUserID(String userID) throws UserIDTooShortException {
        if (userID == null) {
            userID = null;
        } else if (userID.length() < MINIMUM_USERID_LENGTH) {
            throw new UserIDTooShortException();
        } else {
            this.userID = userID;
        }

        setChanged();
        notifyObservers();
    }

    public String getUserID() {
        return userID;
    }

    // Only accept two types of user types: "patient" ir "care_provider".
    public void setUserType(String userType) throws InvalidUserTypeException {
        if (userType == null) {
            this.userType = null;
        } else if (userType.equals("patient") || userType.equals("care_provider")) {
            this.userType = userType;
        } else {
            throw new InvalidUserTypeException();
        }

        setChanged();
        notifyObservers();
    }

    public String getUserType(){
        return this.userType;
    }

    public void setEmail(String email) throws InvalidEmailAddressException {
        if (!isValidEmail(email))
            throw new InvalidEmailAddressException();

        this.email = email;

        setChanged();
        notifyObservers();
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

        setChanged();
        notifyObservers();
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

        setChanged();
        notifyObservers();
    }

    public String getPassword(){
        return this.password;
    }

    public String toString() {
        if (userID == null)
            return "null";

        return userID.toString();
    }
}

class UserIDTooShortException extends Exception {}
class InvalidEmailAddressException extends Exception {}
class InvalidPhoneNumberException extends Exception {}
class InvalidUserTypeException extends Exception {}

