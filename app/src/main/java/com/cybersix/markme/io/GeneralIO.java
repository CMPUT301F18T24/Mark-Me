package com.cybersix.markme.io;

import android.content.Context;
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
    private Settings settings = null;
    private static GeneralIO instance = null;
    private OnTaskComplete setSettingsHandler = new OnTaskComplete() {
        @Override
        public void onTaskComplete(Object result) {
            settings = (Settings) result;
        }
    };

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
        diskIO.loadSettings(setSettingsHandler);
    }

    @Override
    public void findUser(final String username, final OnTaskComplete handler) {
        diskIO.loadPatient(new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                Patient p = (Patient) result;
                if (p != null && p.getUsername().equals(username)) {
                    ArrayList<UserModel> users = new ArrayList<>();
                    users.add(p);
                    handler.onTaskComplete(users);
                } else
                    elasticSearchIO.findUser(username, handler);
            }
        });
    }

    public void loginAs(final String username, final OnTaskComplete handler) {
        findUser(username, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                final ArrayList<UserModel> users = (ArrayList<UserModel>) result;
                if (users != null)
                    diskIO.saveSettings(settings, new OnTaskComplete() {
                        @Override
                        public void onTaskComplete(Object result) {
                            handler.onTaskComplete(users);
                        }
                    });
                else
                    handler.onTaskComplete(users);
            }
        });
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
        elasticSearchIO.getProblems(user, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                ArrayList<ProblemModel> problems = (ArrayList<ProblemModel>) result;
                if (problems == null) {
                    diskIO.loadPatient(new OnTaskComplete() {
                        @Override
                        public void onTaskComplete(Object result) {
                            Patient p = (Patient) result;
                            if (p != null && p.getUserId().equals(user.getUserId()))
                                handler.onTaskComplete(p.getProblems());
                        }
                    });
                } else
                    handler.onTaskComplete(result);
            }
        });
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
        elasticSearchIO.getRecords(problem, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                final ArrayList<RecordModel> records = (ArrayList<RecordModel>) result;
                if (records == null) {
                    diskIO.loadPatient(new OnTaskComplete() {
                        @Override
                        public void onTaskComplete(Object result) {
                            Patient p = (Patient) result;
                            if (p.getUserId().equals(problem.getPatientId())) {
                                for (ProblemModel lproblem: p.getProblems()) {
                                    if (problem.getProblemId().equals(lproblem.getProblemId()))
                                        handler.onTaskComplete(lproblem.getRecords());
                                }
                            }
                        }
                    });
                } else
                    handler.onTaskComplete(result);
            }
        });
    }

    protected static class Settings {
        public boolean syncRequired = false;
    }
}
