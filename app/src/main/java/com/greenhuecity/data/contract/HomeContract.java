package com.greenhuecity.data.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.model.Cars;

import java.util.List;

public interface HomeContract {
    interface IView{
        void setDataRecyclerViewCar(List<Cars> mList);
    }
    interface IPresenter{
        void getCarList(String brand);
        void startAutoScroll(final RecyclerView recyclerView);
        void stopAutoScroll();
    }
}
