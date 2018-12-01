package com.cybersix.markme.io;

import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class GeneralIO implements UserModelIO, RecordModelIO, ProblemModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;
    private static GeneralIO instance = null;

    private GeneralIO() {
        diskIO = new DiskIO();
        elasticSearchIO = new ElasticSearchIO();
    }

    public static GeneralIO getInstance() {
        if (instance == null)
            instance = new GeneralIO();
        return instance;
    }

    @Override
    public UserModel findUser(String username) {
//        if (elasticSearchIO.isConnected()) // give preference to cloud before local
            return elasticSearchIO.findUser(username);
    }

    @Override
    public boolean addUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            return elasticSearchIO.addUser(user);
//        return diskIO.addUser(user);
    }

    @Override
    public boolean deleteUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            return elasticSearchIO.deleteUser(user);

//        return diskIO.deleteUser(user);
    }

    @Override
    public void editUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            elasticSearchIO.editUser(user);

//        diskIO.editUser(user);
    }

    @Override
    public ProblemModel findProblem(String problemId) {
        return elasticSearchIO.findProblem(problemId);
    }

    @Override
    public void addProblem(ProblemModel problem) {
        elasticSearchIO.addProblem(problem);
    }

    @Override
    public ArrayList<ProblemModel> getProblems(UserModel user) {
        return elasticSearchIO.getProblems(user);
    }

    @Override
    public RecordModel findRecord(String recordId) {
        return elasticSearchIO.findRecord(recordId);
    }

    @Override
    public void addRecord(RecordModel record) {
        elasticSearchIO.addRecord(record);
    }

    @Override
    public ArrayList<RecordModel> getRecords(ProblemModel problem) {
        return elasticSearchIO.getRecords(problem);
    }
}
