package com.example.dfcsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dfcsproject.IncomingRequests.Dashboard;

public class HomeScreen extends AppCompatActivity {

    LinearLayout responces,doctorlist,labratorylist,medicallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        responces = findViewById(R.id.responces);
        doctorlist = findViewById(R.id.doctorList);
        labratorylist = findViewById(R.id.lablist);
        medicallist = findViewById(R.id.medicallist);



        responces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Dashboard.class);
                startActivity(intent);
            }
        });
        doctorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, com.example.dfcsproject.Doctor.Dashboard.class);
                startActivity(intent);
            }
        });
        labratorylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, com.example.dfcsproject.Lab.Dashboard.class);
                startActivity(intent);
            }
        });
        medicallist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,com.example.dfcsproject.Medical.Dashboard.class);
                startActivity(intent);
            }
        });


    }
}