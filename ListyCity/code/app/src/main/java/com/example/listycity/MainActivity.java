package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton, deleteButton, confirmButton;
    EditText enterCity;

    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        confirmButton = findViewById(R.id.confirm_button);
        enterCity = findViewById(R.id.enter_city);

        String []cities = {"Edmonton,", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Select city
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selected = position;
            cityList.setItemChecked(position, true);
        });

        // Add city
        addButton.setOnClickListener(v -> {
            findViewById(R.id.bottom_row).setVisibility(View.VISIBLE);
            enterCity.setText("");
            enterCity.requestFocus();
        });

        // Confirm
        confirmButton.setOnClickListener(v -> {
            String newCity = enterCity.getText().toString().trim();

            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
            }

            findViewById(R.id.bottom_row).setVisibility(View.GONE);
        });

        // DELETE CITY
        deleteButton.setOnClickListener(v -> {
            if (selected != -1) {
                dataList.remove(selected);
                cityAdapter.notifyDataSetChanged();
                selected = -1;
                cityList.clearChoices();
            }
        });


    }
}