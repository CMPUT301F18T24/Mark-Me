package com.cybersix.markme;

import android.graphics.Bitmap;
import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class RecordModel {
    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<Bitmap> photos;
    private BodyLocation bodyLocation;
    private Location mapLocation;

    public RecordModel(String title, String desc){
        photos = new ArrayList<Bitmap>(10);
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

    public void addPhoto(Bitmap photo) throws TooManyPhotosException, PhotoTooLargeException{
        //TODO: Check photo size when Photo class has been figured out!

        int photoSize = photo.getByteCount(); //TODO: GET PHOTO SIZE
        if(photoSize > 64000){ throw new PhotoTooLargeException(); }

        if(photos.size()<10){ photos.add(photo); }
        else{ throw new TooManyPhotosException(); }
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    public Bitmap removePhoto(Bitmap photo){
        boolean r = this.photos.remove(photo);
        return photo;
    }

    public void setMapLocation(Location mapLocation) {
        this.mapLocation = mapLocation;
    }

    public Location getMapLocation() {
        return mapLocation;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String c){this.comment = c;}

    public String toString() {
        // this will return a formatted string that will be visible to the user
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("MDT"));
        String bodyPart;
        if (this.getBodyLocation() == null) {
            bodyPart = "No Assignment body location";
        }
        else {
            bodyPart = this.getBodyLocation().getBodyPart().name();
        }
        return this.getTitle() + " - " + bodyPart + " | " + format.format(this.getTimestamp());
    }

}

class TooManyPhotosException extends Exception {

}

class PhotoTooLargeException extends Exception {

}