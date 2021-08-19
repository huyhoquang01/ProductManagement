package com.example.aidnetworking.Labs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aidnetworking.R;
import com.example.aidnetworking.models.MessageModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Lab7Activity extends AppCompatActivity {
    ImageButton ivSend;
    EditText etMessage;
    TextView tvMessage;
    MessageAdapter adapter;
    RecyclerView recyclerView;
    ImageView ivTrump;
    ArrayList<MessageModel> list = new ArrayList<>();
    public static final String TAG = "My debug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab7);

        ivSend = findViewById(R.id.ivSend);
        etMessage = findViewById(R.id.etMessage);
        tvMessage = findViewById(R.id.tvMessage);
        ivTrump = findViewById(R.id.ivAvatarTrump);

        adapter = new MessageAdapter(this,list);
        recyclerView = findViewById(R.id.rcvChat);

        Glide.with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Donald_Trump_%285440393641%29_closeup.jpg/649px-Donald_Trump_%285440393641%29_closeup.jpg")
                .into(ivTrump);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.3.2:2046/").build();
        WebSocket socket = client.newWebSocket(request, listener);
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();

        ivSend.setOnClickListener(view -> {
            String msg = etMessage.getText().toString();
            if (msg.length()!=0){
                MessageModel messageModel = new MessageModel(msg, true);
                JSONObject object = new JSONObject();

                try {
                    object.put("message",messageModel.getMessage());
                    object.put("isMyMessage", messageModel.getIsMyMessage());
                    socket.send(object.toString());

                    ArrayList<MessageModel> list_clone = new ArrayList<>(list);
                    list_clone.add(messageModel);
                    list.clear();
                    list.addAll(list_clone);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            etMessage.setText("");
        });

    }
        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                try {
                    text = text.replace("\\\"","'");
                    text = text.substring(1,text.length()-1);
                    Log.d(TAG, "onMessage: " + text);
                    JSONObject object = new JSONObject(text);
                    String messageReceive = object.getString("data");
                    MessageModel messageModel = new MessageModel(messageReceive, false);

                    runOnUiThread(() -> {
                        ArrayList<MessageModel> list_clone = new ArrayList<>(list);
                        list_clone.add(messageModel);
                        list.clear();
                        list.addAll(list_clone);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        };

}