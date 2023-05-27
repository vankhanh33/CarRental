package com.greenhuecity.data.presenter;

import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.model.Car;

public class CarDetailPresenter implements CarDetailContract.IPresenter {
    CarDetailContract.IView mView;

    public CarDetailPresenter(CarDetailContract.IView mView) {
        this.mView = mView;
    }


    @Override
    public void getDataDetailCar(Car car) {
        mView.setDataDetailCar(car);
    }
}
