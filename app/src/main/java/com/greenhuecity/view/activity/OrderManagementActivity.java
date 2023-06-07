package com.greenhuecity.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.OrderManagementContract;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.presenter.OrderManagementPresenter;
import com.greenhuecity.view.adapter.ManagerCarRvAdapter;

import java.util.List;


public class OrderManagementActivity extends AppCompatActivity implements OrderManagementContract.IView {
    RecyclerView rvOrder;
    TextView tvNumberOfOrders;
    OrderManagementPresenter mPresenter;
    ManagerCarRvAdapter managerCarRvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        rvOrder = findViewById(R.id.recyclerView_car);
        tvNumberOfOrders = findViewById(R.id.textView_quantity);
        rvOrder.setHasFixedSize(true);
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPresenter = new OrderManagementPresenter(this);
        mPresenter.getOrderManagementList(mPresenter.getUsersId(this));

    }

    @Override
    public void setDataRecyclerViewOrderManagement(List<OrderManagement> mList) {
        managerCarRvAdapter = new ManagerCarRvAdapter(mList, this);
        rvOrder.setAdapter(managerCarRvAdapter);
        if (mList != null) tvNumberOfOrders.setText(String.valueOf(mList.size()));
        else tvNumberOfOrders.setText(String.valueOf(0));
        if (managerCarRvAdapter != null) {
            managerCarRvAdapter.setUpdateOrderIF(new ManagerCarRvAdapter.UpdateOrderIF() {
                @Override
                public void updateCofirm(int car_id, int order_id) {
                    mPresenter.updateStatusOrder(order_id, "Confirmed", car_id, "Has been hired");
                }

                @Override
                public void updateRefuse(int car_id, int order_id) {
                    mPresenter.updateStatusOrder(order_id, "Canceled", car_id, "Free time");
                }
            });
        }
    }

}
