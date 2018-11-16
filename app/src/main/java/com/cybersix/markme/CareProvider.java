package com.cybersix.markme;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends UserModel {
    private List<Patient> patients = null;

    public CareProvider(String username){
        this(username, null);
    }

    public CareProvider(String username, List<Patient> patients) {
        super();
        super.setUserID(username);

        if (patients == null)
            this.patients = new ArrayList<Patient>();
        else
            this.patients = patients;
    }

    public void addPatient(Patient p){
        patients.add(p);
    }

    public boolean removePatient(Patient p) {
        if (patients.remove(p)) {
            notifyObservers();
            return true;
        }

        return false;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public boolean hasPatient(Patient p){
        return patients.contains(p);
    }
}
