package com.cybersix.markme.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

        for (Bitmap photo : r.getPhotos()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            photos.add(stream.toByteArray());
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
