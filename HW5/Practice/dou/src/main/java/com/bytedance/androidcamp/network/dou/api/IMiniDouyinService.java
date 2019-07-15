package com.bytedance.androidcamp.network.dou.api;

import com.bytedance.androidcamp.network.dou.MainActivity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {
    // TODO 7: Define IMiniDouyinService
    String HOST = "http://test.androidcamp.bytedance.com/mini_douyin/invoke/";
    @Multipart
    @POST("video")
    Call<MainActivity.returnsomething> PostVideo(
            @Query("user_name") String userName,
            @Part MultipartBody.Part coverImage,
            @Part MultipartBody.Part video
    );

    @GET("video")
    Call<MainActivity.Feeds> getVideo();
}
