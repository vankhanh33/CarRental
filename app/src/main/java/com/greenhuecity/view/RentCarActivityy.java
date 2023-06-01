package com.greenhuecity.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.DirectionsApi;

import com.google.maps.GeoApiContext;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.greenhuecity.R;
import com.greenhuecity.data.model.Car;
import com.greenhuecity.data.remote.OnBottomSheetListener;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RentCarActivityy extends AppCompatActivity implements OnMapReadyCallback, OnBottomSheetListener {
    TextView tvName, tvBrand, tvDeparture_day, tvReturn_day, tvDistance, tvLocation, tvMomo;
    LinearLayout layoutPay;
    private GoogleMap mMap;
    private LocationManager locationManager;

    Car car;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initGUI();
        car = (Car) getIntent().getSerializableExtra("car");
        tvName.setText(car.getCar_name());
        tvBrand.setText(car.getCategory_name());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        // Khởi tạo LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapFragment.getMapAsync(this);
        setDataTvRental();


    }

    private void initGUI() {
        tvName = findViewById(R.id.textView_Name);
        tvBrand = findViewById(R.id.textView_brand);
        tvDeparture_day = findViewById(R.id.textView_start);
        tvReturn_day = findViewById(R.id.textView_end);
        tvLocation = findViewById(R.id.textView_pickup_location);
        tvMomo = findViewById(R.id.textView_momo);
        layoutPay = findViewById(R.id.button_paynow);
        tvDistance = findViewById(R.id.textView_distance);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            // Lấy thông tin vị trí hiện tại của người dùng
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                LatLng origin = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                LatLng destination = new LatLng(car.getLatitude(), car.getLongitude());
                drawRoute(origin, destination);
                //khoảng cách
                Location locationA = new Location("point A");
                locationA.setLatitude(origin.latitude);
                locationA.setLongitude(origin.longitude);

                Location locationB = new Location("point B");
                locationB.setLatitude(destination.latitude);
                locationB.setLongitude(destination.longitude);

                float distance = locationA.distanceTo(locationB) / 1000;
                String formattedDistance = String.format("%.2f km", distance);
                tvDistance.setText(formattedDistance);
                //
                getAddressFromLatLng(destination);

            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


    }

    private void drawRoute(LatLng origin, LatLng destination) {
        mMap.clear();

        // add marker at origin and destination
        mMap.addMarker(new MarkerOptions().position(origin).title("Origin"));
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));

        // move camera to the origin
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 12));

        // build GeoApiContext
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDKhV86CCjNXNLM83nU_BlsGJTq3PsSdVk")
                .build();

        // request direction
        try {
            DirectionsResult result = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                    .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                    .await();

            if (result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];

                // add polylines to map
                List<LatLng> path = new ArrayList<>();
                for (com.google.maps.model.LatLng point : route.overviewPolyline.decodePath()) {
                    path.add(new LatLng(point.lat, point.lng));
                }
                mMap.addPolyline(new PolylineOptions().addAll(path).width(50).color(Color.RED));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    private void getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String locality = address.getLocality();
                String country = address.getCountryName();
                String addressLine = address.getAddressLine(0);
                tvLocation.setText(addressLine);
                // Use the address information as needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setDataTvRental(){
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
                ChooseDayBottomSheetFragment bottomSheetFragment = ChooseDayBottomSheetFragment.newInstance("start",tvReturn_day.getText().toString());
                bottomSheetFragment.mListener = (OnBottomSheetListener) RentCarActivityy.this;
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());

            }
        });
        tvReturn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDayBottomSheetFragment bottomSheetFragment = ChooseDayBottomSheetFragment.newInstance("end",tvDeparture_day.getText().toString());
                bottomSheetFragment.mListener = (OnBottomSheetListener) RentCarActivityy.this;
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());

            }
        });

        //



    }


    @Override
    public void onResult(String result, String isCheck) {
        if(result != null){
            if(isCheck.equals("start")){
                tvDeparture_day.setText(result);
            }else if(isCheck.equals("end")) tvReturn_day.setText(result);
        }
    }
}
