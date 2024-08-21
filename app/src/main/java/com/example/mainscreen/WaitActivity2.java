package com.example.mainscreen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class WaitActivity2 extends AppCompatActivity {

    private RadioGroup radioGroupPeople;
    private RadioGroup radioGroupDining;
    private EditText editTextCustomAdults;
    private Button buttonContinue;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait2);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize the UI elements
        radioGroupPeople = findViewById(R.id.radioGroupPeople);
        radioGroupDining = findViewById(R.id.radioGroupDining);
        editTextCustomAdults = findViewById(R.id.editTextCustomAdults);
        buttonContinue = findViewById(R.id.buttonContinue);

        // Add listener for radio buttons in People group
        radioGroupPeople.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check if the "Custom" option is selected
                if (checkedId == R.id.radioAdultsCustom) {
                    // Show the custom number of adults input field
                    editTextCustomAdults.setVisibility(View.VISIBLE);
                } else {
                    // Hide the custom number of adults input field
                    editTextCustomAdults.setVisibility(View.GONE);
                }
            }
        });

        // Set up the button click listener
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values
                int selectedPeopleId = radioGroupPeople.getCheckedRadioButtonId();
                RadioButton selectedPeopleButton = findViewById(selectedPeopleId);
                String numberOfPeople = selectedPeopleButton.getText().toString();

                // If custom option is selected, override number of people
                if (selectedPeopleId == R.id.radioAdultsCustom) {
                    String customPeople = editTextCustomAdults.getText().toString();
                    if (TextUtils.isEmpty(customPeople)) {
                        Toast.makeText(WaitActivity2.this, "Please enter the number of people", Toast.LENGTH_SHORT).show();
                        return; // Stop further execution if no input is provided
                    }
                    numberOfPeople = customPeople; // Override with custom value
                }

                int selectedDiningId = radioGroupDining.getCheckedRadioButtonId();
                RadioButton selectedDiningButton = findViewById(selectedDiningId);
                String diningOption = selectedDiningButton.getText().toString();

                // Save waiting data to Firestore
                saveWaitingData(numberOfPeople, diningOption);
            }
        });
    }

    private void saveWaitingData(String numberOfPeople, String diningOption) {
        // Create waiting data map
        Map<String, Object> waitingData = new HashMap<>();
        waitingData.put("numberOfPeople", numberOfPeople);
        waitingData.put("diningOption", diningOption);

        // Save to Firestore
        firestore.collection("waitings") // The name of the collection where waitings will be stored
                .add(waitingData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(WaitActivity2.this, "Waiting successfully added", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity and go back to the previous screen
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(WaitActivity2.this, "Error adding waiting", Toast.LENGTH_SHORT).show();
                });
    }
}
