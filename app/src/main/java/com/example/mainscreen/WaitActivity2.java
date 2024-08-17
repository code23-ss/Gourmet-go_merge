package com.example.mainscreen; // 여기에 패키지 이름을 맞게 수정하세요

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class WaitActivity2 extends AppCompatActivity {

    private RadioGroup radioGroupPeople;
    private RadioGroup radioGroupDining;
    private Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait2);

        // Initialize the UI elements
        radioGroupPeople = findViewById(R.id.radioGroupPeople);
        radioGroupDining = findViewById(R.id.radioGroupDining);
        buttonContinue = findViewById(R.id.buttonContinue);

        // Set up the button click listener
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values
                int selectedPeopleId = radioGroupPeople.getCheckedRadioButtonId();
                RadioButton selectedPeopleButton = findViewById(selectedPeopleId);
                String numberOfPeople = selectedPeopleButton.getText().toString();

                int selectedDiningId = radioGroupDining.getCheckedRadioButtonId();
                RadioButton selectedDiningButton = findViewById(selectedDiningId);
                String diningOption = selectedDiningButton.getText().toString();
            }
        });
    }
}