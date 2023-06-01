package com.greenhuecity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTime extends AppCompatActivity  {
    CalendarView calendarView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet_calendar);
        calendarView = findViewById(R.id.calendarView);

        // Lấy ngày tháng năm hiện tại dưới dạng chuỗi
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

// Gán giá trị ngày tháng năm hiện tại cho TextView

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Lấy ngày được chọn
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Định dạng ngày theo chuẩn dd/MM/yyyy
                String formattedDate = sdf.format(selectedDate.getTime());
                Toast.makeText(DateTime.this, formattedDate, Toast.LENGTH_SHORT).show();


                try {
                    // Chuyển đổi chuỗi ngày thành đối tượng Date
                    Date endDate = sdf.parse(formattedDate);
                    Date startDate = sdf.parse(currentDate);

                    // Tính toán khoảng cách giữa hai đối tượng Date
                    long diffInMillis = endDate.getTime() - startDate.getTime();
                    int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));

                    // Lưu kết quả vào biến kq
                    int kq = diffInDays;
                    Toast.makeText(DateTime.this, kq +"", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    // Xử lý ngoại lệ khi không thể chuyển đổi chuỗi ngày thành đối tượng Date
                    Log.d("AAA","Error");
                }
            }
        });


        //


    }

}
