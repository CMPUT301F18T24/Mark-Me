package com.cybersix.markme;

import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.UserModel;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

public class CareProviderTest {

    @Test
    public void testAddPatient() throws UserModel.UsernameTooShortException {
        CareProvider care = new CareProvider("Steve2131231");
        Patient pat = new Patient("guy443123123");
        Patient pat2 = new Patient("guy4412123");
        care.addPatient(pat);
        List<Patient> getPatients =  care.getPatients();
        assertEquals(pat,getPatients.get(0));

        care.addPatient(pat2);

        getPatients =  care.getPatients();
        assertEquals(pat2,getPatients.get(1));

    }

    @Test
    public void testGetPatients() throws UserModel.UsernameTooShortException  {
        CareProvider care = new CareProvider("Steve12312321");
        Patient pat = new Patient("guy44123123");
        Patient pat2 = new Patient("guy4412123");
        care.addPatient(pat);
        care.addPatient(pat2);
        List<Patient> getPatients =  care.getPatients();
        assertEquals(pat,getPatients.get(0));
        assertEquals(pat2,getPatients.get(1));
    }

    @Test
    public void testRemovePatient() throws UserModel.UsernameTooShortException {
        CareProvider care = new CareProvider("Steve1234235");
        Patient pat = new Patient("guy4423213");
        Patient pat2 = new Patient("guy4412123");
        care.addPatient(pat);
        care.addPatient(pat2);

        care.removePatient(pat);
        assertTrue(!care.getPatients().contains(pat));

        care.removePatient(pat2);
        assertTrue(!care.getPatients().contains(pat2));
    }

    @Test
    public void testRemovePatientFromEmptyList() throws UserModel.UsernameTooShortException {
        CareProvider care = new CareProvider("Steve123123");
        Patient pat = new Patient("guy44123123");

        assertEquals(care.getPatients().size(), 0); // Check empty before
        care.removePatient(pat);
        assertEquals(care.getPatients().size(), 0); // Check empty after
    }

    @Test
    public void testHasPatient()throws UserModel.UsernameTooShortException {
        CareProvider care = new CareProvider("Steve123123");
        Patient pat = new Patient("guy441234523");
        Patient pat2 = new Patient("guy4412123");
        care.addPatient(pat);
        care.addPatient(pat2);

        assertTrue(care.hasPatient(pat));

        care.removePatient(pat2);
        assertFalse(care.hasPatient(pat2));
    }

}
