package com.greenhuecity.data.presenter;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.gson.Gson;

import com.greenhuecity.data.contract.RentCarConstract;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.OrderItems;
import com.greenhuecity.data.model.Orders;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.RentCarActivity;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentCarPresenter implements RentCarConstract.IPresenter {
    RentCarConstract.IView mView;
    RentCarActivity rentCarActivity;
    LocationManager locationManager;
    ApiService apiService;
    Date dateStartTimeCar = null;
    Date dateEndTimeCar = null;
    boolean isCancelled = false;

    public RentCarPresenter(RentCarConstract.IView mView, RentCarActivity rentCarActivity, LocationManager locationManager) {
        this.mView = mView;
        this.rentCarActivity = rentCarActivity;
        this.locationManager = locationManager;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getCarDistributor(CarDistributor carDistributor) {
        mView.setOrderInfo(carDistributor);
    }

    @Override
    public void onMapReady(GoogleMap googleMap, CarDistributor carDistributor) {
        if (ActivityCompat.checkSelfPermission(rentCarActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                LatLng origin = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                LatLng destination = new LatLng(carDistributor.getDistributors().getLatitude(), carDistributor.getDistributors().getLongitude());

                //Hiển thị vị trí nhà phân phối trên map
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 13));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(destination)
                        .title(carDistributor.getDistributors().getName())
                        .snippet(carDistributor.getDistributors().getAddress()));

                // Hiển thị InfoWindow khi click vào Marker
                marker.showInfoWindow();
                //khoảng cách
                mView.setDistance(distanceToDistributor(origin, destination));
                //địa chỉ
                getAddressFromLatLng(destination);

            }

        } else {
            ActivityCompat.requestPermissions(rentCarActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
                mView.setAddress(locality + ", " + country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String distanceToDistributor(LatLng origin, LatLng destination) {
        //khoảng cách
        Location locationA = new Location("Customers");
        locationA.setLatitude(origin.latitude);
        locationA.setLongitude(origin.longitude);

        Location locationB = new Location("Destributors");
        locationB.setLatitude(destination.latitude);
        locationB.setLongitude(destination.longitude);

        float distance = locationA.distanceTo(locationB) / 1000;
        String formattedDistance = String.format("%.2f km", distance);
        return formattedDistance;
    }

    @Override
    public void getDayTime(Calendar calendar, CarDistributor carDistributor, TextView tv, Date startDate, Date endDate, int item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            dateStartTimeCar = sdf.parse(carDistributor.getCars().getFrom_time());
            dateEndTimeCar = sdf.parse(carDistributor.getCars().getEnd_time());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();
        DatePickerDialog datePickerDialog = new DatePickerDialog(rentCarActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                // Thiết lập giá trị cho calendar
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Thiết lập TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(rentCarActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        // Thiết lập giá trị cho calendar
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        Date chooseDate = calendar.getTime();

                        if (chooseDate.getTime() < currentDate.getTime()) {
                            mView.notifiErrorDate("Ngày giờ không hợp lệ");
                            return;
                        }
                        long dateStart = (endDate.getTime() - chooseDate.getTime()) / (24 * 60 * 60 * 1000);
                        if (item == 0) {
                            if (chooseDate.getTime() >= dateStartTimeCar.getTime() && chooseDate.getTime() < endDate.getTime() && dateStart >= 1 && dateStart <= 7) {
                                tv.setText(sdf.format(chooseDate));
                                mView.setTotalRent((int) dateStart);
                            } else
                                mView.notifiErrorDate("Ngày giờ thuê phải nhỏ hơn ngày trả 1 ngày, thuê không quá 1 tuần và phù hợp với ngày cho xe cho thuê");
                        } else if (item == 1) {
                            long dateEnd = (chooseDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                            if (chooseDate.getTime() <= dateEndTimeCar.getTime() && chooseDate.getTime() > startDate.getTime() && dateEnd >= 1 && dateEnd <= 7) {
                                tv.setText(sdf.format(chooseDate));
                                mView.setTotalRent((int) dateEnd);
                            } else
                                mView.notifiErrorDate("Ngày giờ trả phải lớn hơn ngày thuê 1 ngày, thuê không quá 1 tuần và phù hợp với ngày cho xe cho thuê");
                        }


                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void getCurrentDate(Calendar calendar) {
        mView.setCurrentDate(calendar);
    }

    @Override
    public String generateRandomString() {
        int length = 8;
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return sb.toString();
    }

    @Override
    public int getUsersId() {
        SharedPreferences preferences = rentCarActivity.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            return users.getId();
        }
        return 0;
    }

    @Override
    public void upOrders(String cod, String from_time, String end_time) {
        apiService.upOrders(cod, from_time, end_time, getUsersId()).enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }


    @Override
    public void upOrderItems(int car_id, int order_id, double price) {
        apiService.upOrderItems(car_id, order_id, price).enqueue(new Callback<OrderItems>() {
            @Override
            public void onResponse(Call<OrderItems> call, Response<OrderItems> response) {

            }

            @Override
            public void onFailure(Call<OrderItems> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadOrderProcessing(String from_time, String end_time, double price, int car_id) {
        String rndCode = generateRandomString();

        ProgressDialog progressDialog = new ProgressDialog(rentCarActivity);
        progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không cho phép hủy
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Huỷ công việc của bạn và ẩn ProgressDialog
                progressDialog.dismiss();
                isCancelled = true;
            }
        });
        // Hiển thị ProgressDialog
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isCancelled) {
                    upOrders(rndCode, from_time, end_time);
                    progressDialog.dismiss();
                    mView.successOrders("Quý khách vui lòng chờ chúng tôi xác nhận sau đó đến lấy xe!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            apiService.getOrders(rndCode).enqueue(new Callback<List<Orders>>() {
                                @Override
                                public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                                    List<Orders> ordersList = response.body();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            upOrderItems(car_id, ordersList.get(0).getId(), price);
                                        }
                                    }, 2000);
                                }
                                @Override
                                public void onFailure(Call<List<Orders>> call, Throwable t) {
                                }
                            });

                        }
                    }, 2000);
                }

            }
        }, 2000);
    }

}
