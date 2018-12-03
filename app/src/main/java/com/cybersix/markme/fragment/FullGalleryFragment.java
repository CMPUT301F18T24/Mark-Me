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

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.cybersix.markme.SlideShowActivity;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.deletePhoto;
import com.cybersix.markme.model.GalleryItemModel;

import java.util.ArrayList;
import java.util.List;

public class FullGalleryFragment extends Fragment {
    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    public static final String GALLERY_MODE = "ca.cybersix.gallerymode";


    public static List<GalleryItemModel> photos;

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
        photos = new ArrayList<GalleryItemModel>();
        int size;

        if(getArguments()!=null) {
            int problemIndex = getArguments().getInt(GALLERY_MODE);
            if (RecordController.getInstance().getSelectedProblemRecords().get(problemIndex) == null) {
                size = 0;
            } else {
                size = RecordController.getInstance().getSelectedProblemRecords().get(problemIndex).getPhotos().size();
            }

            for (int i = 0; i < size; i++) {
                byte[] photo = RecordController.getInstance().getSelectedProblemRecords().get(problemIndex).getPhotos().get(i);
                photos.add(new GalleryItemModel(
                        problemIndex,
                        BitmapFactory.decodeByteArray(photo, 0, photo.length)
                ));
            }
        } else if (ProblemController.getInstance().getSelectedProblem() != null) {
            if (RecordController.getInstance().getSelectedProblemRecords() == null) {
                size = 0;
            } else {
                size = RecordController.getInstance().getSelectedProblemRecords().size();
            }

            for (int i = 0; i < size; i++) {
                int recordSize = RecordController.getInstance().getSelectedProblemRecords().get(i).getPhotos().size();
                for (int j = 0; j < recordSize; j++) {
                    byte[] photo = RecordController.getInstance().getSelectedProblemRecords().get(i).getPhotos().get(i);
                    photos.add(new GalleryItemModel(
                            i,
                            BitmapFactory.decodeByteArray(photo, 0, photo.length)
                    ));
                }
            }
        }


        GridView gridView = view.findViewById(R.id.fragment_full_gallery_gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity(), photos);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(),
                        SlideShowActivity.class);
                intent.putExtra(PHOTO_CONTENT, position);
                getActivity().startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(),
                        deletePhoto.class);
                intent.putExtra(PHOTO_CONTENT, position);
                getActivity().startActivity(intent);
                return true;
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        return view;
    }

}

