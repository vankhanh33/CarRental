package com.greenhuecity.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.presenter.CarDetailPresenter;


public class CarDetailActivity extends AppCompatActivity implements CarDetailContract.IView, OnMapReadyCallback{
    ImageView img;
    TextView tvName, tvMaxSpeed, tvHoursPower, tvMileage, tvDescription;
    CarDetailPresenter mPresenter;
    Car car = null;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initGUI();
        car = (Car) getIntent().getSerializableExtra("car");
        if (car != null) {
            mPresenter = new CarDetailPresenter(this);
            mPresenter.getDataDetailCar(car);
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       supportMapFragment.getMapAsync(this);
        MapView mapView =  findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

    }

    private void initGUI() {
        img = findViewById(R.id.img_detail_car);
        tvName = findViewById(R.id.textView_nameCar);
        tvMaxSpeed = findViewById(R.id.textView_km_h);
        tvHoursPower = findViewById(R.id.textView_ps);
        tvMileage = findViewById(R.id.textView_km_l);
        tvDescription = findViewById(R.id.textView_description);
    }

    @Override
    public void setDataDetailCar(Car car) {
        Glide.with(this).load(car.getCar_img()).into(img);
        tvName.setText(car.getCar_name());
        tvMaxSpeed.setText(car.getMax_speed());
        tvHoursPower.setText(car.getHorsepower());
        tvMileage.setText(car.getMileage());
        tvDescription.setText(car.getCar_description());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Do something with the map here
        // For example: Add marker to the map
        LatLng sydney = new LatLng(-33.852, 151.211);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(sydney)
                .title("Sydney")
                .snippet("The most populous city in Australia.");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
    }
}
