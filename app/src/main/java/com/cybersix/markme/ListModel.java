package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Observable;

public class ListModel<T> extends Observable {
    private ArrayList<T> list = null;
    private ArrayAdapter<T> adapter = null;

    ListModel() {
        this.list = new ArrayList<T>();
        this.adapter = new ArrayAdapter<T>()
    }

    public void addObserver(@NonNull ListObserver observer) {
        addObserver(observer);

        setChanged();
        notifyObservers();
    }

    public void add(T item) {
        this.list.add(item);
    }
}
