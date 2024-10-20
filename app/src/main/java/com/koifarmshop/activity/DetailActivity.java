package com.koifarmshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.koifarmshop.R;
import com.koifarmshop.model.Cart;
import com.koifarmshop.model.NewKoi;
import com.koifarmshop.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView tenCa, giaCa, moTa;
    Button btnThem;
    ImageView imgHinhAnh;
    Spinner spinner;
    Toolbar toolbar;
    NewKoi newKoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        ActionToolBar();
        initData();
        initControl();
        badge = findViewById(R.id.menu_sl);
    }

    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        if (!Utils.cartArray.isEmpty()) {
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
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
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
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
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        newKoi = (NewKoi) getIntent().getSerializableExtra("chitiet");
        tenCa.setText(newKoi.getTenCa());
        moTa.setText(newKoi.getMota());
        Glide.with(getApplicationContext()).load(newKoi.getHinhAnh()).into(imgHinhAnh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giaCa.setText("Giá: " + decimalFormat.format(Double.parseDouble(newKoi.getGia())) + "Đ");

        // set value cho spinner - số lượng item add vô giỏ hàng
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterSpin = new ArrayAdapter<>(this, com.nex3z.notificationbadge.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterSpin);
    }

    private void initView() {
        tenCa = findViewById(R.id.txttenca);
        giaCa = findViewById(R.id.txtgiaca);
        moTa = findViewById(R.id.txtmotachitiet);
        btnThem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imgHinhAnh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.menu_sl);
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
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
            badge.setText(String.valueOf(totalItem));
        }
    }
}