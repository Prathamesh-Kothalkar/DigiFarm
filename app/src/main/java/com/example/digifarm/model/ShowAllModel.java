package com.example.digifarm.model;

import java.io.Serializable;

public class ShowAllModel implements Serializable {
    String uid,name,category,city,img_url;
    int price,quantity;

    public ShowAllModel(){

    }

    public ShowAllModel(String img_url,String name, String category, String city, String uid, int price,int quantity){
        this.img_url=img_url;
        this.category=category;
        this.name=name;
        this.price=price;
        this.city=city;
        this.uid=uid;
        this.quantity=quantity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String desc) {
        this.uid = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}




}
