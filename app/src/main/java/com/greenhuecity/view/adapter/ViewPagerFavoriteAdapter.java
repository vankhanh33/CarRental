package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.childnavfragment.CarFavoriteFragment;

public class ViewPagerFavoriteAdapter extends FragmentStateAdapter {
    public ViewPagerFavoriteAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new CarFavoriteFragment(0);
            case 1: return  new CarFavoriteFragment(3);
            case 2: return  new CarFavoriteFragment(1);
            case 3: return new CarFavoriteFragment(2);

            default: return new CarFavoriteFragment(0);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
