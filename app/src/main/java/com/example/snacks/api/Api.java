package com.example.snacks.api;

import com.example.snacks.base.BaseGson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {
    @FormUrlEncoded
    @POST("snackshop/public/index.php/api/snack/sendsmscode")
    Observable<BaseGson<String>> sendSmsCode(@Field("tel") String tel, @Field("code") String code);
}
