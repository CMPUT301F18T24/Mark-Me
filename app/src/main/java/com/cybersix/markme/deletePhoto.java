package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class deletePhoto extends AppCompatActivity {
    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    ImageView delete_image = findViewById(R.id.imageView_delete_photo);
    Intent intent = getIntent();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photo);
//        opens an intent, grab the picture and then put it in the next function
        String bitmap = getIntent().getStringExtra(PHOTO_CONTENT);
//        intent.getStringExtra(PHOTO_CONTENT, bitmap);
        delete_image.setImageResource(Integer.parseInt(bitmap));

    }

    public void deleteImage(View view) {
        NavigationController.getInstance().switchToFragment(BodyFragment.class);
    }
}
