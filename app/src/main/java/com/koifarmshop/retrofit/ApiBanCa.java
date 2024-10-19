package com.koifarmshop.retrofit;

import io.reactivex.rxjava3.core.Observable;
import com.koifarmshop.model.FishKindModel;
import com.koifarmshop.model.NewKoiModel;

import retrofit2.http.GET;

public interface ApiBanCa {
    @GET("getLoaiCa.php")
    Observable<FishKindModel> getFishKind();

    @GET("getNewKoi.php")
    Observable<NewKoiModel> getNewKoi();


}
