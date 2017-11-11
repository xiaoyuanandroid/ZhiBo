package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.LoginReposeBean;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public interface LoginApi {
    @POST("live/login.json")
    Call<LoginReposeBean> login(@Body FormBody formBody);
}
