package com.koifarmshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koifarmshop.R;
import com.koifarmshop.adapter.CartAdapter;
import com.koifarmshop.model.EventBus.SumEvent;
import com.koifarmshop.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    TextView emptyCart, tongTien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnMuaHang;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        initView();
        initControl();
        tinhTongTien();
    }

    private void tinhTongTien() {
        long tongTienGioHang = 0;
        for (int i = 0; i < Utils.cartArray.size(); i++) {
            tongTienGioHang = tongTienGioHang + (Utils.cartArray.get(i).getGiaCa() * Utils.cartArray.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(tongTienGioHang));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //kiem tra coi gio hang co trong khong
        if (Utils.cartArray.size() == 0) {
            emptyCart.setVisibility(View.VISIBLE);
        } else {
            adapter = new CartAdapter(getApplicationContext(), Utils.cartArray);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initView() {
        emptyCart = findViewById(R.id.txtgiohangtrong);
        tongTien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnMuaHang = findViewById(R.id.btnmuahang);
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