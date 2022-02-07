package com.example.weatherdetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.app_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                        openApp();
            }
        });
    }

        private void openApp() {
            Intent in = new Intent(this, App.class);
            startActivity(in);
        }

        Button temp_btn = findViewById(R.id.temperature_button);
        Button humid_btn = findViewById(R.id.humidity_button);
        Button air_press_btn = findViewById(R.id.air_pressure_button);
        Button reading_btn = findViewById(R.id.reading_button);
        Button air_quality = findViewById(R.id.air_quality_button);
        Button login = findViewById(R.id.login_button);
        Button signup = findViewById(R.id.signup_button);
        //Button app = findViewById(R.id.app_button);
        Button first = findViewById(R.id.first_button);

        //temp_btn.setOnClickListener (view -> startActivity(new Intent(MainActivity.this, TemperatureReader.class)));

        //humid_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, HumidityReader.class)));

        //air_press_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirPressureReader.class)));

        //reading_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReadingActivity.class)));

        //air_quality.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirQualityReader.class)));

        //login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Login.class)));

        //app.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, App.class)));

        //first.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, FirstActivity.class)));
    }
