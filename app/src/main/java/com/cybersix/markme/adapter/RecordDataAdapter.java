/**
 * CMPUT 301 Team 24
 *
 * This is the record data adapter to be used in the elastic search IO class for adapting the
 * queried record results into record objects that will be used throughout the application
 *
 * Version 0.1
 *
 * Date: 2018-11-19
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 * @see com.cybersix.markme.model.RecordModel
 */
package com.cybersix.markme.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.cybersix.markme.actvity.LiveCameraActivity;
import com.cybersix.markme.model.BodyLocation;
import com.cybersix.markme.model.RecordModel;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

public class RecordDataAdapter {
    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<byte[]> photos;
    private BodyLocation bodyLocation;
    private LatLng mapLocation;
    private String problemId;

    @JestId
    private String recordId;

    public RecordDataAdapter(RecordModel r) {
        title = r.getTitle();
        description = r.getDescription();
        timestamp = r.getTimestamp();
        comment = r.getComment();

        photos = new ArrayList<byte[]>();
        for (byte[] photo : r.getPhotos()) {
            photos.add(photo);
        }

        bodyLocation = r.getBodyLocation();
        mapLocation = r.getMapLocation();
        problemId = r.getProblemId();
        recordId = r.getRecordId();
    }

    public RecordModel get() {
        RecordModel r = new RecordModel(title, description);
        try {
            r.setTimestamp(timestamp);
            r.setComment(comment);

            if (photos != null) for (byte[] photo: photos) {
                r.addPhoto(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            }

            r.setBodyLocation(bodyLocation);
            r.setMapLocation(mapLocation);
            r.setProblemId(problemId);
            r.setRecordId(recordId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
