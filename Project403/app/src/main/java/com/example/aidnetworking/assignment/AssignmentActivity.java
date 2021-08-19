package com.example.aidnetworking.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aidnetworking.R;
import com.example.aidnetworking.assignment.fragments.HomeFragment;
import com.example.aidnetworking.assignment.fragments.CartFragment;
import com.example.aidnetworking.assignment.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AssignmentActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        bottomNav = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, HomeFragment.class, null)
                .commit();

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, HomeFragment.class, null)
                        .addToBackStack("")
                        .commit();

            } else if (item.getItemId() == R.id.nav_cart) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, CartFragment.class, null)
                        .addToBackStack("")
                        .commit();

            } else if (item.getItemId() == R.id.nav_user) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, ProfileFragment.class, null)
                        .addToBackStack("")
                        .commit();

            }
            return false;
        });
    }
}