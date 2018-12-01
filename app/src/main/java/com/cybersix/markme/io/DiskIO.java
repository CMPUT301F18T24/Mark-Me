/**
 * CMPUT 301 Team 24
 *
 * Attempts to load the .sav file named under MainActivity.FILENAME, and store the
 * emotions in that file to the ArrayList of emotions.
 *
 * Based on CMPUT 301 lab's lonelyTwitter app. Modified so that abstract classes are retrievable
 * through GSON.
 *
 * lonelyTwitter: https://github.com/joshua2ua/lonelyTwitter
 * @author joshua
 */
package com.cybersix.markme.io;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class DiskIO {
    private static final String PATIENT_FILENAME = "PATIENT.dat";
    private static final String SETTINGS_FILENAME = "SETTINGS.dat";
    private Context context = null;

    protected DiskIO() {

    }

    public void setContext(Context context) {
        this.context = context;
    }


    public Patient loadPatient() {
        Patient p = null;
        try {
            p = new LoadPatientTask().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public void savePatient(Patient p) {
        try {
            new SavePatientTask().execute(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GeneralIO.Settings loadSettings() {
        GeneralIO.Settings s = null;
        try {
            s = new LoadSettingsTask().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void saveSettings(GeneralIO.Settings s) {
        try {
            new SaveSettingsTask().execute(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private Patient asyncLoadPatient() {
        Patient patient = null;
        try {
            FileInputStream fis = context.openFileInput(PATIENT_FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeList = new TypeToken<Patient>(){}.getType();
            patient = gson.fromJson(reader, typeList);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public boolean isUserOnDisk(UserModel user) {
        UserModel loadedUser = loadPatient();
        if (loadedUser == null)
            return false;
        else if (loadedUser.getUserId().equals(user.getUserId()))
            return true;
        else
            return false;
    }

    private void asyncSavePatient(Patient p) {
        try {
            FileOutputStream fos = context.openFileOutput(PATIENT_FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(p, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient() {
        File file = new File(PATIENT_FILENAME);
        if (file.exists())
            file.delete();
    }

    private GeneralIO.Settings asyncLoadSettings() {
        GeneralIO.Settings settings = new GeneralIO.Settings();
        try {
            FileInputStream fis = context.openFileInput(SETTINGS_FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeList = new TypeToken<GeneralIO.Settings>(){}.getType();
            settings = gson.fromJson(reader, typeList);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return settings;
    }

    private void asyncSaveSettings(GeneralIO.Settings settings) {
        try {
            FileOutputStream fos = context.openFileOutput(SETTINGS_FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(settings, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private class LoadPatientTask extends AsyncTask<Void, Void, Patient> {
        protected Patient doInBackground(Void... params) {
            return asyncLoadPatient();
        }
    }

    private class SavePatientTask extends AsyncTask<Patient, Void, Void> {
        protected Void doInBackground(Patient... params) {
            for (Patient p: params)
                asyncSavePatient(p);
            return null;
        }
    }

    private class LoadSettingsTask extends AsyncTask<Void, Void, GeneralIO.Settings> {
        protected GeneralIO.Settings doInBackground(Void... params) {
            return asyncLoadSettings();
        }
    }

    private class SaveSettingsTask extends AsyncTask<GeneralIO.Settings, Void, Void> {
        protected Void doInBackground(GeneralIO.Settings... params) {
            for (GeneralIO.Settings s: params)
                asyncSaveSettings(s);
            return null;
        }
    }
}
