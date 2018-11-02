package com.cybersix.markme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemModel {
    private List<RecordModel> records;
    private String title;
    private String description;
    private Date started;

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
    }

    public RecordModel getRecord(int index) {
        if (records.size() <= index)
            return null;

        return records.get(index);
    }
}

class TitleTooLongException extends Exception {

}

class DescriptionTooLongException extends Exception {

}