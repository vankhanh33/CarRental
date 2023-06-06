package com.greenhuecity.data.model;

public class Orders {
    int id;
    String code,status,from_time,end_time;
    int user_id;

    public Orders(int id, String code, String status, String from_time, String end_time, int user_id) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.from_time = from_time;
        this.end_time = end_time;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getUser_id() {
        return user_id;
    }
}
