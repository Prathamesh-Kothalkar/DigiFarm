package com.example.digifarm;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    String name;
    String email;
    String number;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Item(String name, String email, String number, int image) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.image = image;
    }


}
