package com.example.mainscreen;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainscreen.MainscreenActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText nameEditText, idEditText;
    Button findPwBtn, cancelBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nameEditText = findViewById(R.id.findPw_nameEditText);
        idEditText = findViewById(R.id.findPw_IdEditText);
        findPwBtn = findViewById(R.id.findPwBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        findPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameEditText.getText().toString().trim();
                final String email = idEditText.getText().toString().trim();

                // 이름과 이메일이 입력되었는지 확인
                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter both name and email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase에서 비밀번호 재설정 이메일 보내기
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                // 비밀번호 재설정 이메일을 보냈으므로 메인 액티비티로 이동
                                Intent intent = new Intent(ForgotPasswordActivity.this, MainscreenActivity.class);
                                startActivity(intent);
                            } else {
                                // Firebase의 예외 처리
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "The email address does not exist.", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthRecentLoginRequiredException e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Recent login required.", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "User collision occurred.", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 취소 버튼 클릭 시 메인 액티비티로 이동
                Intent intent = new Intent(ForgotPasswordActivity.this, MainscreenActivity.class);
                startActivity(intent);
            }
        });
    }
}