package com.koifarmshop.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.koifarmshop.R;
import com.koifarmshop.adapter.FishKindAdapter;
import com.koifarmshop.adapter.NewKoiAdapter;
import com.koifarmshop.model.FishKind;
import com.koifarmshop.model.NewKoi;
import com.koifarmshop.model.NewKoiModel;
import com.koifarmshop.retrofit.ApiBanCa;
import com.koifarmshop.retrofit.RetrofitClient;
import com.koifarmshop.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    FishKindAdapter fishKindAdapter;
    List<FishKind> fishKindArray;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanCa apiBanCa;
    List<NewKoi> newKoiArray;
    NewKoiAdapter koiAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        apiBanCa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanCa.class);

        show();
        ActionBar();


        if (isConnected(this)) {

            ActionViewFlipper();
            getNewKoi();
            getFishKind();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "NO INTERNET ACCESS", Toast.LENGTH_SHORT).show();

        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;

                    case 1:
                        Intent donsac = new Intent(getApplicationContext(), SingleActivity.class);
                        donsac.putExtra("loai", 1);
                        startActivity(donsac);
                        break;
                    case 2:
                        Intent dasac = new Intent(getApplicationContext(), SingleActivity.class);
                        dasac.putExtra("loai", 2);
                        startActivity(dasac);
                        break;
                }

            }
        });
    }

    private void getNewKoi() {
        compositeDisposable.add(apiBanCa.getNewKoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newKoiModel -> {
                            if (newKoiModel.isSuccess()) {
                                newKoiArray = newKoiModel.getResult();
                                koiAdapter = new NewKoiAdapter(getApplicationContext(), newKoiArray);
                                recyclerViewManHinhChinh.setAdapter(koiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối điuợc server  " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void getFishKind() {
        compositeDisposable.add(apiBanCa.getFishKind()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fishKindModel -> {
                            if (fishKindModel.isSuccess()) {
                                fishKindArray = fishKindModel.getResult();
                                Log.d("ALOOOOOOOOO", fishKindArray.get(0).getTenCa());
                                //khởi tạo adapter
                                fishKindAdapter = new FishKindAdapter(getApplicationContext(), fishKindArray);
                                listViewManHinhChinh.setAdapter(fishKindAdapter);
                                //Toast.makeText(getApplicationContext(), fishKindModel.getResult().get(0).getTenCa(), Toast.LENGTH_LONG).show();
                            }
                        }
                ));

    }

    private void ActionViewFlipper() {
        List<String> adsArray = new ArrayList<>();
        adsArray.add("https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-6/463598877_470518639360089_1068075192180422651_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=127cfc&_nc_ohc=X0wIs5u9Jo0Q7kNvgElKLVE&_nc_ht=scontent.fsgn5-5.fna&_nc_gid=A6338upoGv9fg_ez9PME4Q-&oh=00_AYBKTkYdCO9bdPk52NsWEhkqxWi4PCQydHttSxJ4H_qLYQ&oe=671739B4");
        adsArray.add("https://scontent.fsgn5-12.fna.fbcdn.net/v/t39.30808-6/463334637_470518599360093_7669654121727159099_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=127cfc&_nc_ohc=-hYPU_G6gjYQ7kNvgFk4q1d&_nc_ht=scontent.fsgn5-12.fna&_nc_gid=AJBYH9DZMrTo9_Sde587vsp&oh=00_AYDnADvcSaItOVpfwfR8A3MDEgZEBIoRZhdrifP1g9tU7g&oe=67170EA4");
        adsArray.add("https://scontent.fsgn5-14.fna.fbcdn.net/v/t39.30808-6/463455693_470518602693426_8612427031500709045_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=127cfc&_nc_ohc=E0rsPPAJ_i4Q7kNvgFb5wU9&_nc_ht=scontent.fsgn5-14.fna&_nc_gid=Awlv0C5C21_16GAVoIPFJYe&oh=00_AYCgbtUyZ2-3kuImrkAkWBz0aomQntxoAmcO_aMxAgaqpA&oe=671719DC");
        for (int i = 0; i < adsArray.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(adsArray.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                drawerLayout.openDrawer(GravityCompat.START);
//                if (drawerLayout.isDrawerOpen(GravityCompat.START))
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                else
//                    drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void show() {
        toolbar = findViewById(R.id.toolBarManHinhChinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        //giỏ hang
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);

        //khpowir tạo list trước
        fishKindArray = new ArrayList<>();
        newKoiArray = new ArrayList<>();

//khoi tao mang gio hang
        if (Utils.cartArray == null) {
            Utils.cartArray = new ArrayList<>();
        } else {
            int totalItem = 0;
            for (int i = 0; i < Utils.cartArray.size(); i++) {
                totalItem = totalItem + Utils.cartArray.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //đã thêm access trong MANIFEST
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    //resume để hiển thị số lượng giỏ hàng khi thêm trong detail page về homepage
    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.cartArray.size(); i++) {
            totalItem = totalItem + Utils.cartArray.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}