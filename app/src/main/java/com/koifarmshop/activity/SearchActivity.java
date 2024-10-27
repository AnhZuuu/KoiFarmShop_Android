package com.koifarmshop.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.koifarmshop.R;
import com.koifarmshop.adapter.SingleAdapter;
import com.koifarmshop.databinding.ActivitySearchBinding;
import com.koifarmshop.model.NewKoi;
import com.koifarmshop.model.NewKoiModel;
import com.koifarmshop.retrofit.ApiBanCa;
import com.koifarmshop.retrofit.RetrofitClient;
import com.koifarmshop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    SingleAdapter singleAdapter;
    List<NewKoi> newKoiList;
    ApiBanCa apiBanCa;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        ActionToolBar();
    }

    private void initView() {
        newKoiList = new ArrayList<>();
        apiBanCa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanCa.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleViewSearch.setHasFixedSize(true);
        binding.recycleViewSearch.setLayoutManager(layoutManager);
        binding.edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    newKoiList.clear();
                    singleAdapter = new SingleAdapter(getApplicationContext(), newKoiList);
                    binding.recycleViewSearch.setAdapter(singleAdapter);
                } else {
                    getDataSearch(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getDataSearch(String s) {
        newKoiList.clear();
        compositeDisposable.add(apiBanCa.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newKoiModel -> {
                            if (newKoiModel.isSuccess()) {
                                newKoiList = newKoiModel.getResult();
                                singleAdapter = new SingleAdapter(getApplicationContext(), newKoiList);
                                binding.recycleViewSearch.setAdapter(singleAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}