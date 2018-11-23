package com.cybersix.markme;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.TimeZone;

import io.searchbox.annotations.JestId;

public class ProblemModel extends Observable implements DataModel {
    private ArrayList<RecordModel> records ;
    private String title;
    private String description;
    private Date started;

    @JestId
    private String problemID;

    public static final int MAX_TITLE_LENGTH = 30;
    public static final int MAX_DESCRIPTION_LENGTH = 300;

    /**
     * @return The problemID, from elastic search database.
     */
    public String getProblemID() {
        return problemID;
    }

    /**
     * @param problemID The problemID, from elastic search database.
     */
    public void setProblemID(String problemID) {
        this.problemID = problemID;
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

    @Override
    public Fragment getDataFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(ListFragment.EXTRA_TITLE, getTitle());
        bundle.putString(ListFragment.EXTRA_DESCRIPTION, getDescription());

        Fragment fragment = new ListFragment<RecordModel>();
        fragment.setArguments(bundle);

        return fragment;
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
