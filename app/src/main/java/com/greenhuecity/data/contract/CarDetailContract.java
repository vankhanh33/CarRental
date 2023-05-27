package com.greenhuecity.data.contract;

import com.greenhuecity.data.model.Car;

public interface CarDetailContract {
    interface IView{
        void setDataDetailCar(Car car);
    }
    interface IPresenter{
        void getDataDetailCar(Car car);
    }
}
