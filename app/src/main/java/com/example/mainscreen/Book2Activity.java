package com.example.mainscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Book2Activity extends AppCompatActivity {

    private RadioGroup radioGroupTitle, radioGroupAdults;
    private EditText editTextFirstName, editTextLastName, editTextMobileNumber, editTextEmail, editTextSpecialRequests;
    private CheckBox checkBoxPersonal;
    private CheckBox checkBoxUpdates;
    private Button buttonContinue;

    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book2);

        // Initialize views
        radioGroupTitle = findViewById(R.id.radioGroupTitle);
        radioGroupAdults = findViewById(R.id.radioGroupAdults);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSpecialRequests = findViewById(R.id.editTextSpecialRequests);
        checkBoxPersonal = findViewById(R.id.checkBoxPersonal);
        checkBoxUpdates = findViewById(R.id.checkBoxUpdates);
        buttonContinue = findViewById(R.id.buttonContinue);

        // Set default date and time
        Calendar now = Calendar.getInstance();
        selectedYear = now.get(Calendar.YEAR);
        selectedMonth = now.get(Calendar.MONTH);
        selectedDay = now.get(Calendar.DAY_OF_MONTH);
        selectedHour = now.get(Calendar.HOUR_OF_DAY);
        selectedMinute = now.get(Calendar.MINUTE);

        // DatePickerDialog initialization
        findViewById(R.id.datePickerButton).setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    (view, year, monthOfYear, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = monthOfYear;
                        selectedDay = dayOfMonth;
                    },
                    selectedYear,
                    selectedMonth,
                    selectedDay
            );
            // Set accent color
            datePickerDialog.setAccentColor(Color.parseColor("#E24443"));
            datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
        });

        // TimePickerDialog initialization
        findViewById(R.id.timePickerButton).setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                    (view, hourOfDay, minute, second) -> {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                    },
                    selectedHour,
                    selectedMinute,
                    true
            );
            // Set accent color
            timePickerDialog.setAccentColor(Color.parseColor("#E24443"));
            timePickerDialog.show(getSupportFragmentManager(), "TimePickerDialog");
        });

        // Handle button click
        buttonContinue.setOnClickListener(v -> {
            // Collect data from the form
            String title = ((RadioButton) findViewById(radioGroupTitle.getCheckedRadioButtonId())).getText().toString();
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String mobileNumber = editTextMobileNumber.getText().toString();
            String email = editTextEmail.getText().toString();
            String specialRequests = editTextSpecialRequests.getText().toString();
            boolean personal = checkBoxPersonal.isChecked();
            boolean updates = checkBoxUpdates.isChecked();
            int adults = Integer.parseInt(((RadioButton) findViewById(radioGroupAdults.getCheckedRadioButtonId())).getText().toString());

            // Display a confirmation message
            Toast.makeText(Book2Activity.this, "Reservation made for " + title + " " + firstName + " " + lastName +
                            "\nDate: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear +
                            "\nTime: " + selectedHour + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute) +
                            "\nAdults: " + adults +
                            "\nUpdates: " + (updates ? "Subscribed" : "Not Subscribed"),
                    Toast.LENGTH_LONG).show();
        });
    }
}