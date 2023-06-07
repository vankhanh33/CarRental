package com.greenhuecity.data.contract;

import android.widget.ImageView;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;

public interface CarDetailContract {
    interface IView{
        void setDataDetailCar(Cars car);
        boolean isCheckCarFavoriteDB(Cars car);
        void setDistributors(Distributors distributors);
        void notloggedIn(String mess);
    }
    interface IPresenter{
        void getDataDetailCar(Cars car);
        void updateDataFavorite(Cars car, ImageView img);
        void getDistributors(int id);
        boolean isLogged();
    }
}
