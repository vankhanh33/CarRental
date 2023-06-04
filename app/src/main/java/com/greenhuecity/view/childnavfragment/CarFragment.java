package com.greenhuecity.view.childnavfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.HomeContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.presenter.HomePresenter;
import com.greenhuecity.view.adapter.CarRecyclerViewAdapter;

import java.util.List;

public class CarFragment extends Fragment implements HomeContract.IView {
    RecyclerView rvCar;
    HomePresenter brandPresenter;


    CarRecyclerViewAdapter mAdapter;
    List<Cars> carList;
    String brands;


    public CarFragment(String brands) {
        this.brands = brands;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        rvCar = view.findViewById(R.id.recyclerView_car);
        rvCar.setHasFixedSize(true);

        rvCar.setNestedScrollingEnabled(false);

        rvCar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        brandPresenter = new HomePresenter(this);
        brandPresenter.getCarList(brands);

        return view;
    }

    @Override
    public void setDataRecyclerViewCar(List<Cars> mList) {
        carList = mList;
        mAdapter = new CarRecyclerViewAdapter(carList, getContext());
        rvCar.setAdapter(mAdapter);

        rvCar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    brandPresenter.stopAutoScroll();
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    brandPresenter.startAutoScroll(rvCar);
                return false;
            }
        });
        brandPresenter.startAutoScroll(rvCar);

    }

}
