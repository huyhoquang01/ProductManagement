package com.example.aidnetworking.assignment.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aidnetworking.R;
import com.example.aidnetworking.assignment.AddProductActivity;
import com.example.aidnetworking.models.ProductModel;
import com.example.aidnetworking.models.ResponseModel;
import com.example.aidnetworking.myretrofit.IRetrofitServices;
import com.example.aidnetworking.myretrofit.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> list;
    IRetrofitServices iRetrofitServices = new RetrofitBuilder().createService(IRetrofitServices.class);
    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPrice.setText(String.valueOf(list.get(position).getPrice()));
        holder.tvProductName.setText(list.get(position).getProduct_name());
        Glide.with(context).load(list.get(position).getImage_url()).into(holder.ivProductImage);

        holder.ivMore.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, holder.ivMore);
            popup.inflate(R.menu.item_menu);
            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.action_Update) {
                    Intent intent = new Intent(context, AddProductActivity.class);
                    intent.putExtra("productID", list.get(position).getId());
                    context.startActivity(intent);
                } else if (menuItem.getItemId() == R.id.action_Remove) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Are you sure about that?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Remove",
                            (dialog, which) -> {
                                // remove item
                                ProductModel productModel = new ProductModel(list.get(position).getId());
                                Log.d("prod", "onBindViewHolder: " + productModel);
                                IRetrofitServices services = new RetrofitBuilder().createService(IRetrofitServices.class);
                                services.deleteProduct(productModel).enqueue(callbackRemove);
                                dialog.dismiss();
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView tvPrice, tvProductName;
        ImageView ivProductImage, ivMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            ivProductImage = itemView.findViewById(R.id.ivProduct);
            ivMore = itemView.findViewById(R.id.ivOptions);
        }
    }

    Callback<ResponseModel> callbackRemove = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            Log.d("res", "onResponse: " + response.body());
            if (response.isSuccessful() && response.body() != null) {
                ResponseModel res = response.body();
                if (res.getStatus()) {
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                    iRetrofitServices.getAllProduct().enqueue(callbackGetAllProduct);
                } else {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_LONG).show();
                }
            } else Log.e("Response Remove Error", "onResponse: ");
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e("Callback Delete", "onFailure: " + t.getMessage());
        }
    };

    Callback<ArrayList<ProductModel>> callbackGetAllProduct = new Callback<ArrayList<ProductModel>>() {
        @Override
        public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
            if (response.body()!=null) {
                list.clear();
                list.addAll(response.body());
                notifyDataSetChanged();
            } else Toast.makeText(context, "There's an error. Please try again later.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
            Log.e("Get All Products failed", "onFailure: " + t.getMessage());
        }
    };
}
