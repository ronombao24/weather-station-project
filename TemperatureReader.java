package com.example.weatherdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TemperatureReader extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref_values;

    GraphView tempGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_reader);
        tempGraph = (GraphView) findViewById(R.id.temp_chart);
        db = FirebaseDatabase.getInstance();
        ref_values = db.getInstance().getReference().child("Weather Station Reading");
    }
    @Override
    protected void onStart() {
        super.onStart();
        ref_values.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index = 0;
                double xVal = 0;
                double yVal = Double.parseDouble(snapshot.child("temperature_reading").getValue().toString());
                DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                for (DataSnapshot myData : snapshot.getChildren()) {
                    dp[index] = new DataPoint(xVal, yVal);
                    index++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}