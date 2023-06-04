package com.greenhuecity.data.model;

public class Distributors {
    int id;
    String code, name,photo,address,description;
    double latitude,longitude;

    public Distributors(int id, String code, String name, String photo, String address, String description, double latitude, double longitude) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.photo = photo;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
