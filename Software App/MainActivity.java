package com.example.weatherdetector;

//These are the imports needed
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button temp_btn = findViewById(R.id.temperature_button);
        Button humid_btn = findViewById(R.id.humidity_button);
        Button air_press_btn = findViewById(R.id.air_pressure_button);
        Button reading_btn = findViewById(R.id.reading_button);
        Button air_quality = findViewById(R.id.air_quality_button);

        temp_btn.setOnClickListener (view -> startActivity(new Intent(MainActivity.this, TemperatureReader.class)));

        humid_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, HumidityReader.class)));

        air_press_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirPressureReader.class)));

        reading_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReadingActivity.class)));

        air_quality.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirQualityReader.class)));
    }
}
