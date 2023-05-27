package com.greenhuecity.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.CarFavoriteContract;
import com.greenhuecity.data.model.Car;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CarFavoritePresenter implements CarFavoriteContract.IPresenter {
    CarFavoriteContract.IView mView;
    List<Car> carList;

    public CarFavoritePresenter(CarFavoriteContract.IView mView) {
        this.mView = mView;
        carList = new ArrayList<>();
    }


    @Override
    public void getCarList(int id, Context context) {
        SharedPreferences pref = context.getSharedPreferences("favorite", MODE_PRIVATE);
        Gson gson = new Gson();
        String carListJson = pref.getString("car_list", "");
        if (!carListJson.isEmpty()) {
            Type type = new TypeToken<List<Car>>() {
            }.getType();
            List<Car> list = gson.fromJson(carListJson, type);
            if (id == 0) {
                carList = list;
            } else
                for (Car c : list)
                    if (c.getCategory_id() == id) carList.add(c);
            mView.setDataRecyclerViewCar(carList);
        }

    }
}
