package com.example.aidnetworking.assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidnetworking.LoadingDialog;
import com.example.aidnetworking.R;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.models.UserModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText etEmail;
    Button btnSendEmail;
    TextView tvBackLogin;
    IRetrofitServices iRetrofitServices;
    LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etEmail = findViewById(R.id.etEmailForgotP);
        btnSendEmail = findViewById(R.id.btnForgotP);
        tvBackLogin = findViewById(R.id.tvBackLogin);
        iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);

        btnSendEmail.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            UserModel userModel = new UserModel(email,"");
            loadingDialog.startLoadingDialog();
            iRetrofitServices.changePassword(userModel).enqueue(callbackSendEmail);
        });

        tvBackLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    Callback<ResponseModel> callbackSendEmail = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            ResponseModel responseModel = response.body();
            if (responseModel != null){
                if (responseModel.getStatus()){
                    showDialog("Great", "An email has been sent to you. Check it out!");
                    loadingDialog.dismissDialog();
                    etEmail.setText("");
                }
            } else {
                showDialog("Oh no!","An error occur in progress! Please check your email address and try again.");
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            showDialog("Oh no!","An error occur in progress! Please try again later.");
        }
    };

    private void showDialog(String alert, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
        alertDialog.setTitle(alert);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
                (dialogInterface, i) -> {
                    alertDialog.dismiss();
                });
        alertDialog.show();
    }
}