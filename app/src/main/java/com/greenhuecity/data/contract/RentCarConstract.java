package com.greenhuecity.data.contract;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.greenhuecity.data.model.Car;

public interface RentCarConstract {
    interface IView {
        void initGUI();

        void setDataTvRental();

        void setCarInfo(Car car);

        void showDistance(String formattedDistance);

        void setAddress(String addressLine);
    }

    interface IPresenter {
        void getCar(Car car);

        void onMapReady(GoogleMap googleMap, Car car);

        void drawRoute(GoogleMap googleMap, LatLng origin, LatLng destination);

        void getAddressFromLatLng(LatLng latLng);

        void upRentCar(Car car, String date_start, String date_end, int price);
    }
}
