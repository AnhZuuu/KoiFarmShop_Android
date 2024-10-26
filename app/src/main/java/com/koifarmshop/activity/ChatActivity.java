package com.koifarmshop.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.koifarmshop.R;

public class ChatActivity extends AppCompatActivity {

    private TextInputEditText queryEditText;

    private ImageView sendQuery;

    FloatingActionButton btnShowDialog;

    private ProgressBar progressBar;

    private LinearLayout chatResponse;

    private ChatFutures chatModel;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_activity);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.message_dialog);

        if(dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
        }

        sendQuery= dialog.findViewById(R.id.sendMessage);
        queryEditText = dialog.findViewById(R.id.queryEditText);

        btnShowDialog = findViewById(R.id.showMessageDialog);
        progressBar = findViewById(R.id.progressBar);
        chatResponse = findViewById(R.id.chatResponse);

        chatModel = getChatModel();
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        sendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                String query = queryEditText.getText().toString();

                queryEditText.setText("");

                chatBody("You", query, getDrawable(R.drawable.ic_launcher_background));

                GeminiResp.getResponse(chatModel, query, new ResponseCallback() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        chatBody("AI", response, getDrawable(R.drawable.ic_launcher_foreground));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        chatBody("AI", "Please try again.", getDrawable(R.drawable.ic_launcher_foreground));
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

        });
    }

    private ChatFutures getChatModel() {
        GeminiResp model = new GeminiResp();
        GenerativeModelFutures modelFutures = model.getModel();

        return modelFutures.startChat();
    }

    private void chatBody(String userName, String query, Drawable image) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_message, null);

        TextView name = view.findViewById(R.id.name);
        TextView message = view.findViewById(R.id.agentMessage);

        name.setText(userName);
        message.setText(query);

        chatResponse.addView(view);

        ScrollView scrollView = findViewById(R.id.scrollView2);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

    }
}