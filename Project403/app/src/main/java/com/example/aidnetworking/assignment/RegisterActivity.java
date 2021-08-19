package com.example.aidnetworking.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidnetworking.LoadingDialog;
import com.example.aidnetworking.R;
import com.example.aidnetworking.models.AccessTokenManager;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.models.UserModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aidnetworking.assignment.GlobalFunctions.isGmailAddress;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword, etPasswordConfirm;
    Button btnRegister;
    TextView tvGoLogin;
    IRetrofitServices iRetrofitServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);

        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            if (email.length() > 0 && isGmailAddress(email)) {
                // check email is existed or not
                UserModel userModel = new UserModel(email, "");
                iRetrofitServices.getUserByEmail(userModel).enqueue(callbackCheckEmail);
            } else {
                Toast.makeText(this, "Your email address is not valid or empty.", Toast.LENGTH_LONG).show();
            }
        });
        tvGoLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    Callback<ResponseModel> callbackRegister = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.body()!=null && response.isSuccessful()) {
                ResponseModel resModel = response.body();
                Toast.makeText(RegisterActivity.this, resModel.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            } else Toast.makeText(RegisterActivity.this, "Response is null!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.i("error", "onFailure: " + t.getMessage());
        }
    };

    Callback<Integer> callbackCheckEmail = new Callback<Integer>() {
        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            try {
                int status = response.body();
                if (status!=1) {
                    String email = etEmail.getText().toString(),
                            password = etPassword.getText().toString(),
                            confirmPassword = etPasswordConfirm.getText().toString();
                    if (password.length() >= 8 && password.equals(confirmPassword)) {
                        // perform register
                        UserModel userModel = new UserModel(email, password);
                        iRetrofitServices.register(userModel).enqueue(callbackRegister);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Your password does not matched or is not valid.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "This email is already used.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.i("check email", "onResponse: " + e.getMessage());
                Toast.makeText(RegisterActivity.this, "Please try again later. ", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
            Log.i("err", "onFailure: " + t.getMessage());
            Toast.makeText(RegisterActivity.this, "Please try again later. ", Toast.LENGTH_LONG).show();
        }
    };

    private void initializeViews() {
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoLogin = findViewById(R.id.tvGoLogin);
    }

}