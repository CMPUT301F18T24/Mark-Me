/**
 * CMPUT 301 Team 24
 *
 * This is the record model. This will hold all of the information of the records based on the logged
 * in patient. This will help with keeping the record information of the patient consistent throughout
 * the app
 *
 * Version 0.1
 *
 * Date: 2018-11-10
 *
 * Copyright Notice
 * @author Vishal Patel TODO: Might be someone else
 */
package com.cybersix.markme.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.cybersix.markme.actvity.LiveCameraActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.TimeZone;

import io.searchbox.annotations.JestId;

public class RecordModel extends Observable {
    private String title;
    private String description;
    private Date timestamp;
    private String comment;
    private ArrayList<byte[]> photos;
    private String photoLabel = "";
    private BodyLocation bodyLocation;
    private LatLng mapLocation;

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    private String problemId;

    @JestId
    private String recordId;

    /**
     * @return The recordId, from elastic search database.
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordID The recordId, from elastic search database.
     */
    public void setRecordId(String recordID) {
        this.recordId = recordID;
    }

    /**
     * constructor of record model
     * @param title
     * @param desc
     */
    public RecordModel(String title, String desc){
        photos = new ArrayList<byte[]>(10);
        this.title = title;
        this.description = desc;
        this.bodyLocation = new BodyLocation(EBodyPart.UNLISTED); //By default unlisted
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
     * sets the label
     * @param label
     */
    public void setLabel(String label){ this.photoLabel=label; }

    /**
     * gets the label
     */
    public String getLabel() { return this.photoLabel; }

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

        // Compress and output the bitmap.
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        photo.compress(Bitmap.CompressFormat.JPEG, LiveCameraActivity.IMAGE_QUALITY, out);
        byte[] bytes = out.toByteArray();

        if(bytes.length > 64000){ throw new PhotoTooLargeException(); }

        if(photos.size()<10){
            photos.add(bytes);
        }
        else{ throw new TooManyPhotosException(); }
    }

    /**
     * get an array of photos
     * @return
     */
    public ArrayList<byte[]> getPhotos() {
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
    public void setMapLocation(LatLng mapLocation) {
        this.mapLocation = mapLocation;
    }

    /**
     * gets the map location
     * @return map location
     */
    public LatLng getMapLocation() {
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * exception too many photos
     */
    public class TooManyPhotosException extends Exception {

    }

    /**
     * exception the photo is large
     */
    public class PhotoTooLargeException extends Exception {

    }
}