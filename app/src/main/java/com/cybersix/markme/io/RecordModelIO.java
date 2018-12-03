/**
 * CMPUT 301 Team 24
 *
 * This is the interface for the record model functions to be used in the elastic search IO
 *
 * Version 0.1
 *
 * Date: 2018-12-02
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 */
package com.cybersix.markme.io;

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import java.util.ArrayList;

public interface RecordModelIO {
    RecordModel findRecord(String recordId);
    void addRecord(RecordModel record);
    ArrayList<RecordModel> getRecords(ProblemModel problem);
}
