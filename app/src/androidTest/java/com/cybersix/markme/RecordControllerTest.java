package com.cybersix.markme;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RecordControllerTest {

    @Before
    public void setup(){
        ProblemController.getInstance().createNewProblem("a","b");
        ProblemController.getInstance().setSelectedProblem(0);
        RecordController.getInstance().records.add(new RecordModel("A","b"));
        RecordController.getInstance().selectedProblemRecords.add(new RecordModel("A","b"));
    }

    @Test
    public void testCreateNewRecord() {
        // this will ensure that the correct record is added to the record controller
        String title = "testTitle";
        String description = "testDescription";

        RecordController testController = new RecordController();
        testController.records.add(new RecordModel(title,description));
        RecordModel record = testController.records.get(0);

        // check that the record information has been saved
        assertEquals(record.getTitle(), title);
        assertEquals(record.getDescription(), description);
    }

    @Test
    public void testEditRecord() {
        // this will ensure that the correct record is added to the record controller
        String title = "testTitle";
        String description = "testDescription";

        String titleEdit = "testTitle is edited";
        String descriptionEdit = "testDescription is now edited";
        String commentEdit = "This is a comment that is added to the record";
        ArrayList<Bitmap> editPhotos = new ArrayList<Bitmap>(14);
        BodyLocation editBodyLocation = new BodyLocation(EBodyPart.LEFTARM);
        LatLng editLocation = new LatLng(10, 10);

        RecordController testController = new RecordController();
        testController.createNewRecord(title, description, null, null, null);

        try {
            // now we edit the created record
            testController.editRecord(0, titleEdit, descriptionEdit, editPhotos, editBodyLocation,
                    editLocation, commentEdit);
//            // get the record
            RecordModel record = testController.records.get(0);
            assertEquals(record.getTitle(), titleEdit);
            assertEquals(record.getDescription(), descriptionEdit);
            assertEquals(record.getComment(), commentEdit);
            assertEquals(record.getPhotos(), editPhotos);
            assertEquals(record.getBodyLocation(), editBodyLocation);
            assertEquals(record.getMapLocation(), editLocation);
        }
        catch (Exception e) {
            // not expected to get here
            assert(false);
        }

    }

    @Test
    public void testLoadRecordData() {

    }

    @Test
    public void testSaveRecordData() {

    }

    @Test
    public void removeRecordData() {
        // this will ensure that the correct record is removed
    }
}
