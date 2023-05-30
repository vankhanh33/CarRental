package com.greenhuecity.data.presenter;

import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.greenhuecity.R;

import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.view.CarDetailActivity;

public class CarDetailPresenter implements CarDetailContract.IPresenter {
    CarDetailContract.IView mView;
    FavoriteCarDatabaseHelper db;
    CarDetailActivity activity;


    public CarDetailPresenter(CarDetailContract.IView mView,CarDetailActivity activity) {
        this.mView = mView;
        this.activity = activity;
    }


    @Override
    public void getDataDetailCar(Car car) {
        mView.setDataDetailCar(car);
    }

    @Override
    public void updateDataFavorite(Car car, ImageView img) {
        db = new FavoriteCarDatabaseHelper(activity);
        if(!mView.isCheckCarFavoriteDB(car)) {
            img.setImageResource(R.drawable.favorite);
            db.addCar(car);
        }
        else {
            img.setImageResource(R.drawable.heart);
            db.deleteCar(car.getCar_id());
        }


    }
}
