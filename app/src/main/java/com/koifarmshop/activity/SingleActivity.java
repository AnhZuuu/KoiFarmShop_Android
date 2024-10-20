package com.koifarmshop.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koifarmshop.adapter.SingleAdapter;
import com.koifarmshop.databinding.ActivitySingleBinding;
import com.koifarmshop.model.NewKoi;
import com.koifarmshop.retrofit.ApiBanCa;
import com.koifarmshop.retrofit.RetrofitClient;
import com.koifarmshop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SingleActivity extends AppCompatActivity {
    private ActivitySingleBinding binding;
    ApiBanCa apiBanCa;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    SingleAdapter adapterSing;
    List<NewKoi> newKoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySingleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiBanCa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanCa.class);
        loai = getIntent().getIntExtra("loai", 1);

        show();
        ActionToolBar();
        getData(page);
//        addEventLoad();
    }

    private void addEventLoad() {
        binding.recycleViewSingle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isLoading) {
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == newKoiList.size() - 1) {
                        isLoading = true;
                        loadMore();
                    }
                }
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                newKoiList.add(null);
                adapterSing.notifyItemInserted(newKoiList.size() -1 );
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remover null
                newKoiList.remove(newKoiList.size() -1);
                adapterSing.notifyItemRemoved(newKoiList.size());
                page = page + 1;
                getData(page);
                adapterSing.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }

    private void getData(int page) {
        compositeDisposable.add(apiBanCa.getSingle(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newKoiModel -> {
                            if (newKoiModel.isSuccess()) {
                                if(adapterSing == null) {
                                    newKoiList = newKoiModel.getResult();
                                    adapterSing = new SingleAdapter(getApplicationContext(), newKoiList);
                                }else {
                                    int vitri = newKoiList.size() -1;
                                    int soLuongAdd = newKoiModel.getResult().size();
                                    for(int i = 0; i < soLuongAdd; i++){
                                        newKoiList.add(newKoiModel.getResult().get(i));
                                    }
                                    adapterSing.notifyItemRangeInserted(vitri, soLuongAdd);
                                }

                                newKoiList = newKoiModel.getResult();
                                adapterSing = new SingleAdapter(getApplicationContext(), newKoiList);
                                binding.recycleViewSingle.setAdapter(adapterSing);
                            }else {
                                Toast.makeText(getApplicationContext(), "Đã hết dữ liệu để load", Toast.LENGTH_LONG).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "không kết lối được server nha ní", Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void show() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recycleViewSingle.setLayoutManager(linearLayoutManager);
        binding.recycleViewSingle.setHasFixedSize(true);
        newKoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}