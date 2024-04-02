package com.example.digifarm.model;

import android.graphics.Bitmap;

public class MyContent {
    private String text;
    private Bitmap image;

    public MyContent(String text, Bitmap image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
