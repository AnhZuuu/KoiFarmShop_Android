package com.koifarmshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.koifarmshop.R;
import com.koifarmshop.databinding.ActivityPurchaseBinding;
import com.koifarmshop.utils.Utils;

import java.text.DecimalFormat;

public class PurchaseActivity extends AppCompatActivity {
private ActivityPurchaseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase);
        binding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initControl();
    }

    private void initView() {
    }

    private void initControl() {
        setSupportActionBar(binding.toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        Log.d("TAG", "initControl: " + tongtien);

        binding.tongtiendathang.setText(decimalFormat.format(tongtien));

        binding.txtemail.setText(Utils.user_current.getEmail());
        binding.txtsodienthoai.setText(Utils.user_current.getPhone());

        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = binding.editDiaChi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    //post data
                    Log.d("test", new Gson().toJson(Utils.cartArray));
                    Intent intent = new Intent(getApplicationContext(), QRActivity.class);
                    intent.putExtra("tongtien", tongtien);
                    startActivity(intent);
                }
            }
        });
    }


}