package com.jyt.video.common.util;


import com.jyt.video.api.Constans;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitManager {

    private static RetrofitManager retrofitManager;
    private static Retrofit retrofit;
    private RetrofitManager(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();


        retrofit = new Retrofit.Builder()

                .addConverterFactory(GsonConverterFactory.create()) // json解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .baseUrl(Constans.BaseUrl)
                .client(okHttpClient)
                .build();


    }

    public static RetrofitManager getInstance(){
        if (retrofitManager==null){
            synchronized (RetrofitManager.class){
                if (retrofitManager==null) {
                    synchronized (RetrofitManager.class) {
                        retrofitManager = new RetrofitManager();
                    }
                }
            }
        }
        return retrofitManager;
    }


    public Retrofit getClient(){
        return retrofit;
    }
}
