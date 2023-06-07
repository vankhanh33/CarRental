package com.greenhuecity.view.fragment.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.SettingContract;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.presenter.SettingPresenter;
import com.greenhuecity.view.activity.LoginActivity;
import com.greenhuecity.view.activity.OrderManagementActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment implements SettingContract.IView {
    CircleImageView civProfile;
    TextView tvNameProfile, tvManager;
    View view;
    SettingPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initGUI();
        mPresenter = new SettingPresenter(this, getActivity());
        mPresenter.getDataShared();
        mPresenter.getDistributors();
        tvNameProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPresenter.isLogged())
                    startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        tvManager.setOnClickListener(view -> startActivity(new Intent(getActivity(), OrderManagementActivity.class)));
        return view;
    }

    private void initGUI() {
        civProfile = view.findViewById(R.id.profileCircleImageView);
        tvNameProfile = view.findViewById(R.id.usernameTextView);
        tvManager = view.findViewById(R.id.textView_manager);
    }

    @Override
    public void setDataSetting(String name, String url) {
        if (url != null) Glide.with(getActivity()).load(url).into(civProfile);
        if (name != null) tvNameProfile.setText(name);

    }

    @Override
    public void checkDistributors(List<Distributors> list) {
        try{
            for (Distributors distributors : list) {
                    if (distributors.getUser_id() == mPresenter.getUsersId()) {
                        tvManager.setVisibility(View.VISIBLE);
                        return;
                    } else tvManager.setVisibility(View.GONE);
                }
        }catch (NullPointerException e){

        }

    }
}
