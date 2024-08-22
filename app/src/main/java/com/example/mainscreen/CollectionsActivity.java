package com.example.mainscreen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CollectionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CollectionAdapter collectionsAdapter;
    private List<Restaurant> restaurantList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collections);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerView = findViewById(R.id.recycler_view_collections);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        restaurantList = new ArrayList<>();
        collectionsAdapter = new CollectionAdapter(restaurantList);
        recyclerView.setAdapter(collectionsAdapter);

        db = FirebaseFirestore.getInstance();

        loadRestaurantsFromFirestore();
    }

    private void loadRestaurantsFromFirestore() {
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Restaurant restaurant = document.toObject(Restaurant.class);
                                if (restaurant != null) {
                                    restaurantList.add(restaurant);
                                }
                            }
                            collectionsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.w("CollectionsActivity", "Error getting documents.", task.getException());
                        Toast.makeText(this, "Failed to load restaurants.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
