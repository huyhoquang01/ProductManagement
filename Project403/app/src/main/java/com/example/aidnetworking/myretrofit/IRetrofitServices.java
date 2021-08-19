package com.example.aidnetworking.myretrofit;

import com.example.aidnetworking.models.CategoryModel;
import com.example.aidnetworking.models.ProductModel;
import com.example.aidnetworking.models.Response2PikModel;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.models.UserModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IRetrofitServices {
    @POST("user_login.php")
    Call<ResponseModel> login(@Body UserModel user);

    @POST("user_register.php")
    Call<ResponseModel> register(@Body UserModel user);

    @POST("user_check_email.php")
    Call<Integer> getUserByEmail(@Body UserModel user);

    @POST("user_forgot_password.php")
    Call<ResponseModel> changePassword(@Body UserModel user);

    @POST("product_get_all.php")
    Call<ArrayList<ProductModel>> getAllProduct();

    @POST("product_insert.php")
    Call<ResponseModel> insertProduct(@Body ProductModel productModel);

    @POST("product_update.php")
    Call<ResponseModel> updateProduct(@Body ProductModel productModel);

    @POST("category_get_all.php")
    Call<ArrayList<CategoryModel>> getAllCategory();

    @POST("product_get_by_id.php")
    Call<ProductModel> getProductById(@Body ProductModel product);

    @POST("product_delete.php")
    Call<ResponseModel> deleteProduct(@Body ProductModel product);

    @Multipart
    @POST("/")
    Call<Response2PikModel> upload2pik(@Part MultipartBody.Part image);

}
