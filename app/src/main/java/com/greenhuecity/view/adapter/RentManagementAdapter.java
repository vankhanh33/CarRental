package com.greenhuecity.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.view.activity.RentMnDetailActivity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RentManagementAdapter extends RecyclerView.Adapter<RentManagementAdapter.ViewHolder> {
    private List<RentManagement> mList;
    private Context mContext;



    public RentManagementAdapter(List<RentManagement> list, Context context) {
        mList = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rental_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        RentManagement rentManagement = mList.get(position);

        Glide.with(mContext).load(rentManagement.getCar_img()).into(holder.imgCar);
        if (rentManagement.getUser_photo() != null)
            Glide.with(mContext).load(rentManagement.getUser_photo()).into(holder.circleImageView);
        holder.tvNameCar.setText(rentManagement.getCar_name());
        holder.tvFromTime.setText(rentManagement.getFrom_time());
        holder.tvEndTime.setText(rentManagement.getEnd_time());
        holder.tvUserName.setText(rentManagement.getFullname());
        holder.tvPrice.setText(currencyFormatter.format(rentManagement.getPrice()) +  "/ngày");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RentMnDetailActivity.class);
                intent.putExtra("list", (Serializable) rentManagement);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCar, tvPrice, tvFromTime, tvEndTime, tvUserName;
        private ImageView imgCar;
        private CircleImageView circleImageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCar = itemView.findViewById(R.id.imageView_car);
            tvNameCar = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
            circleImageView = itemView.findViewById(R.id.profileCircleImageView);
            tvUserName = itemView.findViewById(R.id.usernameTextView);
            tvFromTime = itemView.findViewById(R.id.textView_fromTime);
            tvEndTime = itemView.findViewById(R.id.textView_endTime);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}