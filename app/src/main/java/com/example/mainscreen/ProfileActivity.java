package com.example.mainscreen;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // reservation ImageView 클릭 리스너 설정
        ImageView reservationImageView = findViewById(R.id.reservation);
        reservationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MyReservationActivity로 이동
                Intent intent = new Intent(ProfileActivity.this, MyReservationActivity.class);
                startActivity(intent);
            }
        });
    }
    private long backKeyPressedTime=0;
    @Override
    public void onBackPressed(){
        super.onBackPressed();

        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime=System.currentTimeMillis();
            Toast.makeText(this,"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(System.currentTimeMillis()<=backKeyPressedTime+2000){
            finish();
        }
    }
}