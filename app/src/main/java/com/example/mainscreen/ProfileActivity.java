package com.example.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

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
        reservationImageView.setOnClickListener(v -> {
            // MyReservationActivity로 이동
            Intent intent = new Intent(ProfileActivity.this, MyReservationActivity.class);
            startActivity(intent);
        });

        // TextView 클릭 리스너 설정
        TextView faqTextView = findViewById(R.id.ask);
        faqTextView.setOnClickListener(v -> {
            // AskActivity로 이동
            Intent intent = new Intent(ProfileActivity.this, AskActivity.class);
            startActivity(intent);
        });

        TextView termsServiceTextView = findViewById(R.id.terms_service);
        termsServiceTextView.setOnClickListener(v -> {
            // TermsOfServiceActivity로 이동
            Intent intent = new Intent(ProfileActivity.this, TermsOfServiceActivity.class);
            startActivity(intent);
        });

        TextView privacyPolicyTextView = findViewById(R.id.privacy_policy);
        privacyPolicyTextView.setOnClickListener(v -> {
            // PrivacyPolicyActivity로 이동
            Intent intent = new Intent(ProfileActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        // Delete Account TextView 클릭 리스너 설정
        TextView deleteAccountTextView = findViewById(R.id.delete_account);
        deleteAccountTextView.setOnClickListener(v -> {
            // DeleteAccountActivity로 이동
            Intent intent = new Intent(ProfileActivity.this, DeleteAccountActivity.class);
            startActivity(intent);
        });

        // Logout TextView 클릭 리스너 설정
        TextView logoutTextView = findViewById(R.id.logout);
        logoutTextView.setOnClickListener(v -> logoutUser());
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
        return FirebaseAuth.getInstance().getCurrentUser().getUid(); // 실제 사용자 ID로 대체
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut(); // Firebase에서 로그아웃 수행
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        // 로그인 화면으로 이동
        Intent intent = new Intent(ProfileActivity.this, MainscreenActivity.class);
        startActivity(intent);
        finish(); // 현재 액티비티 종료
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
