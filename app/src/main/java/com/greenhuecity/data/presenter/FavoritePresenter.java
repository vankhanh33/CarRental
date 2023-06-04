package com.greenhuecity.data.presenter;

import android.content.Context;
import android.widget.Toast;

import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Cars;

import java.util.ArrayList;
import java.util.List;


public class FavoritePresenter implements FavoriteContract.IPresenter {
    FavoriteContract.IView mView;
    List<Cars> carList;
    FavoriteCarDatabaseHelper db;

    public FavoritePresenter(FavoriteContract.IView mView,Context context) {
        this.mView = mView;
        carList = new ArrayList<>();
        db = new FavoriteCarDatabaseHelper(context);
    }


    @Override
    public void getCarList( Context context) {
        Toast.makeText(context, db.getAllCars().size() + "", Toast.LENGTH_SHORT).show();
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
