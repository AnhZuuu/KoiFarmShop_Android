package com.koifarmshop;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        show();
        ActionBar();
        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        List<String> adsArray = new ArrayList<>();
        adsArray.add("https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-6/463598877_470518639360089_1068075192180422651_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=127cfc&_nc_ohc=X0wIs5u9Jo0Q7kNvgElKLVE&_nc_ht=scontent.fsgn5-5.fna&_nc_gid=A6338upoGv9fg_ez9PME4Q-&oh=00_AYBKTkYdCO9bdPk52NsWEhkqxWi4PCQydHttSxJ4H_qLYQ&oe=671739B4");
        adsArray.add("https://scontent.fsgn5-12.fna.fbcdn.net/v/t39.30808-6/463334637_470518599360093_7669654121727159099_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=127cfc&_nc_ohc=-hYPU_G6gjYQ7kNvgFk4q1d&_nc_ht=scontent.fsgn5-12.fna&_nc_gid=AJBYH9DZMrTo9_Sde587vsp&oh=00_AYDnADvcSaItOVpfwfR8A3MDEgZEBIoRZhdrifP1g9tU7g&oe=67170EA4");
        adsArray.add("https://scontent.fsgn5-14.fna.fbcdn.net/v/t39.30808-6/463455693_470518602693426_8612427031500709045_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=127cfc&_nc_ohc=E0rsPPAJ_i4Q7kNvgFb5wU9&_nc_ht=scontent.fsgn5-14.fna&_nc_gid=Awlv0C5C21_16GAVoIPFJYe&oh=00_AYCgbtUyZ2-3kuImrkAkWBz0aomQntxoAmcO_aMxAgaqpA&oe=671719DC");
        for( int i = 0 ; i <adsArray.size(); i++) {
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void show() {
        toolbar = findViewById(R.id.toolBarManHinhChinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleView);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
    }
}