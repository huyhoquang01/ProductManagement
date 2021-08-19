package com.example.aidnetworking.assignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidnetworking.R;
import com.example.aidnetworking.assignment.AddProductActivity;
import com.example.aidnetworking.assignment.adapters.ProductAdapter;
import com.example.aidnetworking.models.ProductModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    TextView tvTime, tvUserName;
    EditText etSearch;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    IRetrofitServices iRetrofitServices;
    ArrayList<ProductModel> productList = new ArrayList<>();
    int count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);
        initializeViews(view);


        tvTime.setOnClickListener(view1 -> {
            count++;
            if(count==9){
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                intent.putExtra("productID", -1);
                startActivity(intent);
            }
        });

        return view;
    }

    Callback<ArrayList<ProductModel>> callbackGetAllProduct = new Callback<ArrayList<ProductModel>>() {
        @Override
        public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
            Log.d("Callback", "onResponse: running");
            if (response.body()!=null){
                Log.d("Callback", "onResponse: 2");
                if (productList.size()==0) {
                    productList = response.body();
                    adapter = new ProductAdapter(getContext(), productList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    productList.clear();
                    productList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("Callback", "onResponse: 3");
                }
            }
        }

        @Override
        public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

        }
    };

    private void initializeViews(View view) {
        tvTime = view.findViewById(R.id.tvTime);
        tvUserName = view.findViewById(R.id.tvUserName);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.rcvProduct);
    }

    private void setGreeting(){
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        calendar.get(Calendar.HOUR);        // gets hour in 12h format
        if (hour<11){
            tvTime.setText("morning");
        } else if (hour<18){
            tvTime.setText("afternoon");
        } else tvTime.setText("evening");
    }

    @Override
    public void onStart() {
        super.onStart();
        setGreeting();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume Check", "onResume");
        IRetrofitServices iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);
        iRetrofitServices.getAllProduct().enqueue(callbackGetAllProduct);
    }
}
