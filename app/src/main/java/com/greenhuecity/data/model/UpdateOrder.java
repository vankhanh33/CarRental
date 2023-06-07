package com.greenhuecity.data.model;

public class UpdateOrder {

    int order_id;
    String order_status;
    int car_id;
    String car_status;

    public UpdateOrder(int order_id, String order_status, int car_id, String car_status) {
        this.order_id = order_id;
        this.order_status = order_status;
        this.car_id = car_id;
        this.car_status = car_status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar_status() {
        return car_status;
    }
}
