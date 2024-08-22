package com.example.mainscreen ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private ListView listReviews;
    private Button btnWriteComments;
    private ArrayList<String> reviews;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        listReviews = findViewById(R.id.listReviews);
        btnWriteComments = findViewById(R.id.btnWriteComments);

        // Sample reviews to display
        reviews = new ArrayList<>();
        reviews.add("4.5 - Excellent service...");
        reviews.add("3.0 - Went for afternoon tea...");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviews);
        listReviews.setAdapter(adapter);

        btnWriteComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the activity to write a new comment
                Intent intent = new Intent(ReviewActivity.this, WriteCommentActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the new comment from the intent
            String newComment = data.getStringExtra("newComment");
            reviews.add(newComment);
            adapter.notifyDataSetChanged();
        }
    }
}
