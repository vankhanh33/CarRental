package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.RentalManagementContract;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentalManagementPresenter implements RentalManagementContract.IPresenter {
    RentalManagementContract.IView mView;
    ApiService apiService;
    public RentalManagementPresenter(RentalManagementContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }
    @Override
    public void getRentManagementList(int id) {
        apiService.getRentCars(id).enqueue(new Callback<List<RentManagement>>() {
            @Override
            public void onResponse(Call<List<RentManagement>> call, Response<List<RentManagement>> response) {
                List<RentManagement> managementList = response.body();
                mView.setDataRecyclerView(managementList);
            }

            @Override
            public void onFailure(Call<List<RentManagement>> call, Throwable t) {

            }
        });

    }

    @Override
    public int getUsersId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            return  users.getId();
        }
        return 0;
    }


}
