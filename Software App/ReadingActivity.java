package com.example.weatherdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadingActivity extends AppCompatActivity {
TextView temp, air_press, air_quality, humid;
FirebaseDatabase db;
DatabaseReference ref_values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        temp = (TextView)findViewById(R.id.temp_read_val);
        //air_press = (TextView)findViewById(R.id.air_press_val);
        air_quality = (TextView)findViewById(R.id.air_qual_read_val);
        //humid = (TextView)findViewById(R.id.humid_val);
        ref_values = db.getInstance().getReference().child("Weather Station Reading");
        ref_values.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a_q = snapshot.child("CO2_reading").getValue().toString(); //AIR QUALITY
                String t = snapshot.child("temperature_reading").getValue().toString(); //TEMPERATURE
                temp.setText(" " + t + " °C");
                air_quality.setText(" " + a_q + " ppm");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}