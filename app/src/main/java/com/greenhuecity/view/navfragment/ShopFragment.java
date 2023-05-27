package com.greenhuecity.view.navfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.greenhuecity.R;
import com.greenhuecity.view.adapter.ViewPagerShopAdapter;

public class ShopFragment extends Fragment  {
    TabLayout tabLayout;
    ViewPager2 viewPager2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop,container,false);
        tabLayout = view.findViewById(R.id.tablayout_brand);
        viewPager2 = view.findViewById(R.id.viewpager2_car);

        tabLayout.setSaveEnabled(false);
        viewPager2.setAdapter(new ViewPagerShopAdapter(this.getActivity()));
        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> {
            switch (position){
                case 0: tab.setText("Nổi bật");break;
                case 1: tab.setText("PKL");break;
                case 2: tab.setText("Honda");break;
                case 3: tab.setText("Yamaha");break;

            }
        }).attach();


        return view;
    }

}
