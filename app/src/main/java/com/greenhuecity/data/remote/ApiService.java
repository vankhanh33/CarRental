package com.greenhuecity.data.remote;

import com.greenhuecity.data.model.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    //Lấy tất cả xe
    @GET("get-car-list.php")
    Call<List<Car>> getCar();

    //Lấy tất cả xe theo thương hiệu
    @POST("get-car-list-brand.php")
    @FormUrlEncoded
    Call<List<Car>> getCarByBrand(@Field("brand") String brand);
}
