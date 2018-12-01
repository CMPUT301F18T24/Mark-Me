package com.cybersix.markme.io;

import com.cybersix.markme.adapter.ProblemDataAdapter;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class DiskIO implements UserModelIO, ProblemModelIO, RecordModelIO {
    private static final String PROBLEM_FILENAME = "problem_queue.dat";
    private static final String RECORD_FILENAME = "record_queue.dat";
    private Patient currentUser = null;

    protected DiskIO(Patient currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Attempts to load the .sav file named under MainActivity.FILENAME, and store the
     * emotions in that file to the ArrayList of emotions.
     *
     * Based on CMPUT 301 lab's lonelyTwitter app. Modified so that abstract classes are retrievable
     * through GSON.
     *
     * lonelyTwitter: https://github.com/joshua2ua/lonelyTwitter
     * author: joshua
     */
    private void load() {
        try {
            InputStream fis = this.getClass().getClassLoader().getResourceAsStream(PROBLEM_FILENAME);//context.openFileInput();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeList = new TypeToken<ArrayList<ProblemModel>>(){}.getType();
            Patient loadedPatient = gson.fromJson(reader, typeList);
            if (loadedPatient != null)
                currentUser = loadedPatient;

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(PROBLEM_FILENAME));
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(currentUser, writer);

            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProblemModel findProblem(String problemId) {
        if (currentUser == null)
            return null;

        for (ProblemModel problem: currentUser.getProblems())
            if (problemId.equals(problem.getPatientId()))
                return problem;

        return null;
    }

    @Override
    public void addProblem(ProblemModel problem) {
        save();
    }

    @Override
    public ArrayList<ProblemModel> getProblems(UserModel user) {
        load();
        return currentUser.getProblems();
    }

    @Override
    public RecordModel findRecord(String recordId) {
        if (currentUser == null)
            return null;

        for (ProblemModel problem: currentUser.getProblems())
            for (RecordModel record: problem.getRecords())
                if (record.getRecordId().equals(recordId))
                    return record;

        return null;
    }

    @Override
    public void addRecord(RecordModel record) {
        save();
    }

    @Override
    public ArrayList<RecordModel> getRecords(ProblemModel problem) {
        load();
        if (currentUser == null)
            return null;

        for (ProblemModel loadedProblem: currentUser.getProblems())
            if (problem.getProblemId().equals(problem.getProblemId()))
                return loadedProblem.getRecords();

        return null;
    }

    @Override
    public UserModel findUser(String username) {
        if (currentUser != null && currentUser.getUsername().equals(username))
            return currentUser;
        else
            return null;
    }

    @Override
    public boolean addUser(UserModel user) {
        currentUser = (Patient) user;
        save();
        return true;
    }

    @Override
    public boolean deleteUser(UserModel user) {
        return false;
    }

    @Override
    public void editUser(UserModel user) {
        addUser(user);
    }
}
