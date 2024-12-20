package com.koifarmshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koifarmshop.R;
import com.koifarmshop.databinding.ActivityRegisterBinding;
import com.koifarmshop.retrofit.ApiBanCa;
import com.koifarmshop.retrofit.RetrofitClient;
import com.koifarmshop.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    ApiBanCa apiBanCa;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initControl();
    }

    private void initControl() {
        binding.btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String str_email = binding.email.getText().toString().trim();
        String str_password = binding.password.getText().toString().trim();
        String str_repassword = binding.repassword.getText().toString().trim();
        String str_phone = binding.phone.getText().toString().trim();
        String str_name = binding.name.getText().toString().trim();

        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_name)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_password)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_repassword)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa xác nhận lại Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_phone)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Số điện thoại", Toast.LENGTH_SHORT).show();
        } else {
            if (str_password.equals(str_repassword)) {
            //post data
                compositeDisposable.add(apiBanCa.dangky(str_email, str_password, str_name, str_phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()) {
                                        Utils.user_current.setEmail(str_email);
                                        Utils.user_current.setPassword(str_password);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                        ));
            } else {
                Toast.makeText(getApplicationContext(), "Password không khớp", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void initView() {
        apiBanCa = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanCa.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}