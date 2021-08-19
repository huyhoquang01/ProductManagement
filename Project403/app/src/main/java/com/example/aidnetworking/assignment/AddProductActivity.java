package com.example.aidnetworking.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aidnetworking.R;
import com.example.aidnetworking.models.CategoryModel;
import com.example.aidnetworking.models.ProductModel;
import com.example.aidnetworking.models.Response2PikModel;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    TextView tvForm;
    EditText etName, etPrice;
    Spinner spinnerCategory;
    Button btnCapture, btnCancel, btnInsert;
    ImageView ivCaptured;

    IRetrofitServices iRetrofitServices;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    int productId;
    static String BASE_2PIK_URL = "https://2.pik.vn/";
    String image_url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);
        initializeViews();

        iRetrofitServices.getAllCategory().enqueue(getCategory);

        btnCapture.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        });

        btnCancel.setOnClickListener(view -> finish());

        btnInsert.setOnClickListener(view -> {
            String name = etName.getText().toString().trim(),
                    price = etPrice.getText().toString().trim();
            if (name.length() != 0 && price.length() != 0) {
                try {
                    // CHECK AND PREPARE DATA
                    double priceValue = Double.parseDouble(price);
                    CategoryModel category = (CategoryModel) spinnerCategory.getSelectedItem();

                    // CHECK "INSERT OR UPDATE"?
                    if (productId == -1) {
                        // "https://h2hglobal.org/wp-content/uploads/2020/11/che_pham_lam_sach_rang.jpg"
                        ProductModel product = new ProductModel(-1, priceValue, category.getId(), name, image_url);
                        iRetrofitServices.insertProduct(product).enqueue(insert_update_CB);
                    } else {
                        Log.d("Product ID for Updating", "id= " + productId);
                        ProductModel product = new ProductModel(productId, priceValue, category.getId(), name, "https://h2hglobal.org/wp-content/uploads/2020/11/che_pham_lam_sach_rang.jpg");
                        iRetrofitServices.updateProduct(product).enqueue(insert_update_CB);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Boy! It is price", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Don't let it empty, bro.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // CALLBACK GET ALL CATEGORY
    Callback<ArrayList<CategoryModel>> getCategory = new Callback<ArrayList<CategoryModel>>() {
        @Override
        public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
            if (response.body() != null) {
                categoryList = response.body();
                ArrayAdapter<CategoryModel> adapter = new ArrayAdapter<>(AddProductActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(adapter);
                if (productId!=-1){
                    ProductModel productModel = new ProductModel(productId);
                    iRetrofitServices.getProductById(productModel).enqueue(callbackGetProductById);
                }
            }
        }

        @Override
        public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
            Toast.makeText(AddProductActivity.this, "Try again later.", Toast.LENGTH_SHORT).show();
            Log.i("err get categories", "onFailure: " + t.getMessage());
        }
    };

    // INIT VIEWS
    private void initializeViews() {
        etName = findViewById(R.id.etAddName);
        etPrice = findViewById(R.id.etAddPrice);
        spinnerCategory = findViewById(R.id.spnCategory);
        btnCapture = findViewById(R.id.btnCaptureImage);
        btnInsert = findViewById(R.id.btnInsert);
        btnCancel = findViewById(R.id.btnCancel);
        ivCaptured = findViewById(R.id.ivProduct);
        tvForm = findViewById(R.id.tvFormTitle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            encoded = "data:image/png;base64," + encoded;
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", encoded);
            IRetrofitServices service = new RetrofitBuilder().createService2Pik(IRetrofitServices.class, BASE_2PIK_URL);
            service.upload2pik(part).enqueue(uploadCB);
        }
    }

    // CALLBACK UPLOAD IMAGE TO SERVER
    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()) {
                Response2PikModel model = response.body();
                image_url = model.getSaved();
                Log.i("My debugs", image_url);
                Glide.with(AddProductActivity.this)
                        .load(image_url)
                        .into(ivCaptured);
            } else {
                Log.i("uploadCB Error: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.i("onFailure uploadCB: ", t.getMessage());
        }
    };

    // CALLBACK PRODUCT "INSERT AND UPDATE"
    Callback<ResponseModel> insert_update_CB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful() && response.body()!=null) {
                ResponseModel model = response.body();
                if (model.getStatus()) {
                    Toast.makeText(AddProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Insert failed",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("insertCB onResponse>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e("insertCB onFailure>>>>", t.getMessage());
        }
    };

    // CALLBACK GET PRODUCT BY ID
    Callback<ProductModel> callbackGetProductById = new Callback<ProductModel>() {
        @Override
        public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
            if (response.isSuccessful() && response.body()!=null){
                ProductModel model = response.body();
                productId=model.getId();
                etName.setText(model.getProduct_name());
                etPrice.setText(String.valueOf(model.getPrice()));
                int index = getIndex(categoryList, model.getCategory_id());
                spinnerCategory.setSelection(index);
                image_url = model.getImage_url();
                Glide.with(AddProductActivity.this)
                        .load(model.getImage_url())
                        .into(ivCaptured);
            } else {
                Log.e("getByIdCB onResponse>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<ProductModel> call, Throwable t) {
            Log.e("Get product=id error", "onFailure: " + t.getMessage() );
        }
    };

    // GET INDEX OF CATEGORY SPINNER
    private Integer getIndex(ArrayList<CategoryModel> _data, int category_id){
        for (int i = 0; i< _data.size(); i++) {
            if (_data.get(i).getId() == category_id){
                return i;
            }
        }
        return 0;
    }

    // SET TITLE FORM
    private void setTitleForm(int id){
        if (id==-1){
            tvForm.setText("Add a new Product");
            btnInsert.setText("Insert");
        } else {
            tvForm.setText("Update Product");
            btnInsert.setText("Update");
        }
    }

    // GET PRODUCT ID FROM START
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        productId = intent.getIntExtra("productID", -1);
        setTitleForm(productId);
    }
}