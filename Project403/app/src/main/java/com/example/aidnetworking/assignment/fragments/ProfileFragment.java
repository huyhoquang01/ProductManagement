package com.example.aidnetworking.assignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.aidnetworking.Labs.Lab7Activity;
import com.example.aidnetworking.R;

public class ProfileFragment extends Fragment {
    TextView tvChat;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        tvChat = view.findViewById(R.id.tvChat);
        tvChat.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), Lab7Activity.class));
        });
        return view;
    }
}
