package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.adapter.CarRecyclerViewAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvSearch;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvSearch = findViewById(R.id.recyclerView_car);
        tvResult = findViewById(R.id.text_result);
        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new GridLayoutManager(this,3));
        List<Cars> carsList = (List<Cars>)getIntent().getSerializableExtra("list");
        if(carsList != null){
            CarRecyclerViewAdapter carRecyclerViewAdapter = new CarRecyclerViewAdapter(carsList,this);
            rvSearch.setAdapter(carRecyclerViewAdapter);
        }
        tvResult.setText(String.valueOf(carsList.size()));
    }
}
