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

    /**
     *
     * @param username Username of the user.
     * @param password Password of the user.
     * @throws UsernameTooShortException Throws exception if username is less than 8 chars.
     */
    public UserModel(String username, String password) throws UsernameTooShortException {
        if (username.length() < MINIMUM_USERNAME_LENGTH)
            throw new UsernameTooShortException();

        this.username = username;
        this.password = password;
    }

    /**
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
     * @param username The username of the user.
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
     * @return The username
     */
    public String getUsername() {
        return username;
    }


    /**
     * @param userType Only accepts 2 types of user: patient and care_provider
     * @throws InvalidUserTypeException Throws exception if type provided does not match required,
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
     * @return The user type
     */
    public String getUserType(){
        return this.userType;
    }

    /**
     * @param email The email of the user, must be in the format: ...@...
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
     * @param email The email to check.
     * @return Returns true if email is valid, false otherwise.
     */
    static public boolean isValidEmail(String email) {
        if (email == null || !email.contains("@"))
            return false;

        return true;
    }

    /**
     * @return The email address of the user.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * @param phone - The phone number of the user, must be in the following format: XXX-XXX-XXXX
     * @throws InvalidPhoneNumberException Throws exception if phone number does not meet the requirements.
     */
    public void setPhone(String phone) throws InvalidPhoneNumberException {
        if (!isValidPhone(phone))
            throw new InvalidPhoneNumberException();

        this.phone = phone;

        setChanged();
        notifyObservers();
    }

    /**
     * Checks if phone is valid.
     * @param phone - The phone to check.
     * @return Returns true if phone # is valid, false otherwise.
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
     * @return The phone number of the user.
     */
    public String getPhone(){
        return this.phone;
    }

    /**
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;

        setChanged();
        notifyObservers();
    }

    /**
     * @return The password of the user.
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * @return Returns a string representation of the usermodel object,
     */
    public String toString() {
        if (userID == null)
            return "null";

        return userID.toString() + ": " + username.toString();
    }
}

class UsernameTooShortException extends Exception {}
class InvalidEmailAddressException extends Exception {}
class InvalidPhoneNumberException extends Exception {}
class InvalidUserTypeException extends Exception {}

