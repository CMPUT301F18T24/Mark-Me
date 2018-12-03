package com.cybersix.markme.io;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class GeneralIO implements UserModelIO, RecordModelIO, ProblemModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;
    private static GeneralIO instance = null;
    private Boolean offlineMode = false;
    private Context context = null;

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
        this.context = context;
        diskIO.setContext(context);
    }

    @Override
    public void findUser(final String username, final OnTaskComplete handler) {
        elasticSearchIO.findUser(username, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                ArrayList<UserModel> users = (ArrayList<UserModel>) result;
//                users.clear();
                if (users.isEmpty()) {
                    Log.i("GeneralIO", "Offline mode started.");
                    offlineMode = true;
                    Patient p = diskIO.loadPatient();
                    if (p != null && p.getUsername().equals(username)) {
                        users.add(p);
                    }
                }
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
    public void addProblem(ProblemModel problem, final OnTaskComplete handler) {
        if (offlineMode) {
            diskIO.savePatient();
            handler.onTaskComplete(new Object());
        } else {
            elasticSearchIO.addProblem(problem, new OnTaskComplete() {
                @Override
                public void onTaskComplete(Object result) {
                    diskIO.savePatient();
                    handler.onTaskComplete(result);
                }
            });
        }
    }

    @Override
    public void getProblems(final UserModel user, final OnTaskComplete handler) {
        if (offlineMode) {
            handler.onTaskComplete(diskIO.loadPatient().getProblems());
        } else {
            elasticSearchIO.getProblems(user, handler);
        }
    }

    @Override
    public void findRecord(String recordId, OnTaskComplete handler) {
        elasticSearchIO.findRecord(recordId, handler);
    }

    @Override
    public void addRecord(RecordModel record, final OnTaskComplete handler) {
        elasticSearchIO.addRecord(record, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                diskIO.savePatient();
                handler.onTaskComplete(result);
            }
        });
    }

    @Override
    public void getRecords(final ProblemModel problem, final OnTaskComplete handler) {
        if (offlineMode) {
            for (ProblemModel lproblem : diskIO.loadPatient().getProblems()) {
                Log.i("GeneralIO", lproblem.getRecords().size() + "");
                if (lproblem.equals(problem)) {
                    handler.onTaskComplete(lproblem.getRecords());
                    break;
                }
            }
        } else {
            elasticSearchIO.getRecords(problem, handler);
        }
    }

    public boolean previousModeWasOffline() {
        return diskIO.loadPreviousMode();
    }

    public void getEverythingForPatient(final UserModel user) {
        if (offlineMode) {
            diskIO.loadPatient();
        } else if (previousModeWasOffline()) {
            elasticSearchIO.bulkAddPatient(diskIO.loadPatient(), new OnTaskComplete() {
                @Override
                public void onTaskComplete(Object result) {
//                    onlineSetup(user);
                }
            });
        } else {
            onlineSetup(user);
        }
        diskIO.savePreviousMode(offlineMode); // set previous to current
        // otherwise get everything from server
    }

    public void onlineSetup(final UserModel user) {
        elasticSearchIO.getProblems(user, new OnTaskComplete() {
            @Override
            public void onTaskComplete(Object result) {
                Patient patient = (Patient) user;
                patient.setProblems((ArrayList<ProblemModel>) result);
                diskIO.setPatient(patient);
                for (final ProblemModel problem: patient.getProblems()) {
                    elasticSearchIO.getRecords(problem, new OnTaskComplete() {
                        @Override
                        public void onTaskComplete(Object result) {
                            ArrayList<RecordModel> records = (ArrayList<RecordModel>) result;
                            Log.i("GENERALIO", "" + records.size());
                            problem.setRecords(records);
                            diskIO.savePatient();
                        }
                    });
                }
            }
        });
    }
}
