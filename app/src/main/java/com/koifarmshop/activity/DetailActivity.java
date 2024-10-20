package com.koifarmshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.koifarmshop.R;
import com.koifarmshop.databinding.ActivityDetailBinding;
import com.koifarmshop.model.Cart;
import com.koifarmshop.model.NewKoi;
import com.koifarmshop.utils.Utils;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    NewKoi newKoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        binding.btnthemvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        if (!Utils.cartArray.isEmpty()) {
            boolean flag = false;
            int soluong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
            //Kiem tra co trong gio chua neu co roi thi thay doi so luong
            for (int i = 0; i < Utils.cartArray.size(); i++) {
                if (Utils.cartArray.get(i).getIdCa() == newKoi.getId()) ;
                {
                    Utils.cartArray.get(i).setSoluong(soluong + Utils.cartArray.get(i).getSoluong());
                    long gia = Long.parseLong(newKoi.getGia()) * Utils.cartArray.get(i).getSoluong();
                    Utils.cartArray.get(i).setGiaCa(gia);
                    flag = true;
                }
            }
            if (flag == false) {
                long gia = Long.parseLong(newKoi.getGia()) * soluong;
                Cart gioHang = new Cart();
                gioHang.setGiaCa(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdCa(newKoi.getId());
                gioHang.setTenCa(newKoi.getTenCa());
                gioHang.setHinhCa(newKoi.getHinhAnh());
                Utils.cartArray.add(gioHang);
            }
        } else {
            int soluong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
            long gia = Long.parseLong(newKoi.getGia()) * soluong;
            Cart gioHang = new Cart();
            gioHang.setGiaCa(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdCa(newKoi.getId());
            gioHang.setTenCa(newKoi.getTenCa());
            gioHang.setHinhCa(newKoi.getHinhAnh());
            Utils.cartArray.add(gioHang);
        }

        int totalItem = 0;
        for (int i = 0; i < Utils.cartArray.size(); i++) {
            totalItem = totalItem + Utils.cartArray.get(i).getSoluong();
        }
        binding.menuSl.setText(String.valueOf(totalItem));
    }

    private void initData() {
        newKoi = (NewKoi) getIntent().getSerializableExtra("chitiet");
        binding.txttenca.setText(newKoi.getTenCa());
        binding.txtmotachitiet.setText(newKoi.getMota());
        Glide.with(getApplicationContext()).load(newKoi.getHinhAnh()).into(binding.imgchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txtgiaca.setText("Giá: " + decimalFormat.format(Double.parseDouble(newKoi.getGia())) + "Đ");

        // set value cho spinner - số lượng item add vô giỏ hàng
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterSpin = new ArrayAdapter<>(this, com.nex3z.notificationbadge.R.layout.support_simple_spinner_dropdown_item, so);
        binding.spinner.setAdapter(adapterSpin);
    }

    private void initView() {
        FrameLayout frameLayoutGioHang = findViewById(R.id.framegiohang);
        frameLayoutGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gioHang = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(gioHang);
            }
        });

        if (Utils.cartArray != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.cartArray.size(); i++) {
                totalItem = totalItem + Utils.cartArray.get(i).getSoluong();
            }
            binding.menuSl.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.cartArray != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.cartArray.size(); i++) {
                totalItem = totalItem + Utils.cartArray.get(i).getSoluong();
            }
            binding.menuSl.setText(String.valueOf(totalItem));
        }
    }
}