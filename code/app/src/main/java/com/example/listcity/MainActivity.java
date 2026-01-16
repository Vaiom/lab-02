package com.example.listcity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    EditText input_field;
    private int current_pos = -1;

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
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setChoiceMode(1);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update background
                view.setBackgroundColor(Color.parseColor("#E0E0FF"));
                // set new position
                if (current_pos == -1) {
                    current_pos = position;
                // code for handling previously selected item
                } else if (current_pos != position) {
                    // restore old background
                    View prev = parent.getChildAt(current_pos);
                    prev.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    current_pos = position;
                }
            }
        });

        input_field = findViewById(R.id.input_field);
        Button add_city_button = findViewById(R.id.add_button);
        Button del_city_button = findViewById(R.id.del_button);
        Button confirm_button = findViewById(R.id.confirm_button);

        add_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show (unhide)
                input_field.setVisibility(VISIBLE);
                confirm_button.setVisibility(VISIBLE);
            }
        });


        del_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_pos != -1) {
                    dataList.remove(current_pos);
                    cityList.setAdapter(cityAdapter);
                    current_pos = -1;
                }
            }
        });


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add to list
                String user_input = input_field.getText().toString();
                if (!user_input.isEmpty()) {
                    dataList.add(input_field.getText().toString());
                    input_field.setText("");
                    // Refresh list
                    cityList.setAdapter(cityAdapter);
                    // Hide
                    input_field.setVisibility(GONE);
                    confirm_button.setVisibility(GONE);
                }
            }
        });

    }
}