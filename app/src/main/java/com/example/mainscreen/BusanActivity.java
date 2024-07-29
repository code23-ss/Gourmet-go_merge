package com.example.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BusanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Button lastSelectedButton = null;
    private HorizontalScrollView koreanScrollView;
    private HorizontalScrollView chineseScrollView;
    private HorizontalScrollView italianScrollView;
    private HorizontalScrollView japaneseScrollView;
    private HorizontalScrollView fusionScrollView;
    private HorizontalScrollView asianScrollView;
    private HorizontalScrollView viewmoreScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(adapter.getPosition("Busan"));

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        ImageView home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Toast.makeText(BusanActivity.this, "You are already on the home screen", Toast.LENGTH_SHORT).show();
        });

        Button buttonKorean = findViewById(R.id.button_korean);
        Button buttonChinese = findViewById(R.id.button_Chinese);
        Button buttonItalian = findViewById(R.id.button_Italian);
        Button buttonJapanese = findViewById(R.id.button_Japanese);
        Button buttonFusion = findViewById(R.id.button_Fusion);
        Button buttonAsian = findViewById(R.id.button_Asian);
        Button buttonViewMore = findViewById(R.id.button_View_more);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button selectedButton = (Button) v;

                if (lastSelectedButton != null) {
                    lastSelectedButton.setSelected(false);
                }

                if (lastSelectedButton == selectedButton) {
                    lastSelectedButton = null;
                } else {
                    selectedButton.setSelected(true);
                    lastSelectedButton = selectedButton;
                }

                toggleRestaurantScrollView(selectedButton);
            }
        };

        buttonKorean.setOnClickListener(buttonClickListener);
        buttonChinese.setOnClickListener(buttonClickListener);
        buttonItalian.setOnClickListener(buttonClickListener);
        buttonJapanese.setOnClickListener(buttonClickListener);
        buttonFusion.setOnClickListener(buttonClickListener);
        buttonAsian.setOnClickListener(buttonClickListener);
        buttonViewMore.setOnClickListener(buttonClickListener);

        koreanScrollView = findViewById(R.id.korean_restaurant_scroll_view);
        chineseScrollView = findViewById(R.id.chinese_restaurant_scroll_view);
        italianScrollView = findViewById(R.id.italian_restaurant_scroll_view);
        japaneseScrollView = findViewById(R.id.japanese_restaurant_scroll_view);
        fusionScrollView = findViewById(R.id.fusion_restaurant_scroll_view);
        asianScrollView = findViewById(R.id.asian_restaurant_scroll_view);
        viewmoreScrollView = findViewById(R.id.viewmore_restaurant_scroll_view);
    }

    private void toggleRestaurantScrollView(Button button) {
        koreanScrollView.setVisibility(View.GONE);
        chineseScrollView.setVisibility(View.GONE);
        italianScrollView.setVisibility(View.GONE);
        japaneseScrollView.setVisibility(View.GONE);
        fusionScrollView.setVisibility(View.GONE);
        asianScrollView.setVisibility(View.GONE);
        viewmoreScrollView.setVisibility(View.GONE);

        if (button == findViewById(R.id.button_korean)) {
            koreanScrollView.setVisibility(View.VISIBLE);
        } else if (button == findViewById(R.id.button_Chinese)) {
            chineseScrollView.setVisibility(View.VISIBLE);
        } else if (button == findViewById(R.id.button_Italian)) {
            italianScrollView.setVisibility(View.VISIBLE);
        } else if (button == findViewById(R.id.button_Japanese)) {
            japaneseScrollView.setVisibility(View.VISIBLE);
        } else if (button == findViewById(R.id.button_Fusion)) {
            fusionScrollView.setVisibility(View.VISIBLE);
        } else if (button == findViewById(R.id.button_Asian)) {
            asianScrollView.setVisibility(View.VISIBLE);
        }
        else if (button == findViewById(R.id.button_View_more)) {
            viewmoreScrollView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String city = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + city, Toast.LENGTH_LONG).show();

        Intent intent = null;
        switch (city) {
            case "Seoul":
                intent = new Intent(this, MainActivity.class);
                break;
            case "Jeju":
                intent = new Intent(this, JejuActivity.class);
                break;
            case "Busan":
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "No city selected", Toast.LENGTH_SHORT).show();
    }
}