package com.example.mainscreen;

import android.util.Log;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Book2Activity extends AppCompatActivity {

    private RadioGroup radioGroupTitle, radioGrouppeople;
    private EditText editTextFirstName, editTextLastName, editTextMobileNumber, editTextEmail, editTextSpecialRequests;
    private CheckBox checkBoxPersonal;
    private CheckBox checkBoxUpdates;
    private Button buttonContinue;

    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book2);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        radioGroupTitle = findViewById(R.id.radioGroupTitle);
        radioGrouppeople = findViewById(R.id.radioGroupPeople);
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
            int people = Integer.parseInt(((RadioButton) findViewById(radioGrouppeople.getCheckedRadioButtonId())).getText().toString());

            // Save reservation data to Firestore
            saveReservationData(title, firstName, lastName, mobileNumber, email, specialRequests, personal, updates, people);
        });
    }

    private void saveReservationData(String title, String firstName, String lastName, String mobileNumber, String email,
                                     String specialRequests, boolean personal, boolean updates, int people) {
        // Create reservation data map
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("title", title);
        reservation.put("firstName", firstName);
        reservation.put("lastName", lastName);
        reservation.put("mobileNumber", mobileNumber);
        reservation.put("email", email);
        reservation.put("specialRequests", specialRequests);
        reservation.put("personal", personal);
        reservation.put("updates", updates);
        reservation.put("people", people);
        reservation.put("date", String.format("%d/%d/%d", selectedDay, selectedMonth + 1, selectedYear));
        reservation.put("time", String.format("%d:%02d", selectedHour, selectedMinute));

        // Save to Firestore
        firestore.collection("reservations") // The name of the collection where reservations will be stored
                .add(reservation)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Book2Activity", "Reservation added with ID: " + documentReference.getId());
                    Toast.makeText(Book2Activity.this, "Reservation successfully made", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the form or navigate to another activity
                    finish(); // Close the activity and go back to the previous screen
                })
                .addOnFailureListener(e -> {
                    Log.e("Book2Activity", "Error adding reservation", e);
                    Toast.makeText(Book2Activity.this, "Error making reservation", Toast.LENGTH_SHORT).show();
                });
    }
}
