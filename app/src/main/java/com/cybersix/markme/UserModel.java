package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import java.util.Observable;
import io.searchbox.annotations.JestId;

public class UserModel extends Observable {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String userType;

    @JestId
    private String userID;

    public static final int MINIMUM_USERNAME_LENGTH = 8;

    // Need a default constructor to compile with Patient/Care Provider inheritance.
    public UserModel() { }

    /*added a constructor*/
    public UserModel(String username, String password) throws UsernameTooShortException {
        if (username.length() < MINIMUM_USERNAME_LENGTH)
            throw new UsernameTooShortException();

        this.username = username;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) throws UsernameTooShortException {

        if (username.length() < MINIMUM_USERNAME_LENGTH) {
            throw new UsernameTooShortException();
        } else {
            this.username = username;
        }

        setChanged();
        notifyObservers();

    }

    public String getUsername() {
        return username;
    }

    // Only accept two types of user types: "patient" ir "care_provider".
    public void setUserType(String userType) throws InvalidUserTypeException {

        if (userType.equals("patient") || userType.equals("care_provider")) {
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

}

class UsernameTooShortException extends Exception {}
class InvalidEmailAddressException extends Exception {}
class InvalidPhoneNumberException extends Exception {}
class InvalidUserTypeException extends Exception {}

