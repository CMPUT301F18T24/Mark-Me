package com.cybersix.markme;

import java.util.Collection;
import java.util.Date;

public class RecordModel {
    private Date timestamp;
    private Collection<PhotoRecord> photos;
    private Collection<CommentRecord> comments;
    private BodyLocation bodyLocation;

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public void setMapLocation(GPSCoordinate mapLocation) {
        this.mapLocation = mapLocation;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private GPSCoordinate mapLocation;

    public Date getTimestamp() {
        return timestamp;
    }

    public Collection<PhotoRecord> getPhotos() {
        return photos;
    }

    public Collection<CommentRecord> getComments() {
        return comments;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public GPSCoordinate getMapLocation() {
        return mapLocation;
    }

    public String getComment() {
        return comment;
    }

    public String comment;
}
