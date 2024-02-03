package com.example.digifarm.model;

public class CategoryModel {
    String img_url,name,type;

    public CategoryModel(){

    }
    public CategoryModel(String img_url,String name,String type){
        this.img_url=img_url;
        this.name=name;
        this.type=type;
    }

    public void setImg_url(String img_url){
        this.img_url=img_url;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setType(String type){
        this.type=type;
    }

    public String getImg_url(){
        return this.img_url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

