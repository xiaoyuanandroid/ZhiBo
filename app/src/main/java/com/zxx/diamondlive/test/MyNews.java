package com.zxx.diamondlive.test;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public interface MyNews {
    @GET("index")
    Call<TopNews> getNews(@Query("type") String type,@Query("key") String key);

    @GET("index/{id}")
    Call<TopNews> getNews2(@Path("id") String id);

    @POST("index")
    Call<TopNews> postNews(@Body FormBody formBody);
}
