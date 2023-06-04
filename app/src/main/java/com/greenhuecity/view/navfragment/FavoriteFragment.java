package com.greenhuecity.view.navfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.presenter.FavoritePresenter;
import com.greenhuecity.view.adapter.CarRecyclerViewAdapter;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.IView {
    TextView tvEmpty;
    RecyclerView rvCar;
    FavoritePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        tvEmpty = view.findViewById(R.id.textView_favoriteNull);
        rvCar  = view.findViewById(R.id.recyclerView_car);
        rvCar.setHasFixedSize(true);
        rvCar.setLayoutManager(new GridLayoutManager(requireContext(),2));
        mPresenter = new FavoritePresenter(this,requireContext());
        mPresenter.getCarList(requireContext());
        return view;
    }

    @Override
    public void setDataRecyclerViewCar(List<Cars> mList) {
        CarRecyclerViewAdapter adapter = new CarRecyclerViewAdapter(mList,requireContext());
        rvCar.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setDataEmpty(String mess) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(mess);
    }

    @Override
    public void setDataExist() {
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCarList(requireContext());
    }
}
