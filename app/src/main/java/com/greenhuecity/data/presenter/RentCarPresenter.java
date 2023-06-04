package com.greenhuecity.data.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.greenhuecity.data.contract.RentCarConstract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.RentCarActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentCarPresenter implements RentCarConstract.IPresenter {
    RentCarConstract.IView mView;
    RentCarActivity rentCarActivity;
    LocationManager locationManager;
    ApiService apiService;
    String status = "Chờ xác nhận";
    String payment = "Chưa thanh toán";

    public RentCarPresenter(RentCarConstract.IView mView, RentCarActivity rentCarActivity, LocationManager locationManager) {
        this.mView = mView;
        this.rentCarActivity = rentCarActivity;
        this.locationManager = locationManager;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getCar(Cars car) {
        mView.setCarInfo(car);
    }

    @Override
    public void onMapReady(GoogleMap googleMap, Cars car) {
        if (ActivityCompat.checkSelfPermission(rentCarActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            // Lấy thông tin vị trí hiện tại của người dùng
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                LatLng origin = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                LatLng destination = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                drawRoute(googleMap, origin, destination);
                //khoảng cách
                Location locationA = new Location("point A");
                locationA.setLatitude(origin.latitude);
                locationA.setLongitude(origin.longitude);

                Location locationB = new Location("point B");
                locationB.setLatitude(destination.latitude);
                locationB.setLongitude(destination.longitude);

                float distance = locationA.distanceTo(locationB) / 1000;
                String formattedDistance = String.format("%.2f km", distance);
                mView.showDistance(formattedDistance);
                //
                getAddressFromLatLng(destination);

            }

        } else {
            ActivityCompat.requestPermissions(rentCarActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void drawRoute(GoogleMap mMap, LatLng origin, LatLng destination) {
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

    @Override
    public void getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(rentCarActivity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String locality = address.getLocality();
                String country = address.getCountryName();
                String addressLine = address.getAddressLine(0);
                mView.setAddress(addressLine);
                // Use the address information as needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upRentCar(Cars car, String date_start, String date_end, int price) {
        apiService.upRentCar(car.getCar_id(), car.getDistributor_id(), date_start, date_end, price, status, payment).enqueue(new Callback<Cars>() {
            @Override
            public void onResponse(Call<Cars> call, Response<Cars> response) {

            }

            @Override
            public void onFailure(Call<Cars> call, Throwable t) {

            }
        });
    }


}
