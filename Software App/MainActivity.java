package com.example.weatherdetector;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;

    EditText name;
    EditText email;
    EditText password;
    EditText reenter;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);

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

        signup.setOnClickListener(new View.OnClickListener){
            @Override
                    public void onClick(View view)
        {
            checkingTheData;
        }
    }


    void checkingTheData(){
        if(empty(name))
        {
            //Toast is used to display a message when user enters nothing or something wrong
            Toast toast= Toast.makeText(this, "Enter your first name to register", Toast.LENGTH_SHORT)
            toast.show();
        }

        if(email(email) == false)
        {
            email.setError("Invalid email. Enter the correct email");
        }
    }


        //temp_btn.setOnClickListener (view -> startActivity(new Intent(MainActivity.this, TemperatureReader.class)));

        //humid_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, HumidityReader.class)));

        //air_press_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirPressureReader.class)));

        //reading_btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReadingActivity.class)));

        //air_quality.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AirQualityReader.class)));

        //login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Login.class)));

        //app.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, App.class)));

        //first.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, FirstActivity.class)));
    }
