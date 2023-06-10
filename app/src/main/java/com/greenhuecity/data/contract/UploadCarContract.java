package com.greenhuecity.data.contract;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.greenhuecity.data.model.Brands;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Powers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.http.Field;

public interface UploadCarContract {
    interface IView {
        void setDataDistributorList(List<Distributors> mList);
        void setDataBrandList(List<Brands> mList);

        void setDataPowerList(List<Powers> mList);
        void setDataStatusList(List<String> mList);
        void notifiError(String mess);
    }

    interface IPresenter {
        void getDataList();

        void uploadCar(String car_name,
                       double price,
                       String description,
                       String license_plates,
                       String status,
                       String from_time,
                       String end_time,
                       String approve,
                       int power_id,
                       int brand_id,
                       int user_id,
                       int distributor_id,
                       double top_speed,
                       double horse_power,
                       double mileage,
                       String image_data,
                       String random_photo);
        int getUsersId();
        String generateRandomString();
        void getDayTime(Calendar calendar,  TextView tv, Date startDate, Date endDate, int item);
    }
}
