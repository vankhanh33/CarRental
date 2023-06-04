package com.greenhuecity.data.model;

import java.io.Serializable;

public class Cars implements Serializable {
    int car_id;
    String car_img, car_name;
    double price;
    String description,status,from_time,end_time, approve;
    int power_id;
    String power_name, power_value;
    int spec_id;
    double  spec_top_speed, spec_horse_power, spec_mileage;
    int brand_id;
    String brand_img,brand_name;
    int distributor_id;

    public Cars(int car_id, String car_img, String car_name, double price, String description, String status, String from_time, String end_time, String approve, int power_id, String power_name, String power_value, int spec_id, double spec_top_speed, double spec_horse_power, double spec_mileage, int brand_id, String brand_img, String brand_name, int distributor_id) {
        this.car_id = car_id;
        this.car_img = car_img;
        this.car_name = car_name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.from_time = from_time;
        this.end_time = end_time;
        this.approve = approve;
        this.power_id = power_id;
        this.power_name = power_name;
        this.power_value = power_value;
        this.spec_id = spec_id;
        this.spec_top_speed = spec_top_speed;
        this.spec_horse_power = spec_horse_power;
        this.spec_mileage = spec_mileage;
        this.brand_id = brand_id;
        this.brand_img = brand_img;
        this.brand_name = brand_name;
        this.distributor_id = distributor_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public int getPower_id() {
        return power_id;
    }

    public void setPower_id(int power_id) {
        this.power_id = power_id;
    }

    public String getPower_name() {
        return power_name;
    }

    public void setPower_name(String power_name) {
        this.power_name = power_name;
    }

    public String getPower_value() {
        return power_value;
    }

    public void setPower_value(String power_value) {
        this.power_value = power_value;
    }

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public double getSpec_top_speed() {
        return spec_top_speed;
    }

    public void setSpec_top_speed(double spec_top_speed) {
        this.spec_top_speed = spec_top_speed;
    }

    public double getSpec_horse_power() {
        return spec_horse_power;
    }

    public void setSpec_horse_power(double spec_horse_power) {
        this.spec_horse_power = spec_horse_power;
    }

    public double getSpec_mileage() {
        return spec_mileage;
    }

    public void setSpec_mileage(double spec_mileage) {
        this.spec_mileage = spec_mileage;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_img() {
        return brand_img;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }
}
