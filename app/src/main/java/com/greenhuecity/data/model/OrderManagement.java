package com.greenhuecity.data.model;

public class OrderManagement {
    int car_id;
    String car_name, car_photo, license_plates;
    int order_id;
    String status;
    double rental_costs;
    String code, from_time, end_time;
    int users_id;
    String fullname, photo;

    public OrderManagement(int car_id, String car_name, String car_photo, String license_plates, int order_id, String status, double rental_costs, String code, String from_time, String end_time, int users_id, String fullname, String photo) {
        this.car_id = car_id;
        this.car_name = car_name;
        this.car_photo = car_photo;
        this.license_plates = license_plates;
        this.order_id = order_id;
        this.status = status;
        this.rental_costs = rental_costs;
        this.code = code;
        this.from_time = from_time;
        this.end_time = end_time;
        this.users_id = users_id;
        this.fullname = fullname;
        this.photo = photo;
    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public String getCar_photo() {
        return car_photo;
    }

    public String getLicense_plates() {
        return license_plates;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getStatus() {
        return status;
    }

    public double getRental_costs() {
        return rental_costs;
    }

    public String getCode() {
        return code;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getUsers_id() {
        return users_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoto() {
        return photo;
    }
}
