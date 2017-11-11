package com.zxx.diamondlive.network.api;

import com.zxx.diamondlive.bean.UploadBean;
import com.zxx.diamondlive.config.UrlConfig;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public interface UploadApi  {
    @Multipart
    @POST(UrlConfig.UTIL_FILE_API)
    Call<UploadBean>upload(@Part MultipartBody.Part body);
}
