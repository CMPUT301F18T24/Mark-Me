/**
 * CMPUT 301 Team 24
 *
 * This is the delete photo class to delete a photo from the full gallery
 *
 * Version 0.1
 *
 * Date: 2018-11-25
 *
 * Copyright Notice
 * @author Dorsa Nahid
 * @see com.cybersix.markme.fragment.FullGalleryFragment
 */
package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.fragment.BodyFragment;
import com.cybersix.markme.fragment.FullGalleryFragment;

public class deletePhoto extends AppCompatActivity {
    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    ImageView delete_image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photo);
        delete_image = findViewById(R.id.imageView_delete_photo);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            int position = getIntent().getIntExtra(PHOTO_CONTENT,0);
            delete_image.setImageBitmap(FullGalleryFragment.bitmaps.get(position));

        }

//        opens an intent, grab the picture and then put it in the next function
    }

    public void deleteImage(View view) {
        NavigationController.getInstance().switchToFragment(BodyFragment.class);
    }
}
