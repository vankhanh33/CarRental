package com.greenhuecity.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    CarDetailPresenter mPresenter;
    Car car = null;
    FavoriteCarDatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initGUI();

        db = new FavoriteCarDatabaseHelper(this);
//        for (int i = 0; i< db.getAllCars().size();i++) db.deleteCar(db.getAllCars().get(i).getCar_id());
        car = (Car) getIntent().getSerializableExtra("car");
        if (car != null) {
            mPresenter = new CarDetailPresenter(this,this);
            mPresenter.getDataDetailCar(car);
        }
        if (isCheckCarFavoriteDB(car)) img_heart.setImageResource(R.drawable.favorite);
        else img_heart.setImageResource(R.drawable.heart);
        img_heart.setOnClickListener(view -> mPresenter.updateDataFavorite(car, img_heart));
        if (db.getAllCars().size() != 0)
            Toast.makeText(this, db.getAllCars().size() + "a", Toast.LENGTH_SHORT).show();
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
