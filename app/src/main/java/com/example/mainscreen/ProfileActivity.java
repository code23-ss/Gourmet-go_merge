package com.example.mainscreen;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView userIdTextView;
    private String currentUserId; // 현재 사용자 ID를 저장할 변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Firestore 인스턴스 초기화
        db = FirebaseFirestore.getInstance();

        // 현재 사용자 ID 설정 (로그인한 사용자 ID로 대체)
        currentUserId = getCurrentUserId(); // 이 함수는 실제로 사용자의 ID를 반환해야 합니다.

        // UI 요소 초기화
        userIdTextView = findViewById(R.id.user_id_label);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Firestore에서 사용자 ID를 가져와서 TextView에 설정
        fetchUserIdFromFirestore();

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

    private void fetchUserIdFromFirestore() {
        if (currentUserId != null) {
            db.collection("users").document(currentUserId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String userId = document.getString("ID"); // Firestore 문서에서 'ID' 필드 가져오기 (대문자)
                                userIdTextView.setText(userId);
                            } else {
                                Toast.makeText(ProfileActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to get document", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User ID is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUserId() {
        // 현재 로그인한 사용자의 ID를 반환하는 로직을 여기에 구현해야 합니다.
        // 예: FirebaseAuth.getInstance().getCurrentUser().getUid();
        // 테스트용으로 가상의 사용자 ID를 반환합니다.
        return FirebaseAuth.getInstance().getCurrentUser().getUid(); // 실제 사용자 ID로 대체
    }

    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}