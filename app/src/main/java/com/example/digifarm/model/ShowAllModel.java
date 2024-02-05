package com.example.digifarm.model;

public class ShowAllModel {
    String desc,name,category,city,img_url;
    int price;

    public ShowAllModel(){

    }

    public ShowAllModel(String img_url,String name, String category, String city, String desc, int price){
        this.img_url=img_url;
        this.category=category;
        this.name=name;
        this.price=price;
        this.city=city;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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




}
