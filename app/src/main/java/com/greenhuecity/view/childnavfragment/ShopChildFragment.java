package com.greenhuecity.view.childnavfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.MainActivity;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.BrandContract;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.presenter.BrandPresenter;
import com.greenhuecity.view.adapter.CarRecyclerViewAdapter;

import java.util.List;

public class ShopChildFragment extends Fragment implements BrandContract.IView {
    RecyclerView rvCar;
    BrandPresenter brandPresenter;

    CarRecyclerViewAdapter mAdapter;
    List<Car> carList;
    String id;


    public ShopChildFragment(String id) {
        this.id = id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        rvCar = view.findViewById(R.id.recyclerView_car);
        rvCar.setHasFixedSize(true);
        rvCar.setNestedScrollingEnabled(false);
        rvCar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rvCar.setLayoutManager(new GridLayoutManager(getContext(), 2));
        brandPresenter = new BrandPresenter(this);
        brandPresenter.getCarList(id);

        return view;
    }

    @Override
    public void setDataRecyclerViewCar(List<Car> mList) {
        carList = mList;
        mAdapter = new CarRecyclerViewAdapter(carList, getContext());
        rvCar.setAdapter(mAdapter);
        mAdapter.setEventDeleteFavorite(new CarRecyclerViewAdapter.EventDeleteFavorite() {
            @Override
            public void onClick(int position) {

            }
        });
    }

}
