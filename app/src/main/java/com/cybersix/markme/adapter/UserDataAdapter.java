/**
 * CMPUT 301 Team 24
 *
 * This is the user data adapter to be used in the ElasticSearchIO for adapting the queried user
 * data from the database and adapt it into user object models to be used throughout the application
 *
 * Version 0.1
 *
 * Date: 2018-11-19
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 * @see com.cybersix.markme.model.UserModel
 */
package com.cybersix.markme.adapter;

import android.util.Log;

import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.annotations.JestId;

public class UserDataAdapter {
    private String username = null;
    private String email = null;
    private String phone = null;
    private String type = null;
    private ArrayList<String> assignedPatients = new ArrayList<>();
    private ArrayList<String> assignedCareProvider = new ArrayList<>();

    @JestId
    public String userId = null;

    public UserDataAdapter(UserModel user) {
        extractUserInformation(user);
        if (type == Patient.class.getSimpleName())
            extractPatientInformation((Patient) user);
        else if (type == CareProvider.class.getSimpleName())
            extractCareProviderInformation((CareProvider) user);
    }

    public UserDataAdapter(Patient user) {
        extractUserInformation(user);
        extractPatientInformation(user);
    }

    public UserDataAdapter(CareProvider user) {
        extractUserInformation(user);
        extractCareProviderInformation(user);
    }

    public UserModel get() {
        if (type.equals(Patient.class.getSimpleName()))
            return getAsPatient();
        else if (type.equals(CareProvider.class.getSimpleName()))
            return getAsCareProvider();
        return null;
    }

    public Patient getAsPatient() {
        Patient p = new Patient();
        p.setUserId(userId);
        try {
            p.setPhone(phone);
            p.setEmail(email);
            p.setUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public CareProvider getAsCareProvider() {
        CareProvider c = new CareProvider();
        c.setUserId(userId);
        try {
            c.setPhone(phone);
            c.setEmail(email);
            c.setUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        c.setPatients( ElasticSearchIO.getInstance().getPatients(c) );

        return c;
    }

    private void extractUserInformation(UserModel user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.type = user.getUserType();
    }

    private void extractPatientInformation(Patient patient) {
        for (CareProvider c: patient.getCareProviders())
            assignedCareProvider.add(c.getUserId());
    }

    private void extractCareProviderInformation(CareProvider careProvider) {
        for (Patient p: careProvider.getPatients())
            assignedPatients.add(p.getUserId());
    }
}
