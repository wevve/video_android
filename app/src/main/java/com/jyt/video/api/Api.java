package com.jyt.video.api;


import com.jyt.video.common.entity.BaseJson;
import com.jyt.video.home.entity.HomeResult;
import com.jyt.video.home.entity.HomeTabResult;
import com.jyt.video.home.entity.TabEntity;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.List;

public interface Api {



    //注册
    @POST("/appapi/register.html")
    @FormUrlEncoded
    public Observable<BaseJson> register(@Field("account")String account,@Field("pwd")String password);


    //登录
    @POST("/appapi/login.html")
    @FormUrlEncoded
    public Observable<BaseJson> login(@Field("account")String account,@Field("pwd")String password);

    //首页顶部分栏
    @GET("/appapi/homeTab")
    Observable<BaseJson<HomeTabResult>> getHomeTab();

    @GET("/appapi/homeMain")
    Observable<BaseJson<HomeResult>> getHomeData();

}
