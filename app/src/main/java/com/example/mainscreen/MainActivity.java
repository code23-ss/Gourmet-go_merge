package com.example.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set default selection to "Seoul"
        spinner.setSelection(adapter.getPosition("Seoul"));

        // 검색 이미지 클릭시 검색 창으로 이동
        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });

        // 프로필 이미지 클릭시 프로필 창으로 이동
        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        // 홈 이미지 클릭시 메인 창으로 이동
        ImageView home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "You are already on the home screen", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String city = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + city, Toast.LENGTH_LONG).show();

        Intent intent = null;
        switch (city) {
            case "Seoul":
                return;
            case "Jeju":
                intent = new Intent(this, JejuActivity.class);
                break;
            case "Busan":
                intent = new Intent(this, BusanActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "No city selected", Toast.LENGTH_SHORT).show();
    }
}