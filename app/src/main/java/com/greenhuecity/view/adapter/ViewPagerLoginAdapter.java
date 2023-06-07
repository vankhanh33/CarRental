package com.greenhuecity.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.greenhuecity.view.fragment.login.ForgotPasswordFragment;
import com.greenhuecity.view.fragment.login.LoginFragment;
import com.greenhuecity.view.fragment.login.RegisterFragment;

public class ViewPagerLoginAdapter extends FragmentStateAdapter {
    public ViewPagerLoginAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new LoginFragment();
            case 1: return new RegisterFragment();
            case 2: return new ForgotPasswordFragment();
            default: return new LoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
