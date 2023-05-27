package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.childnavfragment.HomeChildFragment;
import com.greenhuecity.view.childnavfragment.ShopChildFragment;

public class ViewPagerHomeAdapter extends FragmentStateAdapter {
    public ViewPagerHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeChildFragment("");
            case 1: return  new HomeChildFragment("3");
            case 2: return  new HomeChildFragment("1");
            case 3: return new HomeChildFragment("2");
            default: return new HomeChildFragment("");
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
