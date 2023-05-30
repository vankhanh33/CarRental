package com.greenhuecity.data.contract;

import android.content.Context;
import android.widget.ImageView;

import com.greenhuecity.data.model.Car;

public interface CarDetailContract {
    interface IView{
        void setDataDetailCar(Car car);
        boolean isCheckCarFavoriteDB(Car car);
    }
    interface IPresenter{
        void getDataDetailCar(Car car);
        void updateDataFavorite(Car car, ImageView img);
    }
}
