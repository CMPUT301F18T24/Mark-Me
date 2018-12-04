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
package com.cybersix.markme.model;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import android.graphics.Bitmap;

import com.cybersix.markme.observer.UserObserver;

import java.util.Observable;

import io.searchbox.annotations.JestId;

public class UserModel extends Observable {
    public static final int MINIMUM_USERNAME_LENGTH = 8;

    private String username = null;
    private String email = null;
    private String phone = null;
    private final String type = getClass().getSimpleName();
    private Bitmap photoFront = null;
    private Bitmap photoBack = null;

    @JestId
    private String userId = null;

    /**
     * Need a default constructor to compile with Patient/Care Provider inheritance.
     */
    public UserModel() { }


    /**
     * Sets new front photo
     * @param b Bitmap of user
     */
    public void setPhotoFront(Bitmap b){ this.photoFront=b; }

    /**
     * get front user photo
     */
    public Bitmap getPhotoFront(){ return this.photoFront; }

    /**
     * Sets new back photo
     * @param b Bitmap of user
     */
    public void setPhotoBack(Bitmap b){ this.photoBack=b; }

    /**
     * get back user photo
     */
    public Bitmap getPhotoBack(){ return this.photoBack; }

    /**
     *
     * @param username Username of the user.
     * @throws UsernameTooShortException Throws exception if username is less than 8 chars.
     */
    public UserModel(String username) throws UsernameTooShortException {
        setUsername(username);
    }

    /**
     * @return The userId which is the ID of the entry in the elastic search database.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId ID of the entry in the elastic search database.
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @return The user type
     */
    public String getUserType(){
        return type;
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
     * @return Returns a string representation of the usermodel object,
     */
    public String toString() {
        if (userId == null)
            return "null";

        return username.toString() + "- Contact Information:\nPhone: " + phone + ", e-mail: " + email;
    }

    public void addView(UserObserver view) {
        addObserver(view);

        setChanged();
        notifyObservers();
    }

    public void deleteView(UserObserver view) {
        deleteObserver(view);

        setChanged();
        notifyObservers();
    }

    public static class UsernameTooShortException extends Exception {}
    public static class InvalidEmailAddressException extends Exception {}
    public static class InvalidPhoneNumberException extends Exception {}
    public static class InvalidUserTypeException extends Exception {}
}

