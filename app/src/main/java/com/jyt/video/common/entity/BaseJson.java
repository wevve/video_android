package com.jyt.video.common.entity;

import com.google.gson.annotations.SerializedName;

public class BaseJson<T> {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    private int Code;
    private String Msg;

    @SerializedName(value = "Data",alternate = "data")
    private T Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
