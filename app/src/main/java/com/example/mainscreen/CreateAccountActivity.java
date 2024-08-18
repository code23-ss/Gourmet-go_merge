package com.example.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase를 사용하는 권한
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        auth = FirebaseAuth.getInstance(); // Firebase에서 인스턴스를 가져올 것이다!
        firestore = FirebaseFirestore.getInstance();

        Button registerButton = findViewById(R.id.buttonRegister); // 회원가입 버튼 객체 생성

        registerButton.setOnClickListener(v -> registerUser()); // 눌렀을 때 registerUser 함수를 쓸 것이다!
    }

    private void saveUserData(String name, String ID, String email, String password) { // firebase에 저장
        Map<String, String> user = new HashMap<>(); // 해시맵으로 username, email 필드에 저장
        user.put("name", name); // 사용자 이름 저장
        user.put("ID", ID);
        user.put("email", email);
        user.put("password", password);

        // 생성된 ID로 새 문서 추가
        firestore.collection("users") // 여기서! 컬렉션 이름과 같아야합니다
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("CreateAccountActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CreateAccountActivity", "문서 추가 오류", e);
                    }
                });
    }

    private void registerUser() { // xml에 있는 id의 이름을 가져와서 객체로 생성
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString(); // 이름 필드 가져오기
        String ID = ((EditText) findViewById(R.id.editTextUserID)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        auth.createUserWithEmailAndPassword(email, password) // firebase 권한으로 email, password를 만든다.
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // 회원가입 성공 시 Firestore에 사용자 세부 정보 저장
                        saveUserData(name, ID, email, password);
                        // 회원가입 성공 메시지 표시
                        Toast.makeText(CreateAccountActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        // 메인 액티비티로 이동
                        navigateToMainActivity();
                    } else {
                        // 회원가입 실패 시 사용자에게 메시지 표시
                        Toast.makeText(CreateAccountActivity.this, "회원가입 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(CreateAccountActivity.this, MainscreenActivity.class); // 로그인 화면으로 돌아가게 하는 intent 이용!
        startActivity(intent);
        finish();  // 현재 액티비티를 종료하여 뒤로가기 버튼으로 다시 돌아오지 않도록 한다.
    }
}