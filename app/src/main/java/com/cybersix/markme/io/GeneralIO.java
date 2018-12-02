package com.cybersix.markme.io;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class GeneralIO implements UserModelIO, RecordModelIO, ProblemModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;
    private static GeneralIO instance = null;
    private boolean syncRequired = false;

    public static OnTaskComplete emptyHandler = new OnTaskComplete() {
        @Override
        public void onTaskComplete(Object result) {

        }
    };

    private GeneralIO() {
        diskIO = new DiskIO();
        elasticSearchIO = new ElasticSearchIO();
    }

    public static GeneralIO getInstance() {
        if (instance == null)
            instance = new GeneralIO();
        return instance;
    }

    public void setContext(Context context) {
        diskIO.setContext(context);
    }

    @Override
    public void findUser(final String username, final OnTaskComplete handler) {
        elasticSearchIO.findUser(username, handler);
    }

    public void loginAs(final String username, final OnTaskComplete handler) {
        findUser(username, handler);
    }

    @Override
    public void addUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.addUser(user, handler);
    }

    @Override
    public void deleteUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.deleteUser(user, handler);
    }

    @Override
    public void editUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.editUser(user, handler);
    }

    @Override
    public void findProblem(String problemId, OnTaskComplete handler) {
        elasticSearchIO.findProblem(problemId, handler);
    }

    @Override
    public void addProblem(ProblemModel problem, OnTaskComplete handler) {
        elasticSearchIO.addProblem(problem, handler);
    }

    @Override
    public void getProblems(final UserModel user, final OnTaskComplete handler) {
        elasticSearchIO.getProblems(user, handler);
    }

    @Override
    public void findRecord(String recordId, OnTaskComplete handler) {
        elasticSearchIO.findRecord(recordId, handler);
    }

    @Override
    public void addRecord(RecordModel record, OnTaskComplete handler) {
        elasticSearchIO.addRecord(record, handler);
    }

    @Override
    public void getRecords(final ProblemModel problem, final OnTaskComplete handler) {
        elasticSearchIO.getRecords(problem, handler);
    }
}
