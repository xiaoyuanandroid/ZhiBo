package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.UpdateStatusBean;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public interface UpdateStatusApi {
    @POST("live/status/update.json")
    Call<UpdateStatusBean> updateStatus(@Body FormBody formBody);
}
