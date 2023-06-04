package com.greenhuecity.view;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.greenhuecity.MainActivity;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.RentCarConstract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.presenter.RentCarPresenter;
import com.greenhuecity.data.remote.OnBottomSheetListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RentCarActivity extends AppCompatActivity implements OnMapReadyCallback, OnBottomSheetListener, RentCarConstract.IView {
    TextView tvName, tvBrand, tvDeparture_day, tvReturn_day, tvDistance, tvLocation, tvMomo;
    LinearLayout layoutPay;
    private GoogleMap mMap;
    private LocationManager locationManager;

    Cars car;
    RentCarPresenter mPresenter;
    String start_date,end_date;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initGUI();
        car = (Cars) getIntent().getSerializableExtra("car");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Khởi tạo LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mPresenter = new RentCarPresenter(this, RentCarActivity.this, locationManager);
        mPresenter.getCar(car);
        mapFragment.getMapAsync(this);
        setDataTvRental();
        listener();

    }

    public void initGUI() {
        tvName = findViewById(R.id.textView_Name);
        tvBrand = findViewById(R.id.textView_brand);
        tvDeparture_day = findViewById(R.id.textView_start);
        tvReturn_day = findViewById(R.id.textView_end);
        tvLocation = findViewById(R.id.textView_pickup_location);
        tvMomo = findViewById(R.id.textView_momo);
        layoutPay = findViewById(R.id.button_paynow);
        tvDistance = findViewById(R.id.textView_distance);
    }
    private  void listener(){
        layoutPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_date = tvDeparture_day.getText().toString();
                end_date = tvReturn_day.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    // Chuyển đổi chuỗi ngày thành đối tượng Date
                    Date startDate = sdf.parse(start_date);
                    Date endDate = sdf.parse(end_date);

                    // Tính toán khoảng cách giữa hai đối tượng Date
                    long diffInMillis = endDate.getTime() - startDate.getTime();
                    int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));
                    mPresenter.upRentCar(car,start_date,end_date,(int) car.getPrice() * diffInDays);
                    startActivity(new Intent(RentCarActivity.this, MainActivity.class));
                } catch (ParseException e) {
                    // Xử lý ngoại lệ khi không thể chuyển đổi chuỗi ngày thành đối tượng Date
                    Log.d("AAA", "Error");
                }
            }
        });
    }
    @Override
    public void setDataTvRental() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(date);
        tvDeparture_day.setText(sdf.format(new Date()));
        tvReturn_day.setText(formattedDate);
        tvDeparture_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDayBottomSheetFragment bottomSheetFragment = ChooseDayBottomSheetFragment.newInstance("start", tvReturn_day.getText().toString());
                bottomSheetFragment.mListener = (OnBottomSheetListener) RentCarActivity.this;
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });
        tvReturn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDayBottomSheetFragment bottomSheetFragment = ChooseDayBottomSheetFragment.newInstance("end", tvDeparture_day.getText().toString());
                bottomSheetFragment.mListener = (OnBottomSheetListener) RentCarActivity.this;
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mPresenter.onMapReady(googleMap, car);
    }



    @Override
    public void setCarInfo(Cars car) {
        tvName.setText(car.getCar_name());
        tvBrand.setText(car.getBrand_id());
    }

    @Override
    public void showDistance(String formattedDistance) {
        tvDistance.setText(formattedDistance);
    }

    @Override
    public void setAddress(String addressLine) {
        tvLocation.setText(addressLine);
    }


    @Override
    public void onResult(String result, String isCheck) {
        if (result != null) {
            if (isCheck.equals("start")) {
                tvDeparture_day.setText(result);
            } else if (isCheck.equals("end")) tvReturn_day.setText(result);
        }
    }
}
