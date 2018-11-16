package com.cybersix.markme;

import java.util.ArrayList;

public class CloudUtils {
    public static ArrayList<Patient> getAssignedPatients(String careProviderId) {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        patients.add(new Patient("bob", "bob"));
        patients.add(new Patient("rob", "rob"));
        patients.add(new Patient("zod", "zod"));
        return patients;
    }
}
