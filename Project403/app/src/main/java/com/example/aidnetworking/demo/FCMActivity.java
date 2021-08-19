package com.example.aidnetworking.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidnetworking.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class FCMActivity extends AppCompatActivity {

    public static final String TAG = "My Debugs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_activity);

        Button btndemo1 = findViewById(R.id.btnDemo1);

        btndemo1.setOnClickListener(view -> {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isComplete()){
                    Log.e(TAG, "error: " + task.getException());
                }
                String token = task.getResult();
                Log.d(TAG, "token: " + token);
            });
        });
    }
}