package com.cybersix.markme.io;

import com.cybersix.markme.actvity.MainActivity;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DiskIO implements ProblemModelIO, RecordModelIO, UserModelIO {
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
    private void loadFromFile() {
        try {
//            FileInputStream fis = context.openFileInput(MainActivity.FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(Emotion.class, new EmotionSerializeAdapter());
            Gson gson = builder.create();
//            Type typeListEmotions = new TypeToken<ArrayList<Emotion>>(){}.getType();
//            ArrayList<Patient> listOfEmotion = gson.fromJson(reader, typeListEmotions);

//            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserModel findUser(String username) {
        return null;
    }

    @Override
    public boolean addUser(UserModel user) {
        return false;
    }

    @Override
    public boolean deleteUser(UserModel user) {
        return false;
    }

    @Override
    public void editUser(UserModel user) {

    }

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
