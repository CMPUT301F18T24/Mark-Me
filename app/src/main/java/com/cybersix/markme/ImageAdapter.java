/**
 * CMPUT 301 Team 24
 *
 * This is the Image Adapter to be used in the full gallery. Will allow the image to be visible.
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
/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cybersix.markme.model.GalleryItemModel;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<GalleryItemModel> photos;

    public ImageAdapter(Context context, List<GalleryItemModel> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.linearlayout_image, parent, false);
        }
        final Bitmap bitmap = this.photos.get(position).getPhoto();
        final ImageView imageView = convertView.findViewById(R.id.imageview);
        imageView.setImageBitmap(bitmap);
        return convertView;
    }
}
