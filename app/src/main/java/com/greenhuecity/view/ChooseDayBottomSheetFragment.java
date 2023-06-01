package com.greenhuecity.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.greenhuecity.DateTime;
import com.greenhuecity.R;
import com.greenhuecity.data.remote.OnBottomSheetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ChooseDayBottomSheetFragment extends BottomSheetDialogFragment {
    BottomSheetDialog mBottomSheetDialog;
    CalendarView calendarView;
    String currentDate = "";
    String selecDate = "";
    String isCheck, day;
    String choose_day;
    TextView tvOK;


    public OnBottomSheetListener mListener;


    public static ChooseDayBottomSheetFragment newInstance(String isCheck, String day) {
        ChooseDayBottomSheetFragment bottomSheetFragment = new ChooseDayBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isCheck", isCheck);
        bundle.putString("day", day);
        bottomSheetFragment.setArguments(bundle);
        return bottomSheetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            isCheck = bundleReceive.getString("isCheck");
            day = bundleReceive.getString("day");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnBottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnBottomSheetListener");
        }
    }
//
//    public static ChooseDayBottomSheetFragment newInstance() {
//        ChooseDayBottomSheetFragment bottomSheetFragment = new ChooseDayBottomSheetFragment();
//        return bottomSheetFragment;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottomsheet_calendar, null);

        calendarView = view.findViewById(R.id.calendarView);
        tvOK = view.findViewById(R.id.button_ok);
        mBottomSheetDialog.setContentView(view);
        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getDate();
        return mBottomSheetDialog;
    }

    private void getDate() {
        // Lấy ngày tháng năm hiện tại dưới dạng chuỗi
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = sdf.format(new Date());
        //Lấy ngày được chọn
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Định dạng ngày theo chuẩn dd/MM/yyyy
                selecDate = sdf.format(selectedDate.getTime());
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_day = currentDate;
                if (!selecDate.isEmpty()) {
                    choose_day = selecDate;
                }
                if (isCheck.equals("start")) {
                    try {
                        Date today = sdf.parse(currentDate);
                        // Chuyển đổi chuỗi ngày thành đối tượng Date
                        Date endDate = sdf.parse(day);
                        Date startDate = sdf.parse(choose_day);

                        // Tính toán khoảng cách giữa hai đối tượng Date
                        long diffInMillis = endDate.getTime() - startDate.getTime();
                        if (diffInMillis > 0 && startDate.getTime() >= today.getTime()) {
                            mListener.onResult(choose_day, isCheck);
                            mBottomSheetDialog.dismiss();
                        }
                        else
                            Toast.makeText(requireContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        // Xử lý ngoại lệ khi không thể chuyển đổi chuỗi ngày thành đối tượng Date
                        Log.d("AAA", "Error");
                    }
                } else {
                    try {
                        // Chuyển đổi chuỗi ngày thành đối tượng Date
                        Date startDate = sdf.parse(day);
                        Date endDate = sdf.parse(choose_day);

                        // Tính toán khoảng cách giữa hai đối tượng Date
                        long diffInMillis = endDate.getTime() - startDate.getTime();
                        if (diffInMillis > 0) {
                            mListener.onResult(choose_day, isCheck);
                            mBottomSheetDialog.dismiss();
                        }
                        else
                            Toast.makeText(requireContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        // Xử lý ngoại lệ khi không thể chuyển đổi chuỗi ngày thành đối tượng Date
                        Log.d("AAA", "Error");
                    }
                }


            }
        });

    }
}
