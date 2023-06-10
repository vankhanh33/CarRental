package com.greenhuecity.itf;

public interface OnClickButtonUserOrder {
    void eventCancelOrder(int order_id, int car_id);

    void eventCompleteOrder(int order_id, int car_id);

    void eventMapView(double latitude, double longitude);
}
