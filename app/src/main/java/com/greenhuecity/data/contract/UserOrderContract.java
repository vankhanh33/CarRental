package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.UserOrder;

import java.util.List;

public interface UserOrderContract {
    interface IView{
        void setDataRecyclerView(List<UserOrder> mList);
    }
    interface IPresenter{
        void getUserOrderList(int id);
        int getUsersId();
        void updateStatusOrder(int order_id,String order_status, int car_id,String car_status);

    }
}
