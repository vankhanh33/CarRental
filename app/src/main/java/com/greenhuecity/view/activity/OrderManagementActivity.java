package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.OrderManagementContract;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.presenter.OrderManagementPresenter;
import com.greenhuecity.view.adapter.ManagementCarAdapter;

import java.util.List;


public class OrderManagementActivity extends AppCompatActivity implements OrderManagementContract.IView {
    RecyclerView rvOrder;
    TextView tvNumberOfOrders;
    OrderManagementPresenter mPresenter;
    ManagementCarAdapter managerCarRvAdapter;


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
        managerCarRvAdapter = new ManagementCarAdapter(mList, this);
        rvOrder.setAdapter(managerCarRvAdapter);
        if (mList != null) tvNumberOfOrders.setText(String.valueOf(mList.size()));
        else tvNumberOfOrders.setText(String.valueOf(0));
        if (managerCarRvAdapter != null) {
            managerCarRvAdapter.setUpdateOrderIF(new ManagementCarAdapter.UpdateOrderIF() {
                @Override
                public void updateCofirm(int car_id, int order_id) {
                    mPresenter.updateStatusOrder(order_id, "Đã xác nhận", car_id, "Xe đang được thuê");
                }

                @Override
                public void updateRefuse(int car_id, int order_id) {
                    mPresenter.updateStatusOrder(order_id, "Bị hủy từ nhà phân phối", car_id, "Đang rảnh");
                }
            });
        }
    }

}
