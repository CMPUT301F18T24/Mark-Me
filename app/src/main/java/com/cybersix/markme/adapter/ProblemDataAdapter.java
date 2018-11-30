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
