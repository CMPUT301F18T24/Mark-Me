package com.cybersix.markme;

public class Patient extends UserModel {
    public String getPatientName(){
        return this.name;
    }
    public String getPatientID(){
        return this.userID;
    }
}
