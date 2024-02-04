package com.example.digifarm;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    String shopname;
    String name;

    String number;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
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

    public Item(String shopname, String name, String number, int image) {
        this.name = name;
        this.shopname = shopname;
        this.number = number;
        this.image = image;
    }


}
