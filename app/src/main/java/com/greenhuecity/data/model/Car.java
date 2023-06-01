package com.greenhuecity.data.model;

import java.io.Serializable;

public class Car implements Serializable {
    int car_id;
    String car_img,car_name;
    int rental_price;
    String max_speed,horsepower,mileage,car_description;
    double latitude, longitude;
    int users_id,category_id;
    String category_img,category_name;

    //Lấy thông tin xe và hãng


    public Car(int car_id, String car_img, String car_name, int rental_price, String max_speed, String horsepower, String mileage, String car_description, double latitude, double longitude, int users_id, int category_id, String category_img, String category_name) {
        this.car_id = car_id;
        this.car_img = car_img;
        this.car_name = car_name;
        this.rental_price = rental_price;
        this.max_speed = max_speed;
        this.horsepower = horsepower;
        this.mileage = mileage;
        this.car_description = car_description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.users_id = users_id;
        this.category_id = category_id;
        this.category_img = category_img;
        this.category_name = category_name;
    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar_img() {
        return car_img;
    }

    public String getCar_name() {
        return car_name;
    }

    public int getRental_price() {
        return rental_price;
    }

    public String getMax_speed() {
        return max_speed;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public String getMileage() {
        return mileage;
    }

    public String getCar_description() {
        return car_description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getUsers_id() {
        return users_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_img() {
        return category_img;
    }

    public String getCategory_name() {
        return category_name;
    }
}
