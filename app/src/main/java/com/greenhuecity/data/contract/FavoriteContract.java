package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.Cars;

import java.util.List;

public interface FavoriteContract {
    interface IView{
        void setDataRecyclerViewCar(List<Cars> mList);
        void setDataEmpty(String mess);
        void setDataExist();
        void searchTextChangedListener(List<Cars> carsList);
        void getCarsList(List<Cars> carsList);
    }
    interface IPresenter{
        void getCarList(Context context);
        void getCarListAPI();
        List<Cars> filterCarList(String searchText,List<Cars> carsList);
    }
}
