package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.Live;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public interface LiveApi  {
    @POST("live/find.json")
    Call<Live> postLive(@Body FormBody formBody);
}
