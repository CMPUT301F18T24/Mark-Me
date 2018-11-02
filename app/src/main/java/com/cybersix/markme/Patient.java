package com.cybersix.markme;

public class Patient extends UserModel {

    // Users are not required to have names.
//    public String getPatientName(){
//        return this.getName();
//    }

    public String getPatientID(){
        return this.getUserID();
    }
}
