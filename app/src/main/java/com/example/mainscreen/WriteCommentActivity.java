package com.example.mainscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    private FirebaseFirestore firestore;
    private static final int REQUEST_CODE_PICK_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_comment);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

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

        // Date Spinner initialization
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

        // Purpose Spinner initialization
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

        // Spinner selection handling
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
                // No action needed
            }
        });

        // Photo upload click listener
        FrameLayout photoUploadContainer = findViewById(R.id.photoUploadContainer);
        photoUploadContainer.setOnClickListener(v -> {
            // Open gallery to pick a photo
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
        });

        // Continue button click listener
        continueButton.setOnClickListener(v -> {
            if (certificationCheckbox.isChecked()) {
                saveReviewData();
            } else {
                Toast.makeText(WriteCommentActivity.this, "You must certify before continuing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            // Display selected image
            photoIcon.setImageURI(selectedImage);
        }
    }

    private void saveReviewData() {
        // Collect form data
        float rating = ratingBar.getRating();
        String date = spinnerDate.getSelectedItem().toString();
        String purpose = spinnerPurpose.getSelectedItem().toString();
        String otherPurpose = editTextOtherPurpose.getText().toString();
        String reviewText = editReview.getText().toString();
        String title = editTitle.getText().toString();

        // If "Other" is selected, include the other purpose
        if ("Other".equals(purpose) && !otherPurpose.isEmpty()) {
            purpose = otherPurpose;
        }

        // Create review data map
        Map<String, Object> review = new HashMap<>();
        review.put("rating", rating);
        review.put("date", date);
        review.put("purpose", purpose);
        review.put("reviewText", reviewText);
        review.put("title", title);
        review.put("certified", certificationCheckbox.isChecked());

        // Save review data to Firestore
        firestore.collection("reviews") // The name of the collection where reviews will be stored
                .add(review)
                .addOnSuccessListener(documentReference -> {
                    Log.d("WriteCommentActivity", "Review added with ID: " + documentReference.getId());
                    Toast.makeText(WriteCommentActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the form or navigate to another activity
                    finish(); // Close the activity and go back to the previous screen
                })
                .addOnFailureListener(e -> {
                    Log.e("WriteCommentActivity", "Error adding review", e);
                    Toast.makeText(WriteCommentActivity.this, "Error submitting review", Toast.LENGTH_SHORT).show();
                });
    }
}
