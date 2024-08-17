package com.example.mainscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WriteCommentActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Spinner spinnerDate;
    private RadioGroup radioGroup;
    private Spinner spinnerPurpose;
    private EditText editTextOtherPurpose;
    private EditText editReview;
    private EditText editTitle;
    private ImageView photoIcon;
    private CheckBox certificationCheckbox;
    private Button continueButton;

    private static final int REQUEST_CODE_PICK_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        // Find views
        ratingBar = findViewById(R.id.ratingBar);
        spinnerDate = findViewById(R.id.spinnerDate);
        radioGroup = findViewById(R.id.radioGroup);
        spinnerPurpose = findViewById(R.id.spinnerPurpose);
        editTextOtherPurpose = findViewById(R.id.editTextOtherPurpose);
        editReview = findViewById(R.id.editReview);
        editTitle = findViewById(R.id.editTitle);
        photoIcon = findViewById(R.id.photoIcon);
        certificationCheckbox = findViewById(R.id.certificationCheckbox);
        continueButton = findViewById(R.id.continueButton);

        // Date Spinner 초기화
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        for (int i = 0; i < 12; i++) {
            calendar.set(currentYear, currentMonth - i, 1);
            months.add(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, getResources().getConfiguration().locale)
                    + " " + calendar.get(Calendar.YEAR));
        }
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dateAdapter);

        // Purpose Spinner 초기화
        String[] purposes = {
                "Breakfast",
                "Brunch",
                "Lunch",
                "Dinner",
                "Dessert",
                "Coffee or tea",
                "Snacks",
                "Drinks",
                "Late night food",
                "Other"
        };

        ArrayAdapter<String> purposeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, purposes);
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurpose.setAdapter(purposeAdapter);

        // Spinner 선택 시 동작 처리
        spinnerPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                if ("Other".equals(selectedOption)) {
                    editTextOtherPurpose.setVisibility(View.VISIBLE);
                } else {
                    editTextOtherPurpose.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 동작 없음
            }
        });

        // Photo upload click listener
        FrameLayout photoUploadContainer = findViewById(R.id.photoUploadContainer);
        photoUploadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to pick a photo
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
            }
        });

        // Continue button click listener
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (certificationCheckbox.isChecked()) {
                    // Proceed with the form submission
                    Toast.makeText(WriteCommentActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Show error message
                    Toast.makeText(WriteCommentActivity.this, "You must certify before continuing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            // Display selected image (This can be enhanced with more functionality)
            photoIcon.setImageURI(selectedImage);
        }
    }
}