package com.greenhuecity.data.model;

public class Rent_Car {
    int car_id,users_id;
    String rental_start_date,rental_end_date;
    int price;
    String status,payment_status;

    public Rent_Car(int car_id, int users_id, String rental_start_date, String rental_end_date, int price, String status, String payment_status) {
        this.car_id = car_id;
        this.users_id = users_id;
        this.rental_start_date = rental_start_date;
        this.rental_end_date = rental_end_date;
        this.price = price;
        this.status = status;
        this.payment_status = payment_status;
    }

    public int getCar_id() {
        return car_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public String getRental_start_date() {
        return rental_start_date;
    }

    public String getRental_end_date() {
        return rental_end_date;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getPayment_status() {
        return payment_status;
    }
}
