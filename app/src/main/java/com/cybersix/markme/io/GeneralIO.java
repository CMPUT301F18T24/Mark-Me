package com.cybersix.markme.io;

import android.content.Context;
import android.util.Log;

import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GeneralIO implements UserModelIO, RecordModelIO, ProblemModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;
    private Settings settings = null;
    private static GeneralIO instance = null;

    private GeneralIO() {
        diskIO = new DiskIO();
        elasticSearchIO = new ElasticSearchIO();
        settings = diskIO.loadSettings();
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
    public void findUser(String username, OnTaskComplete handler) {
//        UserModel user = diskIO.loadPatient();
//        if (user != null && user.getUsername().equals(username))
//            return user;
//        else
            elasticSearchIO.findUser(username, handler);
    }

    public void loginAs(String username, OnTaskComplete handler) {
        findUser(username, handler);
//        if (userModel != null) {
//            diskIO.saveSettings(settings);
//            synchronizeLocally(userModel);
//            return true;
//        }
//        return false;
    }

    @Override
    public void addUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.addUser(user, handler);
//        if (elasticSearchIO.addUser(user)) {
//            synchronizeLocally(user);
//            return true;
//        }
//        return false;
    }

    @Override
    public void deleteUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.deleteUser(user, handler);
//        if (elasticSearchIO.deleteUser(user)) {
//            UserModel loadedUser = diskIO.loadPatient();
//            if (diskIO.isUserOnDisk(user))
//                diskIO.deletePatient();
//            return true;
//        }
//
//        return false;
    }

    @Override
    public void editUser(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.editUser(user, handler);
//        if (diskIO.isUserOnDisk(user) && user.getUserType().equals(Patient.class.getSimpleName()))
//            diskIO.savePatient((Patient) user);
//        return elasticSearchIO.editUser(user);
    }

    @Override
    public void findProblem(String problemId, OnTaskComplete handler) {
        elasticSearchIO.findProblem(problemId, handler);
//        Patient patient = diskIO.loadPatient();
//        if (patient != null) {
//            for (ProblemModel problem : patient.getProblems())
//                if (problem.getProblemId().equals(problemId))
//                    return problem;
//        }
//        return elasticSearchIO.findProblem(problemId);
    }

    @Override
    public void addProblem(ProblemModel problem, OnTaskComplete handler) {
        elasticSearchIO.addProblem(problem, handler);
//        Patient patient = diskIO.loadPatient();
//        if (patient != null) {
//            patient.addProblem(problem);
//            diskIO.savePatient(patient);
//        }
//        if (!elasticSearchIO.addProblem(problem)) {
//            settings.syncRequired = true;
//            return false;
//        }
//        return true;
    }

    @Override
    public void getProblems(UserModel user, OnTaskComplete handler) {
        elasticSearchIO.getProblems(user, handler);
//        if (diskIO.isUserOnDisk(user)) {
//            Patient patient = diskIO.loadPatient();
//            if (settings.syncRequired)
//                synchronizeRemotely();
//            return patient.getProblems();
//        }
//        return elasticSearchIO.getProblems(user);
    }

    @Override
    public void findRecord(String recordId, OnTaskComplete handler) {
        elasticSearchIO.findRecord(recordId, handler);
//        Patient patient = diskIO.loadPatient();
//        if (patient != null) {
//            for (ProblemModel problem: patient.getProblems())
//                for (RecordModel record: problem.getRecords())
//                    if (record.getRecordId().equals(recordId))
//                        return record;
//        }
//        return elasticSearchIO.findRecord(recordId);
    }

    @Override
    public void addRecord(RecordModel record, OnTaskComplete handler) {
        elasticSearchIO.addRecord(record, handler);
//        Patient patient = diskIO.loadPatient();
//        if (patient != null) {
//            for (ProblemModel problem: patient.getProblems())
//                for (RecordModel lrecord: problem.getRecords())
//                    if (lrecord.getRecordId().equals(record.getRecordId())) {
//                        problem.addRecord(record);
//                        diskIO.savePatient(patient);
//                    }
//        }
//        return elasticSearchIO.addRecord(record);
    }

    @Override
    public void getRecords(ProblemModel problem, OnTaskComplete handler) {
        elasticSearchIO.getRecords(problem, handler);
//        Patient patient = diskIO.loadPatient();
//        if (patient != null) {
//            if (settings.syncRequired)
//                synchronizeRemotely();
//            for (ProblemModel lproblem: patient.getProblems())
//                if (lproblem.getProblemId().equals(problem.getProblemId()))
//                    return lproblem.getRecords();
//        }
//        return elasticSearchIO.getRecords(problem);
    }

//    public void synchronizeRemotely() {
//        if (settings == null)
//            settings = new Settings();
//
//        if (!settings.syncRequired)
//            return;
//
//        Patient patient = diskIO.loadPatient();
//        if (patient == null)
//            return;
//
//        for (ProblemModel problem: patient.getProblems()) {
//            if (!elasticSearchIO.addProblem(problem))
//                return;
//            for (RecordModel record: problem.getRecords()) {
//                if (record.getProblemId() == null)
//                    problem.setProblemId(problem.getProblemId());
//                if (!elasticSearchIO.addRecord(record))
//                    return;
//            }
//        }
//
//        settings.syncRequired = false;
//        diskIO.saveSettings(settings);
//    }
//
//    public void synchronizeLocally(UserModel user) {
//        Patient patient = (Patient) user;
//        patient.setProblems( elasticSearchIO.getProblems(user) );
//        for (ProblemModel problem: patient.getProblems()) {
//            problem.setRecords( elasticSearchIO.getRecords(problem) );
//        }
//
//        diskIO.savePatient((Patient) user);
//    }

    protected static class Settings {
        public boolean syncRequired = false;
    }
}
