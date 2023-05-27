package com.greenhuecity.data.contract;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.model.Car;

import java.util.List;

public interface CarFavoriteContract {
    interface IView{
        void setDataRecyclerViewCar(List<Car> mList);
    }
    interface IPresenter{
        void getCarList(int id, Context context);

    }
}
