package com.cybersix.markme;

import java.util.ArrayList;

public class CareProvider extends UserModel {
    private ArrayList<Patient> patients;

    public CareProvider(String username){
        super();
        super.setUserID(username);
        patients = new ArrayList<Patient>();
    }

    public void addPatient(Patient p){
        patients.add(p);
    }

    public Patient removePatient(Patient p){
        patients.remove(p);
        return p;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public boolean hasPatient(Patient p){
        return patients.contains(p);
    }
}
