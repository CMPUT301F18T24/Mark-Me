/**
 * CMPUT 301 Team 24
 *
 * This class has functions for adding, removing and getting patients to a list
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Curtis Goud, Rizwan Qureshi
 */
package com.cybersix.markme;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends UserModel {
    private List<Patient> patients = null;

    public CareProvider(String username) {
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
