package com.greenhuecity.data.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.RentalManagementActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentalMnDetailPresenter {
    Context context;
    ApiService apiService;

    public RentalMnDetailPresenter(Context context) {
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);

    }


    public void censorshipUpdate(String id, String approve, String mess) {
        apiService.updateCensored(id, approve).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loadingCensorshipMess(mess);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadingCensorshipMess(mess);
            }
        });
    }

    public void loadingCensorshipMess(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không cho phép hủy
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
        // Hiển thị ProgressDialog
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                builder.setTitle("Thông báo");
                builder.setMessage(mess);
                AlertDialog dialog = builder.create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        context.startActivity(new Intent(context, RentalManagementActivity.class));
                    }
                }, 3000);
            }
        }, 2000);
    }

}
