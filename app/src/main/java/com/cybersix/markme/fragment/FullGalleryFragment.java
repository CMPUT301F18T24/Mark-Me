/**
 * CMPUT 301 Team 24
 *
 * This is the full gallery fragment used to view all of the photos of all of the records and problems
 * the user may have. This will also allow the user to edit any of their photos as they wish
 *
 * Version 0.1
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Dorsa Nahid
 * @see com.cybersix.markme.fragment.RecordInfoFragment
 */
package com.cybersix.markme.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cybersix.markme.ImageAdapter;
import com.cybersix.markme.R;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.deletePhoto;

import java.io.FileOutputStream;

public class FullGalleryFragment extends Fragment {
    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    public static final String GALLERY_MODE = "ca.cybersix.gallerymode";
    private boolean galleryViewMode;


    private Bitmap bitmaps[];
    private int counter;

    public FullGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_gallery, container, false);
//        Bitmap b0 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ic_gallery);
//        Bitmap b1 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ic_body);
        bitmaps = new Bitmap[2000];
        counter = 0;
        int size;

        if(getArguments()!=null) {
            int problemIndex = getArguments().getInt(GALLERY_MODE);
            if (RecordController.getInstance().getSelectedProblemRecords().get(problemIndex) == null) {
                size = 0;
            } else {
                size = RecordController.getInstance().getSelectedProblemRecords().get(problemIndex).getPhotos().size();
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    this.bitmaps[counter] = RecordController.getInstance().getSelectedProblemRecords().get(problemIndex).getPhotos().get(j);
                    counter++;
                }
            }

        }else{

            if (RecordController.getInstance().getSelectedProblemRecords() == null) {
                size = 0;
            } else {
                size = RecordController.getInstance().getSelectedProblemRecords().size();
            }

            for (int i = 0; i < size; i++) {
                int recordSize = RecordController.getInstance().getSelectedProblemRecords().get(i).getPhotos().size();
                for (int j = 0; j < recordSize; j++) {
                    this.bitmaps[counter] = RecordController.getInstance().getSelectedProblemRecords().get(i).getPhotos().get(j);
                    counter++;
                }
            }

        }
//        bitmaps[0]= b0;
//        bitmaps[1]= b1;
//        bitmaps[2]= b0;
//        bitmaps[3]= b1;


        GridView gridView = view.findViewById(R.id.fragment_full_gallery_gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity(), this.bitmaps);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap bitmap = FullGalleryFragment.this.bitmaps[position];
                Intent intent = new Intent(getActivity().getBaseContext(),
                        deletePhoto.class);

                try {
                    //Write file
                    String filename = "bitmap.png";
                    FileOutputStream stream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    //Cleanup
                    stream.close();
                    bitmap.recycle();

                    //Pop intent
                    intent.putExtra(PHOTO_CONTENT, filename);
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        return view;
    }
}
