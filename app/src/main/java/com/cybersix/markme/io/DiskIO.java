package com.cybersix.markme.io;

import com.cybersix.markme.adapter.ProblemDataAdapter;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class DiskIO implements ProblemModelIO, RecordModelIO {
    private static DiskIO instance = null;
    private ArrayDeque<ProblemModel> pendingProblems = null;
    private ArrayDeque<RecordModel> pendingRecords = null;

    private DiskIO() {
        pendingProblems = new ArrayDeque<>();
        pendingRecords = new ArrayDeque<>();
    }

    public static DiskIO getInstance() {
        if (instance == null)
            instance = new DiskIO();
        return instance;
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
//    private void loadProblems() {
//        try {
////            FileInputStream fis = context.openFileInput();
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader reader = new BufferedReader(isr);
//
//            GsonBuilder builder = new GsonBuilder();
////            builder.registerTypeAdapter(ProblemModel.class, new EmotionSerializeAdapter());
//            Gson gson = builder.create();
//            Type typeList = new TypeToken<ArrayDeque<ProblemModel>>(){}.getType();
//            pendingProblems = gson.fromJson(reader, typeList);
//
////            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public ProblemModel findProblem(String problemId) {
        return null;
    }

    @Override
    public void addProblem(ProblemModel problem) {

    }

    @Override
    public ArrayList<ProblemModel> getProblems(UserModel user) {
        return null;
    }

    @Override
    public RecordModel findRecord(String recordId) {
        return null;
    }

    @Override
    public void addRecord(RecordModel record) {

    }

    @Override
    public ArrayList<RecordModel> getRecords(ProblemModel problem) {
        return null;
    }
}
