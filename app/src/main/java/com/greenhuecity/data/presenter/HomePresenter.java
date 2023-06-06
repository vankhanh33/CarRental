package com.greenhuecity.data.presenter;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.contract.CarContract;
import com.greenhuecity.data.contract.HomeContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.IPresenter {
    HomeContract.IView mView;
    ApiService apiService;

    public HomePresenter(HomeContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getCarList() {
        apiService.getCarByBrand("").enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                List<Cars> mList = response.body();
                mView.getCarsList(mList);
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {

            }
        });
    }

    @Override
    public List<Cars> filterCarList(String searchText, List<Cars> carsList) {
        List<Cars> filteredList = new ArrayList<>();
        for (Cars car : carsList) {
            if (car.getCar_name().toLowerCase().contains(searchText)
                    || car.getBrand_name().toLowerCase().contains(searchText)) {
                filteredList.add(car);
            }
        }
        return filteredList;
    }
}
