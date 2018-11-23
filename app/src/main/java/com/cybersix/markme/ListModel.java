package com.cybersix.markme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ListModel<T extends ListItemModel> extends Observable {
    private String title = null;
    private String details = null;
    private ArrayList<T> list = null;
    private ArrayAdapter<T> adapter = null;

    ListModel(Context context, int layoutId) {
        this.list = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(context, layoutId, this.list);
    }

    public void addObserver(@NonNull ListObserver observer) {
        addObserver((Observer) observer);
        observer.getListView().setAdapter(this.adapter);

        setChanged();
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;

        setChanged();
        notifyObservers();
    }

    public void setDetails(@NonNull String details) {
        this.details = details;

        setChanged();
        notifyObservers();
    }

    public void add(@NonNull T item) {
        this.list.add(item);
        this.adapter.notifyDataSetChanged();
    }

    public void delete(@NonNull T item) {
        this.list.remove(item);
        this.adapter.notifyDataSetChanged();
    }

    public T get(int position) {
        if (this.list.isEmpty())
            return null;

        return this.list.get(position);
    }
}
