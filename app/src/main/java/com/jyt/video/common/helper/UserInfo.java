package com.jyt.video.common.helper;

import com.orhanobut.hawk.Hawk;

public class UserInfo {
    public static String KEY_USER_ID = "KEY_USER_ID";

    public static Long getUserId(){
        return  get(KEY_USER_ID,null);
    }
    public static void setUserId(Long userId){
        add(KEY_USER_ID,userId+"");
    }

    public static boolean isLogin(){
        return getUserId()!=null;
    }


    public static void add(String key,String value){
        Hawk.put(key,value);
    }

    public static  <T> T get(String key,T defaultValue){
        return Hawk.get(key,defaultValue);
    }
}
