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
    private static final String MODE_FILENAME = "MODE.dat";
    private Context context = null;
    Patient patient = null;

    protected DiskIO() {

    }

    public void setContext(Context context) {
        this.context = context;
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
    public Patient loadPatient() {
        try {
            FileInputStream fis = context.openFileInput(PATIENT_FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeList = new TypeToken<Patient>(){}.getType();
            patient = gson.fromJson(reader, typeList);
            Log.i("DISKIO", patient.getProblems().get(0).getRecords().size() + "");

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

    public void setPatient(Patient patient) {
        this.patient = patient;
        savePatient();
    }

    public void savePatient() {
        try {
            FileOutputStream fos = context.openFileOutput(PATIENT_FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(patient, writer);
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

    public void deleteUser() {
        File file = new File(PATIENT_FILENAME);
        if (file.exists())
            file.delete();
    }

    public boolean loadPreviousMode() {
        Boolean result = false;
        try {
            FileInputStream fis = context.openFileInput(MODE_FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeList = new TypeToken<Boolean>(){}.getType();
            result = gson.fromJson(reader, typeList);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void savePreviousMode(Boolean mode) {
        try {
            FileOutputStream fos = context.openFileOutput(MODE_FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(mode, writer);
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
}
