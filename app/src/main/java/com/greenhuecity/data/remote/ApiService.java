package com.greenhuecity.data.remote;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.OrderItems;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.model.Orders;
import com.greenhuecity.data.model.UpdateOrder;
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
    @GET("arr-distributor.php")
    Call<List<Distributors>> getDistributor();

    @POST("distributors-id.php")
    @FormUrlEncoded
    Call<List<Distributors>> getDistributors(@Field("id") int id);

    //Xử lí đặt hàng
    @POST("up-orders.php")
    @FormUrlEncoded
    Call<Orders> upOrders(@Field("code") String code,
                          @Field("from_time") String from_time,
                          @Field("end_time") String end_time,
                          @Field("users_id") int users_id);

    @POST("orders-code.php")
    @FormUrlEncoded
    Call<List<Orders>> getOrders(@Field("cod") String cod);

    @POST("up-order-items.php")
    @FormUrlEncoded
    Call<OrderItems> upOrderItems(@Field("car_id") int car_id,
                                  @Field("order_id") int order_id,
                                  @Field("price") double price);

    //Xử lí đơn đặt hàng
    @POST("order-management.php")
    @FormUrlEncoded
    Call<List<OrderManagement>> getOrderManagement(@Field("id") int id);

    //update order
    @POST("update-order.php")
    @FormUrlEncoded
    Call<UpdateOrder> updateOrders(@Field("order_id") int order_id,
                                   @Field("order_status") String order_status,
                                   @Field("car_id") int car_id,
                                   @Field("car_status") String car_status);

}
