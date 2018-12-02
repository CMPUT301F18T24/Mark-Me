package com.cybersix.markme.model;

import android.graphics.Bitmap;

public class GalleryItemModel {
    private int problemIndex;
    private Bitmap photo;

    public int getProblemIndex() {
        return problemIndex;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public GalleryItemModel(int problemIndex, Bitmap photo) {
        this.problemIndex = problemIndex;
        this.photo = photo;
    }
}
