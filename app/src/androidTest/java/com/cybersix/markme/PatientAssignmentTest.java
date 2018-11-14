package com.cybersix.markme;

import org.junit.Test;

public class PatientAssignmentTest {

    @Test
    public void CareProvider2Patient(){
        // get the patient id that the care provider has chosen
        // testPatient = new Pateint(); <- set patient ID
        // care provider = new CareProvider();


        // add the association onto the elastic search that the care provider is providing
        // care to the patient
        // string insertion = "Add the query string"
        // try
        // IO_Utility(insertion);

        // retreive the associtation from the elastic search and put into assertion
        // string query = "Add the query that will get the associations"
        // results = ElasticController(query);
        // assertEquals(assignment, testAssignment);
    }

    @Test
    public void addListAssociation() {
        // This will be the test that will check to see a mass upload of associations to the
        // elastic search cloud
        // patientList = List<Patient>();
        // careProvider = new CareProvider();

        // string insertion = "String insertion"
        // for each (patient: patientList) {
        //          ElasticController(insertion)
        //  }

        // retrieve a list of all of the patients that have been assigned
        // string query = "Add the query here"
        // results = ElasticController(query)
        // assertEquals(results, listOfPatientsTest)
    }

    @Test
    public void Patient2CareProvider(){
        // TODO: This is similar to the first test
        // get the care provider id from the selection the patient has chosen

        // add the association onto the elastic search that the patient is under the care provider

        // retrieve the association from the elastic search to ensure that it was put in there
    }
}
