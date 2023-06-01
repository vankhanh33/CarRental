package com.greenhuecity.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.presenter.CarDetailPresenter;

import java.text.NumberFormat;
import java.util.Locale;


public class CarDetailActivity extends AppCompatActivity implements CarDetailContract.IView {
    ImageView img, img_heart;
    TextView tvName, tvMaxSpeed, tvHoursPower, tvMileage, tvDescription, tvPrice;
    LinearLayout btnBooking;
    CarDetailPresenter mPresenter;
    Car car = null;
    FavoriteCarDatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initGUI();

        db = new FavoriteCarDatabaseHelper(this);
        car = (Car) getIntent().getSerializableExtra("car");
        if (car != null) {
            mPresenter = new CarDetailPresenter(this,this);
            mPresenter.getDataDetailCar(car);
        }
        if (isCheckCarFavoriteDB(car)) img_heart.setImageResource(R.drawable.favorite);
        else img_heart.setImageResource(R.drawable.heart);
        img_heart.setOnClickListener(view -> mPresenter.updateDataFavorite(car, img_heart));
        Intent intent = new Intent(this, RentCarActivity.class);
        intent.putExtra("car",car);
        btnBooking.setOnClickListener(view-> startActivity(intent));
    }

    private void initGUI() {
        img = findViewById(R.id.img_detail_car);
        img_heart = findViewById(R.id.img_heart);
        tvName = findViewById(R.id.textView_nameCar);
        tvMaxSpeed = findViewById(R.id.textView_km_h);
        tvHoursPower = findViewById(R.id.textView_ps);
        tvMileage = findViewById(R.id.textView_km_l);
        tvDescription = findViewById(R.id.textView_description);
        tvPrice = findViewById(R.id.textView_priceDetail);
        btnBooking = findViewById(R.id.button_booking);
    }

    @Override
    public void setDataDetailCar(Car car) {
        Glide.with(this).load(car.getCar_img()).into(img);
        tvName.setText(car.getCar_name());
        tvMaxSpeed.setText(car.getMax_speed());
        tvHoursPower.setText(car.getHorsepower());
        tvMileage.setText(car.getMileage());
        tvDescription.setText(car.getCar_description());
        //
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String rent_price = currencyFormatter.format(car.getRental_price()) + "/day";
        tvPrice.setText(rent_price);

    }
    @Override
    public boolean isCheckCarFavoriteDB(Car car) {
        for (Car c : db.getAllCars())
            if (c.getCar_id() == car.getCar_id())
                return true;

        return false;
    }


}
