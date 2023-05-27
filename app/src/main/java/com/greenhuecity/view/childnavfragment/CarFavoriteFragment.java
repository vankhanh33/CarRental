package com.greenhuecity.view.childnavfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.CarFavoriteContract;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.presenter.CarFavoritePresenter;
import com.greenhuecity.view.adapter.CarRecyclerViewAdapter;

import java.util.List;

public class CarFavoriteFragment extends Fragment implements CarFavoriteContract.IView {
    RecyclerView rvCar;
    CarRecyclerViewAdapter mAdapter;

    int id;
    CarFavoritePresenter mPresenter;

    public CarFavoriteFragment(int id) {
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
        mPresenter = new CarFavoritePresenter(this);
        mPresenter.getCarList(id,getContext());
        return view;
    }
    @Override
    public void setDataRecyclerViewCar(List<Car> mList) {
        mAdapter = new CarRecyclerViewAdapter(mList, getContext());
        rvCar.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter.setEventDeleteFavorite(new CarRecyclerViewAdapter.EventDeleteFavorite() {
            @Override
            public void onClick(int position) {
                mList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
