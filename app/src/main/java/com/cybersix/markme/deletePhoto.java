package com.cybersix.markme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.fragment.BodyFragment;

import java.io.FileInputStream;

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

            Bitmap bmp = null;
            String filename = getIntent().getStringExtra("image");
            try {
                FileInputStream is = this.openFileInput(filename);
                bmp = BitmapFactory.decodeStream(is);
                delete_image.setImageBitmap(bmp);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//        opens an intent, grab the picture and then put it in the next function
    }

    public void deleteImage(View view) {
        NavigationController.getInstance().switchToFragment(BodyFragment.class);
    }
}
