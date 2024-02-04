package com.example.digifarm.model;

public class PopularProductModel {
    String img_url,name,category;
    int rupees;

    public PopularProductModel(){

    }

    public PopularProductModel(String img_url, String name, String category, int rupees) {
        this.img_url = img_url;
        this.name = name;
        this.category = category;
        this.rupees = rupees;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getRupees() {
        return rupees;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRupees(int rupees) {
        this.rupees = rupees;
    }
}

