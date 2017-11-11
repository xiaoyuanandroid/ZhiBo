package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.MyLiveReposeBean;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface MyListApi {
    @POST("live/my/list.json")
    Call<MyLiveReposeBean> getMyList (@Body FormBody formBody);
}
