package com.example.mainscreen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class MyReservationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservationList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reservation);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerView = findViewById(R.id.recycler_view_my_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationList = new ArrayList<>();
        reservationAdapter = new ReservationAdapter(reservationList);
        recyclerView.setAdapter(reservationAdapter);

        db = FirebaseFirestore.getInstance();

        loadReservationsFromFirestore();
    }

    private void loadReservationsFromFirestore() {
        db.collection("reservations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Reservation reservation = document.toObject(Reservation.class);
                                reservationList.add(reservation);
                            }
                            reservationAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w("MyReservationActivity", "Error getting documents.", task.getException());
                        Toast.makeText(this, "Failed to load reservations.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
