/**
 * CMPUT 301 Team 24
 *
 * This is the problem data adapter which is used within the ElasticSearchIO which adapts the
 * queried data from the database into problem objects to be used within the application
 *
 * Version 0.1
 *
 * Date: 2018-11-19
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 * @see com.cybersix.markme.model.ProblemModel
 */
package com.cybersix.markme.adapter;

import android.util.Log;

import com.cybersix.markme.io.ElasticSearchIO;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

public class ProblemDataAdapter {
    private String title;
    private String description;
    private Date started;
    private String patientId;

    @JestId
    private String problemId;

    public ProblemDataAdapter(ProblemModel p) {
        title = p.getTitle();
        description = p.getDescription();
        started = p.getDateStarted();
        patientId = p.getPatientId();
        problemId = p.getProblemId();
    }

    public ProblemModel get() {
        ProblemModel p = new ProblemModel();
        p.setPatientId(patientId);
        p.setProblemId(problemId);
        try {
            p.setTitle(title);
            p.setDescription(description);
            p.setStarted(started);
        } catch (Exception e) {

        }
        return p;
    }
}
