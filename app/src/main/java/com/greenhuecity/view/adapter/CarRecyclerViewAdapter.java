package com.greenhuecity.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.view.CarDetailActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.ViewHolder> {
    private List<Car> mList;
    private Context mContext;
    List<Car> favoriteCarList;

    public CarRecyclerViewAdapter(List<Car> list, Context context) {
        mList = list;
        mContext = context;
        favoriteCarList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // vị trí xóa ở fragment favorite
        Car car = mList.get(position);

        Glide.with(mContext).load(car.getCar_img()).into(holder.imgCar);
        holder.tvName.setText(car.getCar_name());

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.tvPrice.setText(currencyFormatter.format(car.getRental_price()) + "/ ngày");
        Glide.with(mContext).load(car.getCategory_img()).into(holder.imgBrand);
        holder.imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarDetailActivity.class);
                 intent.putExtra("car",car);
                 mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice;
        private ImageView imgCar, imgBrand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCar = itemView.findViewById(R.id.imageView_car);
            imgBrand = itemView.findViewById(R.id.imageView_brandCar);
            tvName = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
        }
    }
}