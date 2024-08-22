package com.example.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MyReservationActivity extends AppCompatActivity {

    private TextView noUpcomingReservationText;
    private Button bookRestaurantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reservation);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // TabLayout and ViewPager initialization
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Set up TabLayout and ViewPager (if necessary for future use)
        // viewPager.setAdapter(...); // Add your ViewPager Adapter here if needed
        tabLayout.setupWithViewPager(viewPager);

        // TextView and Button initialization
        noUpcomingReservationText = findViewById(R.id.noUpcomingReservationText);
        bookRestaurantButton = findViewById(R.id.bookRestaurantButton);

        // Show the TextView and Button since we don't have upcoming reservations
        noUpcomingReservationText.setVisibility(View.VISIBLE);
        bookRestaurantButton.setVisibility(View.VISIBLE);

        // Set up the button click listener to start BookActivity
        bookRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start BookActivity when the button is clicked
                Intent intent = new Intent(MyReservationActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });
    }
}
