/**
 * CMPUT 301 Team 24
 *
 * Gallery to grab all the info from the server
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Dorsa Nahid
 */
package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Gallery extends Fragment implements View.OnClickListener {

    private ImageView imageView[];
    private Bitmap bitmapArray[];
    private int counter;
    private final String EXTRA_MESSAGE = "com.cybersix.MESSAGE";
    private final String EXTRA_PHOTO = "com.cybersix.Photo";


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
        bitmapArray = new Bitmap[10];
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
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null){
            int recordId = extras.getInt(EXTRA_MESSAGE);
            int size  = RecordController.getInstance().selectedProblemRecords.get(recordId).getPhotos().size();
            imageGet(size);
        }else{
            Intent intent = new Intent(getContext(), FullGalleryFragment.class);
            startActivity(intent);
        }
    }

    private void imageGet(int size){
        for (int i=0;i< size; i++){
            Bitmap bitmap = RecordController.getInstance().selectedProblemRecords.get(i).getPhotos().get(i);
            bitmapArray[i] = bitmap;
            imageLoader(bitmap);
        }
    }

    //TODO: pass the byte array from server to the function below
    private void imageLoader(Bitmap bmp){
        imageView[counter].setVisibility(View.VISIBLE);
        imageView[counter].setImageBitmap(bmp);
        counter ++;

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), deletePhoto.class);
        switch (view.getId()) {
            case R.id.imageView0:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[0]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView1:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[1]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView2:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[2]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView3:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[3]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView4:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[4]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView5:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[5]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView6:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[6]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView7:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[7]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView8:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[8]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
            case R.id.imageView9:
                intent.putExtra(EXTRA_PHOTO,bitmapArray[9]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                //send the image to another activity t delete server side or show!
                break;
        }
    }
}
