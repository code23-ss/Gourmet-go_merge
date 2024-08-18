package com.example.mainscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText idEditText;
    private Button findPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        idEditText = findViewById(R.id.idEditText);
        findPasswordButton = findViewById(R.id.findPasswordButton);

        findPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String id = idEditText.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || id.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your name, phone number, and ID.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Finding password...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}