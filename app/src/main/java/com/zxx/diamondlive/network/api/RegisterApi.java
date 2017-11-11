package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.RegReposeBean;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public interface RegisterApi {
    @POST("live/register.json")
    Call<RegReposeBean> register(@Body FormBody formBody);
}
