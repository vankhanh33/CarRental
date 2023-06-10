package com.greenhuecity.data.model;

public class Brands {
    int id;
    String  brand_photo,brand_name,description;

    public int getId() {
        return id;
    }

    public String getBrand_photo() {
        return brand_photo;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getDescription() {
        return description;
    }

    public Brands(int id, String brand_photo, String brand_name, String description) {
        this.id = id;
        this.brand_photo = brand_photo;
        this.brand_name = brand_name;
        this.description = description;
    }
}
