package com.cybersix.markme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Gallery extends Fragment {
    private ImageView imageView[];
    private int counter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_gallery, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        counter = 0;
        imageView = new ImageView[10];
        imageView[0] = getView().findViewById(R.id.imageView0);
        imageView[1] = getView().findViewById(R.id.imageView1);
        imageView[2] = getView().findViewById(R.id.imageView2);
        imageView[3] = getView().findViewById(R.id.imageView3);
        imageView[4] = getView().findViewById(R.id.imageView4);
        imageView[5] = getView().findViewById(R.id.imageView5);
        imageView[6] = getView().findViewById(R.id.imageView6);
        imageView[7] = getView().findViewById(R.id.imageView7);
        imageView[8] = getView().findViewById(R.id.imageView8);
        imageView[9] = getView().findViewById(R.id.imageView9);
        //imageGet;
    }

    private void imageGet(){
        //get the image as an byte array, then call:
        byte byteArray[] = new byte[8196];
        int resultSetSize = 0;
        for (int i=0;i< resultSetSize; i++){
            imageLoader(byteArray);
        }
    }

    private void imageLoader(byte[] b){
//        Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(b, 0, b.length));
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        imageView[counter].setVisibility(View.VISIBLE);
        imageView[counter].setImageBitmap(bmp);
        counter ++;

    }

    public void img0(View view) {
        //send the image to another activity t delete server side or show!
    }
    public void img1(View view) {
    }
    public void img2(View view) {
    }
    public void img3(View view) {
    }
    public void img4(View view) {
    }
    public void img5(View view) {
    }
    public void img6(View view) {
    }
    public void img7(View view) {
    }
    public void img8(View view) {
    }
    public void img9(View view) {
    }

}
