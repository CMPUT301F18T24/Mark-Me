package com.cybersix.markme;

import java.util.ArrayList;
import java.util.Date;

public class RecordModel {
    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<PhotoRecord> photos;
    private BodyLocation bodyLocation;
    private GPSCoordinate mapLocation;

    public RecordModel(String title, String desc){
        photos = new ArrayList<PhotoRecord>(10);
        this.title = title;
        this.description = desc;
    }

    public String getTitle(){ return  this.title; }

    public void setTitle(String title){ this.title=title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void addPhoto(PhotoRecord photo) throws TooManyPhotosException{
        //TODO: Check photo size!
        if(photos.size()<10){ photos.add(photo); }
        else{ throw new TooManyPhotosException(); }
    }

    public ArrayList<PhotoRecord> getPhotos() {
        return photos;
    }

    public PhotoRecord removePhoto(PhotoRecord photo){
        boolean r = this.photos.remove(photo);
        return photo;
    }

    public void setMapLocation(GPSCoordinate mapLocation) {
        this.mapLocation = mapLocation;
    }

    public GPSCoordinate getMapLocation() {
        return mapLocation;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String c){this.comment = c;}

}

class TooManyPhotosException extends Exception {

}
