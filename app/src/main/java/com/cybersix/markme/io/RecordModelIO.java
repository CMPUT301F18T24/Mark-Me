package com.cybersix.markme.io;

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import java.util.ArrayList;

public interface RecordModelIO {
    RecordModel findRecord(String recordId);
    void addRecord(RecordModel record);
    ArrayList<RecordModel> getRecords(ProblemModel problem);
}
