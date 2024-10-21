package com.koifarmshop.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.koifarmshop.R;
import com.koifarmshop.databinding.ActivityQractivityBinding;

import java.text.DecimalFormat;

public class QRActivity extends AppCompatActivity {
    private ActivityQractivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityQractivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);

        binding.tien.setText(decimalFormat.format(tongtien));


    }
}