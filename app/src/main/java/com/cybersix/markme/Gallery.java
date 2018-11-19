package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Gallery extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "gallery_mode";
    public static final String RECORD_MODE = "r";
    public static final String ENTIRE_PICS = "e";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            String message = extras.getString(EXTRA_MESSAGE);
            switch (message){
                case RECORD_MODE:
                    //LOAD
                    break;
                case ENTIRE_PICS:
                    //LOAD
                    break;
            }
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Gallery.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}
