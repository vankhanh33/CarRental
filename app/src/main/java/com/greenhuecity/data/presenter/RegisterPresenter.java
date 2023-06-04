package com.greenhuecity.data.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Patterns;
import android.widget.Toast;

import com.greenhuecity.data.contract.RegisterContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.util.Utils;
import com.greenhuecity.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.IPresenter {
    RegisterContract.IView mView;
    LoginActivity loginActivity;
    ApiService apiService;
    List<Users> usersList = new ArrayList<>();
    ;
    boolean isCancelled = false;

    public RegisterPresenter(RegisterContract.IView mView, LoginActivity loginActivity) {
        this.mView = mView;
        this.loginActivity = loginActivity;
        apiService = RetrofitClient.getClient().create(ApiService.class);

    }

    @Override
    public void register(String name, String password, String email, String phone, List<Users> mList) {
        String pass = Utils.sha256(password);
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            mView.showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mView.showErrorMessage("Địa chỉ email không hợp lệ!");
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                mView.showErrorMessage("Số điện thoại không hợp lệ!");
            } else if (isExistUsers(email, phone, mList)) {
                mView.showErrorMessage("Email hoặc số điện thoại đã tồn tại");
            } else {
                apiService.registerUsers(email, pass, name, phone).enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                    }
                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                    }
                });
                mView.showSuccessMessage("Đăng ký thành công!");
            }
        }
    }

    @Override
    public void loadingRegister(String name, String password, String email, String phone, List<Users> mList) {
        ProgressDialog progressDialog = new ProgressDialog(loginActivity);
        progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không cho phép hủy
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Huỷ công việc của bạn và ẩn ProgressDialog
                progressDialog.dismiss();
                isCancelled = true;
            }
        });
        // Hiển thị ProgressDialog
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if (!isCancelled) {
                    register(name, password, email, phone, mList);
                }
                isCancelled = false;
            }
        }, 2999);
    }

    @Override
    public void getListCar() {
        apiService.getUsers().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                usersList = response.body();
                mView.setListCar(usersList);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean isExistUsers(String email, String phone, List<Users> usersList) {
        for (int i = 0; i < usersList.size(); i++) {
            Toast.makeText(loginActivity, ""+usersList.size(), Toast.LENGTH_SHORT).show();
            if (usersList.get(i).getPhone().equals(phone) || usersList.get(i).getEmail().equals(email))
                return true;
        }
        return false;
    }

}
