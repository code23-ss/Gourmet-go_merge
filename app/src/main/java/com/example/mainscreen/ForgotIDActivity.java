package com.example.mainscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotIDActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private Button findIDButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_idactivity);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        findIDButton = findViewById(R.id.findIDButton);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        findIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ForgotIDActivity.this, "Please enter your name and email.", Toast.LENGTH_SHORT).show();
                } else {
                    findUserId(name, email);
                }
            }
        });
    }

    private void findUserId(String name, String email) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean userFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dbName = snapshot.child("name").getValue(String.class);
                    String dbEmail = snapshot.child("email").getValue(String.class);
                    String userId = snapshot.child("id").getValue(String.class);

                    if (name.equals(dbName) && email.equals(dbEmail)) {
                        Toast.makeText(ForgotIDActivity.this, "Your ID is: " + userId, Toast.LENGTH_LONG).show();
                        userFound = true;
                        break;
                    }
                }
                if (!userFound) {
                    Toast.makeText(ForgotIDActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ForgotIDActivity.this, "Error fetching data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}