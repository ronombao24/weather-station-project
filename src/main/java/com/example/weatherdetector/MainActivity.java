package com.example.weatherdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Button air_quality_btn = (Button)findViewById(R.id.air_quality_button);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button temp_btn = findViewById(R.id.temperature_button);
        Button humid_btn = findViewById(R.id.humidity_button);
        Button air_press_btn = findViewById(R.id.air_pressure_button);
        Button reading_btn = findViewById(R.id.reading_button);

        temp_btn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TemperatureReader.class));
            }
        });

        humid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HumidityReader.class));
            }
        });

        air_press_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AirPressureReader.class));
            }
        });

        reading_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReadingActivity.class));
            }
        });
    }
}