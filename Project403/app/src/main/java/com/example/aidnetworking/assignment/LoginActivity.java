package com.example.aidnetworking.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidnetworking.R;
import com.example.aidnetworking.models.AccessToken;
import com.example.aidnetworking.models.AccessTokenManager;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.models.UserModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvGoRegister, tvGoForgotPassword;
    EditText etEmail, etPassword;
    Button btnLogin;

    IRetrofitServices iRetrofitServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        tvGoRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        tvGoForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ChangePasswordActivity.class));
            finish();
        });

        iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString(),
                    password = etPassword.getText().toString();
            UserModel userModel = new UserModel(email, password);
            iRetrofitServices.login(userModel).enqueue(callbackLogin);
        });
    }

    Callback<ResponseModel> callbackLogin = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            ResponseModel responseModel = response.body();
            Log.i("mmm", "onResponse: " + response.body());
            if (responseModel!=null) {
                if (responseModel.getStatus()) {
                    Toast.makeText(LoginActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, AssignmentActivity.class));
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "No data found! Please check your account try again.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.i("err login", "onFailure: " + t.getMessage());
        }
    };

    private void initializeViews() {
        tvGoRegister = findViewById(R.id.tvGoRegister);
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoForgotPassword = findViewById(R.id.tvGoForgotPassword);
    }
}