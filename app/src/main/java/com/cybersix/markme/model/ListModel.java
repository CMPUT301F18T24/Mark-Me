package com.cybersix.markme.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.cybersix.markme.controller.ListObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListModel<T extends ListItemModel> extends Observable {
    private String title = null;
    private String details = null;
    private ArrayList<T> list = null;
    private ArrayAdapter<T> adapter = null;
    private final Class<T> type;

    public ListModel(Context context, int layoutId, Class<T> clazz) {
        this.list = new ArrayList<T>();
        this.adapter = new ArrayAdapter<T>(context, layoutId, this.list);
        this.type = clazz;
    }

    public void addObserver(ListObserver observer) {
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
        this.adapter.add(item);
        this.adapter.notifyDataSetChanged();
    }

    public void delete(@NonNull T item) {
        this.adapter.remove(item);
        this.adapter.notifyDataSetChanged();
    }

    public T get(int position) {
        if (this.list.isEmpty())
            return null;

        return this.adapter.getItem(position);
    }

    public List<T> getAll() {
        return this.list;
    }

    public int size() {
        return this.list.size();
    }

    public T back() {
        return get(this.list.size() - 1);
    }

    public T addNewItem() {
        T item = createItem();
        if (item != null)
            add(item);

        return item;
    }

    public void refresh() {
        setChanged();
        notifyObservers();
        this.adapter.notifyDataSetChanged();
    }

    private T createItem() {
        try {
            return type.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
