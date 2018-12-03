package com.cybersix.markme.io;

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import java.util.ArrayList;

public interface RecordModelIO {
    void findRecord(String recordId, OnTaskComplete handler);
    void addRecord(RecordModel record, OnTaskComplete handler);
    void getRecords(ProblemModel problem, OnTaskComplete handler);
}