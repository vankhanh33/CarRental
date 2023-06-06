package com.greenhuecity.data.model;

public class OrderItems {
    int id,car_id,order_id;
    double price;

    public OrderItems(int id, int car_id, int order_id, double price) {
        this.id = id;
        this.car_id = car_id;
        this.order_id = order_id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCar_id() {
        return car_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public double getPrice() {
        return price;
    }
}
