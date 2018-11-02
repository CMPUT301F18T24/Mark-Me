package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import android.os.Bundle;

public class UserModel {

    private String userID;
    private String email;
    private String phone;
    private String password;
    private String userType;

    // Need a default constructor to compile with Patient/Care Provider inheritance.
    public UserModel() { }

    /*added a constructor*/
    public UserModel(String userID, String password) throws UserIDTooShortException {
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
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setPhone(String phone) throws InvalidPhoneNumberException {
        this.phone = phone;
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

