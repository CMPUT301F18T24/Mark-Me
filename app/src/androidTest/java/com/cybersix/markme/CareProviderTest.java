package com.cybersix.markme;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CareProviderTest {

    @Test
    public void testAddPatient() {
        CareProvider care = new CareProvider("Steve");
        Patient pat = new Patient("guy44","password!");
        Patient pat2 = new Patient("guy4412123","password!");
        care.addPatient(pat);
        List<Patient> getPatients =  care.getPatients();
        assertEquals(pat,getPatients.get(0));

        care.addPatient(pat2);

        getPatients =  care.getPatients();
        assertEquals(pat2,getPatients.get(1));

    }

    @Test
    public void testGetPatients() {
        CareProvider care = new CareProvider("Steve");
        Patient pat = new Patient("guy44","password!");
        Patient pat2 = new Patient("guy4412123","password!");
        care.addPatient(pat);
        care.addPatient(pat2);
        List<Patient> getPatients =  care.getPatients();
        assertEquals(pat,getPatients.get(0));
        assertEquals(pat2,getPatients.get(1));
    }

    @Test
    public void testRemovePatient() {
        CareProvider care = new CareProvider("Steve");
        Patient pat = new Patient("guy44","password!");
        Patient pat2 = new Patient("guy4412123","password!");
        care.addPatient(pat);
        care.addPatient(pat2);

        care.removePatient(pat);
        assertTrue(!care.getPatients().contains(pat));

        care.removePatient(pat2);
        assertTrue(!care.getPatients().contains(pat2));
    }

    @Test
    public void testHasPatient(){
        CareProvider care = new CareProvider("Steve");
        Patient pat = new Patient("guy44","password!");
        Patient pat2 = new Patient("guy4412123","password!");
        care.addPatient(pat);
        care.addPatient(pat2);

        assertTrue(care.hasPatient(pat));

        care.removePatient(pat2);
        assertFalse(care.hasPatient(pat2));
    }

}
