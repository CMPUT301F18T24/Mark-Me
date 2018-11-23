package com.cybersix.markme;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class ListObserver implements Observer {
    private TextView titleView = null;
    private TextView detailView = null;
    private View returnButton = null;
    private View addButton = null;
    private EditText searchField = null;
    private ListView listView = null;
    private ListController controller = null;

    ListObserver(@NonNull ListController controller) {
        this.controller = controller;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public TextView getDetailView() {
        return detailView;
    }

    public void setDetailView(TextView detailView) {
        this.detailView = detailView;
    }

    public View getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(View returnButton) {
        this.returnButton = returnButton;
    }

    public View getAddButton() {
        return addButton;
    }

    public void setAddButton(View addButton) {
        this.addButton = addButton;
    }

    public EditText getSearchField() {
        return searchField;
    }

    public void setSearchField(EditText searchField) {
        this.searchField = searchField;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.displayModelInfo(position);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        ListModel model = (ListModel) o;

        titleView.setText(model.getTitle());
        detailView.setText(model.getDetails());
    }
}
