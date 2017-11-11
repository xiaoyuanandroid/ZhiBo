package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.DeleteResponseBean;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface DeleteLiveApi {
    @POST("live/remove.json")
    Call<DeleteResponseBean> deleteLive(@Body FormBody formBody);
}
