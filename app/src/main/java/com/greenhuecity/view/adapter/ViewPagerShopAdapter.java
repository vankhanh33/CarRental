package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.childnavfragment.ShopChildFragment;

public class ViewPagerShopAdapter extends FragmentStateAdapter {
    public ViewPagerShopAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ShopChildFragment("");
            case 1: return  new ShopChildFragment("3");
            case 2: return  new ShopChildFragment("1");
            case 3: return new ShopChildFragment("2");
            default: return new ShopChildFragment("");
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
