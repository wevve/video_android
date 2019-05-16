package com.jyt.video.api;


import com.jyt.video.common.util.RetrofitManager;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.List;

public class ApiService {

    private static ApiService apiService;
    Retrofit retrofit;
    public Api api;

    private ApiService() {
        retrofit = RetrofitManager.getInstance().getClient();
        api = retrofit.create(Api.class);

    }

    public static ApiService getInstance(){
        if(apiService==null){
            synchronized (ApiService.class) {
                if (apiService == null) {
                    synchronized (ApiService.class) {
                        apiService = new ApiService();
                    }
                }
            }
        }
        return apiService;
    }





}
