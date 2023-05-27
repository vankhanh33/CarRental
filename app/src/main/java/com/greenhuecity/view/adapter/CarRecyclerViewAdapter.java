package com.greenhuecity.view.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenhuecity.R;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.view.CarDetailActivity;
import com.greenhuecity.view.childnavfragment.CarFavoriteFragment;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.ViewHolder> {
    private List<Car> mList;
    private Context mContext;
    List<Car> favoriteCarList;
    public EventDeleteFavorite event;

    public interface EventDeleteFavorite {
         void onClick(int position);
    }

    public void setEventDeleteFavorite(EventDeleteFavorite event) {
        this.event = event;
    }

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
        int pst = position;
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
        //Lấy danh sách yêu thích thử shared
        SharedPreferences pref = mContext.getSharedPreferences("favorite", MODE_PRIVATE);
        Gson gson = new Gson();
        String carListJson = pref.getString("car_list", "");
        if (!carListJson.isEmpty()) {
            Type type = new TypeToken<List<Car>>() {
            }.getType();
            favoriteCarList = gson.fromJson(carListJson, type);
            for (Car c : favoriteCarList) {
                if (c.getCar_id() == car.getCar_id()) {
                    holder.imgFavourite.setImageResource(R.drawable.favorite);
                    break;
                }
            }
        }

        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("favorite", MODE_PRIVATE).edit();
                boolean isRemoved = false;
                Iterator<Car> iterator = favoriteCarList.iterator();
                while (iterator.hasNext()) {
                    Car c = iterator.next();
                    //Kiểm tra car đã tồn tại trong danh sách yêu thích hay chưa
                    if (c.getCar_id() == car.getCar_id()) {
                        //Xóa
                        iterator.remove();
                        isRemoved = true;
                        // xóa khi ở fragment favorite
                           event.onClick(pst);

                        break;
                    }
                }
                if (!isRemoved) {
                    //Thêm
                    favoriteCarList.add(car);
                    holder.imgFavourite.setImageResource(R.drawable.favorite);
                } else {
                    holder.imgFavourite.setImageResource(R.drawable.not_favorite);
                }
                String carListJson = gson.toJson(favoriteCarList);
                editor.putString("car_list", carListJson);
                editor.apply();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice;
        private ImageView imgFavourite, imgCar, imgBrand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavourite = itemView.findViewById(R.id.imageView_favourite);
            imgCar = itemView.findViewById(R.id.imageView_car);
            imgBrand = itemView.findViewById(R.id.imageView_brandCar);
            tvName = itemView.findViewById(R.id.textView_nameCar);
            tvPrice = itemView.findViewById(R.id.textView_priceCar);
        }
    }
}