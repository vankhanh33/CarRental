package com.greenhuecity.data.model;

public class Powers {
    int id;
    String power_name,value;

    public int getId() {
        return id;
    }
    public String getPower_name() {
        return power_name;
    }

    public String getValue() {
        return value;
    }

    public Powers(int id, String power_name, String value) {
        this.id = id;
        this.power_name = power_name;
        this.value = value;
    }
}
