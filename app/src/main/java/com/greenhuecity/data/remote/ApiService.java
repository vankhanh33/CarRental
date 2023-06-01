package com.greenhuecity.data.remote;

import com.greenhuecity.data.model.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    //Lấy tất cả xe theo thương hiệu
    @POST("get-car-list.php")
    @FormUrlEncoded
    Call<List<Car>> getCarByBrand(@Field("brand") String brand);
    @POST("book-rental-car.php")
    @FormUrlEncoded
    Call<Car> upRentCar(@Field("car_id") int car_id,
                              @Field("users_id") int users_id,
                              @Field("rental_start_date") String rental_start_date,
                              @Field("rental_end_date") String rental_end_date,
                              @Field("price") int price,
                              @Field("status") String status,
                              @Field("payment_status") String payment_status);
}
