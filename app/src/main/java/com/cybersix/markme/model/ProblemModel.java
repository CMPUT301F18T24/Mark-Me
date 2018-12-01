package com.cybersix.markme.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Observable;
import java.util.TimeZone;

import io.searchbox.annotations.JestId;

public class ProblemModel extends Observable {
    private ArrayList<RecordModel> records = new ArrayList<>();
    private String title;
    private String description;
    private Date started;
    private String patientId;

    @JestId
    private String problemId;

    public static final int MAX_TITLE_LENGTH = 30;
    public static final int MAX_DESCRIPTION_LENGTH = 300;

    /***
     * constructs the problem model
     */
    public ProblemModel() {
        this.started = new Date();
        this.records = new ArrayList<RecordModel>();
    }

    /***
     * constructs the problem model
     * @param title
     * @param description
     * @throws TitleTooLongException
     * @throws DescriptionTooLongException
     * @throws NullPointerException
     */
    public ProblemModel(String title, String description)
            throws TitleTooLongException, DescriptionTooLongException, NullPointerException {
        setTitle(title);
        setDescription(description);

        this.started = new Date();
        this.records = new ArrayList<RecordModel>();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return The problemId, from elastic search database.
     */
    public String getProblemId() {
        return problemId;
    }

    /**
     * @param problemId The problemId, from elastic search database.
     */
    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }


    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    /**
     * Gets the title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the description
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * sets the title
     * @param title
     * @throws TitleTooLongException
     * @throws NullPointerException
     */
    public void setTitle(String title) throws TitleTooLongException, NullPointerException {
        if (title == null)
            throw new NullPointerException();
        else if (title.length() > MAX_TITLE_LENGTH)
            throw new TitleTooLongException();

        this.title = title;
    }

    /**
     * sets the description
     * @param description
     * @throws DescriptionTooLongException
     * @throws NullPointerException
     */
    public void setDescription(String description) throws DescriptionTooLongException, NullPointerException {
        if (description == null)
            throw new NullPointerException();
        else if (description.length() > MAX_DESCRIPTION_LENGTH)
            throw new DescriptionTooLongException();

        this.description = description;
    }

    /**
     * gets the date started
     * @return the started date
     */
    public Date getDateStarted() {
        return this.started;
    }

    /**
     * adds a record if the record does not exist returns null
     * @param record
     */
    public void addRecord(RecordModel record) {
        if (record == null || records.contains(record))
            return;

        records.add(record);
        record.setProblemId(this.getProblemId());

        setChanged();
        notifyObservers();
    }

    /**
     * adds all records in a record list array to the problem
     * @param rs
     */
    public void addRecords(ArrayList<RecordModel> rs) {
        if(records == null){
            records = new ArrayList<RecordModel>();
        }
        records.addAll(rs);
        notifyObservers();
    }

    // TODO: this is a quick fix [records] for a NULL problem with the records as it is null from the
    // TODO: server but should just be an arraylist of size 0
    // Note: Temporary bug fix.
    public void initializeRecordModel() {
        this.records = new ArrayList<RecordModel>();
    }

    /**
     * Removes a record using its index
     * @param index
     * @return record
     */
    public RecordModel removeRecord(int index) {
        RecordModel record = records.remove(index);
        notifyObservers();

        return record;
    }

    /**
     * removes a record using the record
     * @param record
     * @return boolean to show that the record is properly removed
     */
    public boolean removeRecord(RecordModel record) {
        boolean removed = records.remove(record);
        notifyObservers();

        return removed;
    }

    /**
     * using the index gets the record and returns that record
     * @param index
     * @return
     */
    public RecordModel getRecord(int index) {
        if (records.size() <= index)
            return null;

        return records.get(index);
    }

    /**
     * Gets all the records
     * @return the array list of all the records
     */
    public ArrayList<RecordModel> getRecords() {
        if(records == null){
            records = new ArrayList<RecordModel>();
        }
        return records;
    }

    /**
     * Sets all of the records
     * @param records
     */
    public void setRecords(ArrayList<RecordModel> records) {
        this.records = records;
    }

    /**
     * changes the date to a string
     * @return string of the date
     */
    public String toString() {
        // This will return the string that will be visible to the user
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("MDT"));
        return this.getTitle() + " - " + this.getDescription() + " | " + format.format(this.getDateStarted());
    }

    /**
     * throws an exception when the title too long
     */
    public static class TitleTooLongException extends Exception {

    }

    /**
     * throws an exception when the description is too long
     */
    public static class DescriptionTooLongException extends Exception {

    }
}
