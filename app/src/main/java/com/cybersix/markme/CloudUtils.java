package com.cybersix.markme;

import java.util.ArrayList;

public class CloudUtils {
    public static ArrayList<Patient> getAssignedPatients(String careProviderId) {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        try {
            patients.add(new Patient("bob123456789", "bob"));
            patients.add(new Patient("robIsAmAzInG", "rob"));
            patients.add(new Patient("zodEmperorOfTheW0RLD", "zod"));
        } catch (UserIDTooShortException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
