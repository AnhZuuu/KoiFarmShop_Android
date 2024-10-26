package com.koifarmshop.activity;

public interface ResponseCallback {

    void onResponse(String response);

    void onError(Throwable throwable);
}
