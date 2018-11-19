package com.cybersix.markme;

import android.graphics.Bitmap;
import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import io.searchbox.annotations.JestId;

public class RecordModel {
    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<Bitmap> photos;
    private BodyLocation bodyLocation;
    private Location mapLocation;

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    @JestId
    private String recordID;

    /**
     * constructor of record model
     * @param title
     * @param desc
     */
    public RecordModel(String title, String desc){
        photos = new ArrayList<Bitmap>(10);
        this.title = title;
        this.description = desc;
        this.timestamp = new Date();
    }

    /**
     * gets the title
     * @return title
     */
    public String getTitle(){ return  this.title; }

    /**
     * sets the title
     * @param title
     */
    public void setTitle(String title){ this.title=title; }

    /**
     * gets the description
     * @return description
     */
    public String getDescription() { return description; }

    /**
     * sets the description
     * @param description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * sets body location
     * @param bodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * gets the body location
     * @return body location
     */
    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    /**
     * sets the timestamp
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * gets the timestamp
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * adds a photo of a bitmap type
     * @param photo
     * @throws TooManyPhotosException
     * @throws PhotoTooLargeException
     */
    public void addPhoto(Bitmap photo) throws TooManyPhotosException, PhotoTooLargeException{
        //TODO: Check photo size when Photo class has been figured out!

        int photoSize = photo.getByteCount(); //TODO: GET PHOTO SIZE
        if(photoSize > 64000){ throw new PhotoTooLargeException(); }

        if(photos.size()<10){ photos.add(photo); }
        else{ throw new TooManyPhotosException(); }
    }

    /**
     * get an array of photos
     * @return
     */
    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    /**
     * remove a photo using its bitmap
     * @param photo
     * @return
     */
    public Bitmap removePhoto(Bitmap photo){
        boolean r = this.photos.remove(photo);
        return photo;
    }

    /**
     * set map location
     * @param mapLocation
     */
    public void setMapLocation(Location mapLocation) {
        this.mapLocation = mapLocation;
    }

    /**
     * gets the map location
     * @return map location
     */
    public Location getMapLocation() {
        return mapLocation;
    }

    /**
     * gets the comment
     * @return comment
     */
    public String getComment(){
        return this.comment;
    }

    /**
     * sets comment
     * @param c
     */
    public void setComment(String c){this.comment = c;}

    /**
     * changes the date format to a string using a formatter
     * @return string
     */
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

/**
 * exception too many photos
 */
class TooManyPhotosException extends Exception {

}

/**
 * exception the photo is large
 */
class PhotoTooLargeException extends Exception {

}
