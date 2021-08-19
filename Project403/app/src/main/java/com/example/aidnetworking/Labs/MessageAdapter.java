package com.example.aidnetworking.Labs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidnetworking.R;
import com.example.aidnetworking.models.MessageModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    ArrayList<MessageModel> list;

    public static final String TAG = "My Debugs";

    public MessageAdapter(Context context, ArrayList<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMessage.setText(list.get(position).getMessage());
        if (list.get(position).getIsMyMessage()) {
            holder.item.setGravity(Gravity.END);
            holder.tvMessage.setBackgroundResource(R.drawable.my_message);
        } else {
            holder.tvMessage.setGravity(Gravity.START);
            holder.tvMessage.setBackgroundResource(R.drawable.message_bg);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.cardViewMessage);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

}
