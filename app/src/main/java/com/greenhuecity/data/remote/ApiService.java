package com.greenhuecity.data.remote;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    //Lấy tất cả xe theo thương hiệu
    @POST("arr-car.php")
    @FormUrlEncoded
    Call<List<Cars>> getCarByBrand(@Field("brands") String brands);

    @POST("book-rental-car.php")
    @FormUrlEncoded
    Call<Cars> upRentCar(@Field("car_id") int car_id,
                         @Field("users_id") int users_id,
                         @Field("rental_start_date") String rental_start_date,
                         @Field("rental_end_date") String rental_end_date,
                         @Field("price") int price,
                         @Field("status") String status,
                         @Field("payment_status") String payment_status);

    //Lấy danh sách user
    @GET("arr-users.php")
    Call<List<Users>> getUsers();

    //register
    @POST("register-users.php")
    @FormUrlEncoded
    Call<Users> registerUsers(@Field("email") String email,
                              @Field("password") String password,
                              @Field("fullname") String fullName,
                              @Field("phone") String phone);
    //Lấy danh sách distributors
    @POST("distributors-id.php")
    @FormUrlEncoded
    Call<List<Distributors>> getDistributors(@Field("id") int id);
}
