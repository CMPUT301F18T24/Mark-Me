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


    public void loadPatient(OnTaskComplete handler) {
        new LoadPatientTask().execute(handler);
    }

    public void savePatient(Patient p, OnTaskComplete handler) {
        new SavePatientTask().execute(p, handler);
    }

    public void loadSettings(OnTaskComplete handler) {
        new LoadSettingsTask().execute(handler);
    }

    public void saveSettings(GeneralIO.Settings s, OnTaskComplete handler) {
        new SaveSettingsTask().execute(s, handler);
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

    private class LoadPatientTask extends AsyncTask<OnTaskComplete, Void, Object[]> {
        protected Object[] doInBackground(OnTaskComplete... params) {
            Patient p = asyncLoadPatient();
            OnTaskComplete handler = params[0];
            return new Object[] {p, handler};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            Patient p = (Patient) params[0];
            OnTaskComplete handler = (OnTaskComplete) params[1];
            handler.onTaskComplete(p);
        }
    }

    private class SavePatientTask extends AsyncTask<Object, Void, OnTaskComplete> {
        protected OnTaskComplete doInBackground(Object... params) {
            Patient p = (Patient) params[0];
            OnTaskComplete handler = (OnTaskComplete) params[1];
            asyncSavePatient(p);
            return handler;
        }

        @Override
        protected void onPostExecute(OnTaskComplete runnable) {
            runnable.onTaskComplete(new Object());
        }
    }

    private class LoadSettingsTask extends AsyncTask<OnTaskComplete, Void, Object[]> {
        protected Object[] doInBackground(OnTaskComplete... params) {
            OnTaskComplete handler = params[0];
            GeneralIO.Settings s = asyncLoadSettings();
            return new Object[] {s, handler};
        }

        @Override
        protected void onPostExecute(Object[] params) {
            GeneralIO.Settings s = (GeneralIO.Settings) params[0];
            OnTaskComplete handler = (OnTaskComplete) params[1];
            handler.onTaskComplete(s);
        }
    }

    private class SaveSettingsTask extends AsyncTask<Object, Void, OnTaskComplete> {
        protected OnTaskComplete doInBackground(Object... params) {
            GeneralIO.Settings s = (GeneralIO.Settings) params[0];
            OnTaskComplete handler = (OnTaskComplete) params[1];
            asyncSaveSettings(s);
            return handler;
        }

        @Override
        protected void onPostExecute(OnTaskComplete runnable) {
            runnable.onTaskComplete(new Object());
        }
    }
}
