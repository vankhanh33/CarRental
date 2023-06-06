package com.greenhuecity.data.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.model.Cars;

import java.util.List;

public interface HomeContract {
    interface IView{

        void searchTextChangedListener(List<Cars> carsList);
        void getCarsList(List<Cars> carsList);
    }
    interface IPresenter{
        void getCarList();
        List<Cars> filterCarList(String searchText,List<Cars> carsList);
    }
}
