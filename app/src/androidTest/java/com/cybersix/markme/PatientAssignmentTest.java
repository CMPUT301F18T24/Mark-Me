package com.cybersix.markme;

import org.junit.Test;

public class PatientAssignmentTest {

    @Test
    public void CareProvider2Patient(){
        // get the patient id that the care provider has chosen

        // add the association onto the elastic search that the care provider is providing
        // care to the patient

        // retreive the associtation from the elastic search and put into assertion
    }

    @Test
    public void Patient2CareProvider(){
        // get the care provider id from the selection the patient has chosen

        // add the association onto the elastic search that the patient is under the care provider

        // retrieve the association from the elastic search to ensure that it was put in there
    }
}
