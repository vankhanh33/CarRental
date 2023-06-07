package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.fragment.child.CarFragment;

public class ViewPagerHomeAdapter extends FragmentStateAdapter {
    public ViewPagerHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new CarFragment("");
            case 1: return  new CarFragment("Honda");
            case 2: return  new CarFragment("Yamaha");
            case 3: return new CarFragment("Ducati");
            case 4: return new CarFragment("BMW");
            case 5: return new CarFragment("Aprilia");
            default: return new CarFragment("");
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
