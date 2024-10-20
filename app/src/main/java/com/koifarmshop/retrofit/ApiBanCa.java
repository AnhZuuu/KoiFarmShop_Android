package com.koifarmshop.retrofit;

import io.reactivex.rxjava3.core.Observable;
import com.koifarmshop.model.FishKindModel;
import com.koifarmshop.model.NewKoiModel;
import com.koifarmshop.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanCa {
    @GET("getLoaiCa.php")
    Observable<FishKindModel> getFishKind();

    @GET("getNewKoi.php")
    Observable<NewKoiModel> getNewKoi();

    @POST("detail.php")
    @FormUrlEncoded
    Observable<NewKoiModel> getSingle(
            @Field("page") int page,
            @Field("loai") int loai
    );
    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("phone") String phone
    );


}
