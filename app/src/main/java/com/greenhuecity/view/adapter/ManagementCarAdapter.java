package com.greenhuecity.view.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.OrderManagement;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManagementCarAdapter extends RecyclerView.Adapter<ManagementCarAdapter.ViewHolder> {
    private List<OrderManagement> mList;
    private Context mContext;

    public interface UpdateOrderIF {
        void updateCofirm(int car_id, int order_id);

        void updateRefuse(int car_id, int order_id);
    }

    UpdateOrderIF orderIF;

    public void setUpdateOrderIF(UpdateOrderIF orderIF) {
        this.orderIF = orderIF;
    }


    public ManagementCarAdapter(List<OrderManagement> list, Context context) {
        mList = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_rent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        OrderManagement orderManagement = mList.get(position);

        Glide.with(mContext).load(orderManagement.getCar_photo()).into(holder.imgCar);
        if (orderManagement.getPhoto() != null)
            Glide.with(mContext).load(orderManagement.getPhoto()).into(holder.circleImageView);
        holder.tvNameCar.setText(orderManagement.getCar_name());
        holder.tvCode.setText(orderManagement.getCode());
        holder.tvFromTime.setText(orderManagement.getFrom_time());
        holder.tvEndTime.setText(orderManagement.getEnd_time());
        holder.tvPlates.setText(orderManagement.getLicense_plates());
        holder.tvUserName.setText(orderManagement.getFullname());
        holder.tvStatus.setText(orderManagement.getStatus());
        holder.tvPrice.setText(currencyFormatter.format(orderManagement.getRental_costs()));

        int i = position;
        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
                progressDialog.setCancelable(false); // Không cho phép hủy
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
                // Hiển thị ProgressDialog
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderIF.updateCofirm(orderManagement.getCar_id(), orderManagement.getOrder_id());
                        mList.remove(i);
                        notifyDataSetChanged();
                    }
                }, 2999);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2999);
            }
        });
        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
                progressDialog.setCancelable(false); // Không cho phép hủy
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
                // Hiển thị ProgressDialog
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderIF.updateRefuse(orderManagement.getCar_id(), orderManagement.getOrder_id());
                        mList.remove(i);
                        notifyDataSetChanged();
                    }
                }, 2999);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2999);
            }

        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCar, tvPrice, tvCode, tvFromTime, tvEndTime, tvPlates, tvUserName, tvStatus, tvConfirm, tvRefuse;
        private ImageView imgCar;
        private CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            tvNameCar = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            circleImageView = itemView.findViewById(R.id.profileCircleImageView);
            tvUserName = itemView.findViewById(R.id.usernameTextView);
            tvCode = itemView.findViewById(R.id.textView_code);
            tvFromTime = itemView.findViewById(R.id.textView_fromTime);
            tvEndTime = itemView.findViewById(R.id.textView_endTime);
            tvPlates = itemView.findViewById(R.id.textView_license_plates);
            tvStatus = itemView.findViewById(R.id.textView_status);
            tvConfirm = itemView.findViewById(R.id.textView_confirm);
            tvRefuse = itemView.findViewById(R.id.textView_refuse);
        }
    }
}