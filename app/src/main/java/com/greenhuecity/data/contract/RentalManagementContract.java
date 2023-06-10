package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.RentManagement;

import java.util.List;

public interface RentalManagementContract {
    interface IView{
        void setDataRecyclerView(List<RentManagement> mList);
    }
    interface IPresenter{
        void getRentManagementList(int id);
        int getUsersId(Context context);

    }
}
