package com.koifarmshop.activity;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koifarmshop.R;
import com.koifarmshop.adapter.CartAdapter;
import com.koifarmshop.databinding.ActivityCartBinding;
import com.koifarmshop.model.EventBus.SumEvent;
import com.koifarmshop.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initControl();
        tinhTongTien();
    }

    private void tinhTongTien() {
        long tongTienGioHang = 0;
        for (int i = 0; i < Utils.cartArray.size(); i++) {
            tongTienGioHang = tongTienGioHang + (Utils.cartArray.get(i).getGiaCa() * Utils.cartArray.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txttongtien.setText(decimalFormat.format(tongTienGioHang));
    }

    private void initControl() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.recycleviewgiohang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleviewgiohang.setLayoutManager(layoutManager);

        //kiem tra coi gio hang co trong khong
        if (Utils.cartArray.size() == 0) {
            binding.txtgiohangtrong.setVisibility(View.VISIBLE);
        } else {
            adapter = new CartAdapter(getApplicationContext(), Utils.cartArray);
            binding.recycleviewgiohang.setAdapter(adapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventCalculatorSum(SumEvent event) {
        if (event != null) {
            tinhTongTien();
        }
    }
}