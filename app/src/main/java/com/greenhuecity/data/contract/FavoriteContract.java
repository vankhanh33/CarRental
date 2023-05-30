package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.Car;

import java.util.List;

public interface FavoriteContract {
    interface IView{
        void setDataRecyclerViewCar(List<Car> mList);
        void setDataEmpty(String mess);
        void setDataExist();
    }
    interface IPresenter{
        void getCarList(Context context);

    }
}
