package com.cybersix.markme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.TimeZone;

public class ProblemModel extends Observable {
    private ArrayList<RecordModel> records;
    private String title;
    private String description;
    private Date started;

    public static final int MAX_TITLE_LENGTH = 30;
    public static final int MAX_DESCRIPTION_LENGTH = 300;

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
        notifyObservers();
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
        return records;
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
}

/**
 * throws an exception when the title too long
 */
class TitleTooLongException extends Exception {

}

/**
 * throws an exception when the description is too long
 */
class DescriptionTooLongException extends Exception {

}