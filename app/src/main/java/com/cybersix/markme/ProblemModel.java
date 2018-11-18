package com.cybersix.markme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.TimeZone;

import io.searchbox.annotations.JestId;

public class ProblemModel extends Observable {
    private ArrayList<RecordModel> records;
    private String title;
    private String description;
    private Date started;

    public String getProblemID() {
        return problemID;
    }

    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    @JestId
    private String problemID;

    public static final int MAX_TITLE_LENGTH = 30;
    public static final int MAX_DESCRIPTION_LENGTH = 300;

    public ProblemModel(String title, String description)
            throws TitleTooLongException, DescriptionTooLongException, NullPointerException {
        setTitle(title);
        setDescription(description);

        this.started = new Date();
        this.records = new ArrayList<RecordModel>();
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) throws TitleTooLongException, NullPointerException {
        if (title == null)
            throw new NullPointerException();
        else if (title.length() > MAX_TITLE_LENGTH)
            throw new TitleTooLongException();

        this.title = title;
    }

    public void setDescription(String description) throws DescriptionTooLongException, NullPointerException {
        if (description == null)
            throw new NullPointerException();
        else if (description.length() > MAX_DESCRIPTION_LENGTH)
            throw new DescriptionTooLongException();

        this.description = description;
    }

    public Date getDateStarted() {
        return this.started;
    }

    public void addRecord(RecordModel record) {
        if (record == null || records.contains(record))
            return;

        records.add(record);
        notifyObservers();
    }

    // Note: Temporary bug fix.
    public void initializeRecordModel() {
        this.records = new ArrayList<RecordModel>();
    }

    public RecordModel removeRecord(int index) {
        RecordModel record = records.remove(index);
        notifyObservers();

        return record;
    }

    public boolean removeRecord(RecordModel record) {
        boolean removed = records.remove(record);
        notifyObservers();

        return removed;
    }

    public RecordModel getRecord(int index) {
        if (records.size() <= index)
            return null;

        return records.get(index);
    }

    public ArrayList<RecordModel> getRecords() {
        return records;
    }

    public String toString() {
        // This will return the string that will be visible to the user
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("MDT"));
        return this.getTitle() + " - " + this.getDescription() + " | " + format.format(this.getDateStarted());
    }
}

class TitleTooLongException extends Exception {

}

class DescriptionTooLongException extends Exception {

}