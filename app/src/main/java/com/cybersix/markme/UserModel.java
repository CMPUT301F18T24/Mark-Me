/**
 * CMPUT 301 Team 24
 *
 * This model stores the data of the user of the application.
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import java.util.Observable;
import io.searchbox.annotations.JestId;

public class UserModel extends Observable {


    public static final String USERID = "USERID";
    private String username = null;
    private String email = null;
    private String phone = null;
    private String password = null;
    private String userType = null;

    @JestId
    private String userID = null;

    public static final int MINIMUM_USERNAME_LENGTH = 8;

    /**
     * Need a default constructor to compile with Patient/Care Provider inheritance.
     */
    public UserModel() { }

    public UserModel(String username, String password) throws UsernameTooShortException {
        if (username.length() < MINIMUM_USERNAME_LENGTH)
            throw new UsernameTooShortException();

        this.username = username;
        this.password = password;
    }

    /**
     * Grabs the userID
     * @return The userID which is the ID of the entry in the elastic search database.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID ID of the entry in the elastic search database.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Sets the username using the string input
     * @param username
     * @throws UsernameTooShortException
     */
    public void setUsername(String username) throws UsernameTooShortException {

        if (username.length() < MINIMUM_USERNAME_LENGTH) {
            throw new UsernameTooShortException();
        } else {
            this.username = username;
        }

        setChanged();
        notifyObservers();
    }

    /**
     *
     * @return the user name
     */
    public String getUsername() {
        return username;
    }

    // Only accept two types of user types: "patient" ir "care_provider".

    /**
     * sets the user type
     * @param userType
     * @throws InvalidUserTypeException
     */
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

    /**
     * Returns the type of the user
     * @return type of the user
     */
    public String getUserType(){
        return this.userType;
    }

    /**
     * sets the email using the string given
     * @param email
     * @throws InvalidEmailAddressException
     */
    public void setEmail(String email) throws InvalidEmailAddressException {
        if (!isValidEmail(email))
            throw new InvalidEmailAddressException();

        this.email = email;

        setChanged();
        notifyObservers();
    }

    /**
     * checks for the validity of the email address
     * @param email
     * @return boolean true is the email valid, and false if invalid
     */
    static public boolean isValidEmail(String email) {
        if (email == null || !email.contains("@"))
            return false;

        return true;
    }

    /**
     * gets the email address
     * @return email
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * sets the phone as a string
     * @param phone
     * @throws InvalidPhoneNumberException
     */
    public void setPhone(String phone) throws InvalidPhoneNumberException {
        if (!isValidPhone(phone))
            throw new InvalidPhoneNumberException();

        this.phone = phone;

        setChanged();
        notifyObservers();
    }

    /**
     * checks for the validity of the phone number and makes sure it is in the right format
     * @param phone
     * @return true is phone is valid, false if invalid
     */
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

    /**
     * gets the phone number
     * @return string of the phone number
     */
    public String getPhone(){
        return this.phone;
    }

    /**
     * sets the string password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;

        setChanged();
        notifyObservers();
    }

    /**
     * gets the password
     * @return string of the password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * creates a unique id by adding the userID to username
     * @return UserID+Username as a string
     */
    public String toString() {
        if (userID == null)
            return "null";

        return userID.toString() + ": " + username.toString();
    }
}

/**
 * Throws an exception that the username is too short
 */
class UsernameTooShortException extends Exception {}

/**
 * throws an excpetoin that the email address is invalid
 */
class InvalidEmailAddressException extends Exception {}

/**
 * throws an exception the phone number is invalid
 */
class InvalidPhoneNumberException extends Exception {}

/**
 * throws an exception if the user is invalid
 */
class InvalidUserTypeException extends Exception {}

