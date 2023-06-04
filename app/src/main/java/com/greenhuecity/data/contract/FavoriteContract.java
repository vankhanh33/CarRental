package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.Cars;

import java.util.List;

public interface FavoriteContract {
    interface IView{
        void setDataRecyclerViewCar(List<Cars> mList);
        void setDataEmpty(String mess);
        void setDataExist();
    }
    interface IPresenter{
        void getCarList(Context context);

    }
}
