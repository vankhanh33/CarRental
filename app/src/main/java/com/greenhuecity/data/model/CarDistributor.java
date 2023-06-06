package com.greenhuecity.data.model;


import java.io.Serializable;

public class CarDistributor implements Serializable {
    Cars cars;
    Distributors distributors;

    public CarDistributor(Cars cars, Distributors distributors) {
        this.cars = cars;
        this.distributors = distributors;
    }

    public Cars getCars() {
        return cars;
    }

    public Distributors getDistributors() {
        return distributors;
    }
}
