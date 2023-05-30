package com.greenhuecity.data.presenter;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.contract.HomeContract;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.IPresenter {
    HomeContract.IView mView;
    ApiService apiService;
    private Handler mHandler;
    private Runnable mRunnable;

    public HomePresenter(HomeContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
        public void getCarList(String id) {
            apiService.getCarByBrand(id).enqueue(new Callback<List<Car>>() {
                @Override
                public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                    List<Car> mList = response.body();
                    mView.setDataRecyclerViewCar(mList);

                }

                @Override
                public void onFailure(Call<List<Car>> call, Throwable t) {

                }
            });
        }

    @Override
    public void startAutoScroll(RecyclerView recyclerView) {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            int nextPosition = 0;
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());

            @Override
            public void run() {

                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                if (nextPosition < recyclerView.getAdapter().getItemCount()) {
                    if (lastVisible < recyclerView.getAdapter().getItemCount() - 1) {
                        nextPosition = lastVisible + 1;
                    } else {
                        nextPosition = 0;
                    }
                }
                layoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), nextPosition);
                mHandler.postDelayed(this, 6000);
            }
        };
        mHandler.postDelayed(mRunnable,6000);
    }

    @Override
    public void stopAutoScroll() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

}
