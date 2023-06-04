package com.greenhuecity.data.contract;


import com.greenhuecity.data.model.Users;

import java.util.List;

public interface RegisterContract {
    interface IView{
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
        void setListCar( List<Users> usersList);

    }
    interface IPresenter{
        void register(String name,String password,String email,String phone,List<Users> mList);
        void loadingRegister(String name,String password,String email,String phone,List<Users> mList);
        void getListCar();
        boolean isExistUsers(String email, String phone,List<Users> usersList);
    }
}
