package com.greenhuecity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.greenhuecity.view.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental);
        initGUI();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               int id = item.getItemId();
                if (id == R.id.nav_home) {

                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.nav_favourite) {
                    viewPager2.setCurrentItem(1);
                    return true;
                } else if (id == R.id.nav_notify) {

                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.nav_setting) {
                    viewPager2.setCurrentItem(3);
                } else return false;

                return true;
            }
        });

    }

    private void initGUI() {
        viewPager2 = findViewById(R.id.viewpager_body);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager2.setUserInputEnabled(false);

    }

}