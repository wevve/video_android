package com.jyt.video.common.helper;

import com.jyt.video.api.entity.PersonHomeResult;
import com.orhanobut.hawk.Hawk;

public class UserInfo {
    public static String KEY_USER_ID = "KEY_USER_ID";
    public static String KEY_USER_INFO = "KEY_USER_INFO";

    public static Long getUserId(){
        return  get(KEY_USER_ID,0L);
    }
    public static void setUserId(Long userId){
        add(KEY_USER_ID,userId);
    }

    public static boolean isLogin(){
        if (getUserId()==null || getUserId()==0L){
            return  false;
        }
        return true;
    }
    public static void logout(){
        setUserId(0L);
    }

    public static void setUserHomeInfo(PersonHomeResult info){
        Hawk.put(KEY_USER_INFO,info);
    }

    public static PersonHomeResult getUserHomeInfo(){
       return Hawk.get(KEY_USER_INFO,null);
    }

    public static  <T>  void add(String key,T value){
        Hawk.put(key,value);
    }

    public static  <T> T get(String key,T defaultValue){
        return Hawk.get(key,defaultValue);
    }
}
