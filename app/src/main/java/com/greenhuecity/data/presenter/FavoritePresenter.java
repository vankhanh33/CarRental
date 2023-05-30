package com.greenhuecity.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Car;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavoritePresenter implements FavoriteContract.IPresenter {
    FavoriteContract.IView mView;
    List<Car> carList;
    FavoriteCarDatabaseHelper db;

    public FavoritePresenter(FavoriteContract.IView mView,Context context) {
        this.mView = mView;
        carList = new ArrayList<>();
        db = new FavoriteCarDatabaseHelper(context);
    }


    @Override
    public void getCarList( Context context) {
        if(db.getAllCars() != null && !db.getAllCars().isEmpty()){
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataExist();
        }
        else {
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataEmpty("Danh sách yêu thích trống");
        }
    }
}
