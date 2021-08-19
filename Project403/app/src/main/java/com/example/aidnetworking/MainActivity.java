package com.example.aidnetworking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.aidnetworking.assignment.AssignmentActivity;

public class MainActivity extends AppCompatActivity {
    Button btnLab, btnAsm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLab = findViewById(R.id.btnLab);
        btnAsm = findViewById(R.id.btnAssignment);

        btnAsm.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AssignmentActivity.class));
        });

        btnLab.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, AssignmentActivity.class));
        });
    }
}