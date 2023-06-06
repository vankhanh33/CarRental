package com.greenhuecity.data.presenter;

import android.content.Context;
import android.widget.Toast;

import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritePresenter implements FavoriteContract.IPresenter {
    FavoriteContract.IView mView;
    List<Cars> carList;
    FavoriteCarDatabaseHelper db;
    ApiService apiService;

    public FavoritePresenter(FavoriteContract.IView mView,Context context) {
        this.mView = mView;
        carList = new ArrayList<>();
        db = new FavoriteCarDatabaseHelper(context);
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void getCarList( Context context) {
        if(db.getAllCars() != null && !db.getAllCars().isEmpty()){
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataExist();
        } else {
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataEmpty("Danh sách yêu thích trống");
        }
    }
    //
    @Override
    public void getCarListAPI() {
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
