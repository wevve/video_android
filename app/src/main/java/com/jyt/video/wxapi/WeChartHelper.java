package com.jyt.video.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;

/**
 * Created by chenweiqi on 2017/12/12.
 */
/*
    微信 登陆 支付 帮助类

    需要使用登陆或支付功能的activity
    使用步骤：
    1、init
    2、registerToWx
    3、set响应的Listener
    4、login 或 pay

    WxxxxActivity
    1、init
    2、registerToWx
    3、handleIntent
    4、sendXXXXXResultBroadcast

 */
public class WeChartHelper {

    public static final String ACTION_WECHART_RECEIVE = "ACTION_WECHART_RECEIVE";
    public static final String BROADCAST_TYPE = "BROADCAST_TYPE";
    public static final int BROADCAST_TYPE_LOGIN = 0;
    public static final int BROADCAST_TYPE_PAY = 1;


    private IWXAPI api;

    private String APP_ID;
    private Context context;

    ReceiveUserInfoListener receiveUserInfoListener;

    ReceivePayResultListener receivePayResultListener;

    WeReceiver weReceiver;



    public void init( Context context,String APP_ID) {
        this.context = context;
        this.APP_ID = APP_ID;
    }

    //注销receiver
    public void unInit(){
        if (context!=null && weReceiver!=null)
            context.unregisterReceiver(weReceiver);
    }


    //WXActivity 调用
    public void registerToWx(){
        this.api = WXAPIFactory.createWXAPI(context,APP_ID,false);
        api.registerApp(APP_ID);
    }



    public void handleIntent(Intent intent, IWXAPIEventHandler handler){
        api.handleIntent(intent,handler);
    }



    /*
        微信登陆
     */
    public void login(){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    /*
        微信支付
     */
    public void pay(String partnerId, String prepayId, String timeStamp, String nonceStr, String paySign,String ext){
        PayReq request = new PayReq();
        request.appId = APP_ID;
        request.partnerId = partnerId;
        request.prepayId= prepayId;
        request.packageValue = "Sign=WXPay";
        request.nonceStr= nonceStr;
        request.timeStamp= timeStamp;
        request.sign= paySign;
        request.extData = ext;
        api.sendReq(request);
    }


    /*
        发送登陆结果广播

        解析 https://api.weixin.qq.com/sns/userinfo 接口下的返回结果
     */
    public void sendLoginResultBroadcast(Context context,String response){
        WeChartHelper.WxUser user= new Gson().fromJson(response,new TypeToken<WxUser>(){}.getType());
        Intent intent = new Intent();
        intent.putExtra("data", (Parcelable) user);
        intent.putExtra(WeChartHelper.BROADCAST_TYPE, WeChartHelper.BROADCAST_TYPE_LOGIN);
        intent.setAction(WeChartHelper.ACTION_WECHART_RECEIVE);
        context.sendBroadcast(intent);
    }

    /*
        发送支付结果广播


        @Override
        public void onResp(BaseResp resp) {
            weChartHelper.sendPayResultBroadcast(this,resp);
            finish();
        }
     */
    public void sendPayResultBroadcast(Context context, BaseResp resp){
        boolean payResult = false;
        if (resp.errCode == BaseResp.ErrCode.ERR_OK){
            payResult = true;
        }
        String type = ((PayResp) resp).extData;

        Intent intent = new Intent();
        intent.putExtra(WeChartHelper.BROADCAST_TYPE, WeChartHelper.BROADCAST_TYPE_PAY);
        intent.putExtra("data",payResult);
        intent.putExtra("ext",type);
        intent.setAction(WeChartHelper.ACTION_WECHART_RECEIVE);
        context.sendBroadcast(intent);
    }


    //注册receiver
    private void initReceiver(){
        weReceiver = new WeReceiver();
        IntentFilter intentFilter = new IntentFilter(ACTION_WECHART_RECEIVE);
        context.registerReceiver(weReceiver,intentFilter);
    }

    /**
     * 获取用户信息回调（微信登录）
     * @param receiveUserInfoListener
     */
    public void setReceiveUserInfoListener(ReceiveUserInfoListener receiveUserInfoListener) {
        if (weReceiver==null){
            initReceiver();
        }
        this.receiveUserInfoListener = receiveUserInfoListener;
    }

    /**
     * 获取支付回调
     * @param receivePayResultListener
     */
    public void setReceivePayResultListener(ReceivePayResultListener receivePayResultListener) {
        if (weReceiver==null){
            initReceiver();
        }
        this.receivePayResultListener = receivePayResultListener;
    }

    /**
     * 接收回调广播
     */
    class WeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra(BROADCAST_TYPE,-1);
            if ( type == BROADCAST_TYPE_LOGIN){
                if (receiveUserInfoListener!=null) {
                    receiveUserInfoListener.onGotUserInfo((WxUser) intent.getParcelableExtra("data"));
                }
            }else if ( type == BROADCAST_TYPE_PAY){
                if (receivePayResultListener != null){
                    receivePayResultListener.onPayResult(intent.getBooleanExtra("data",false),intent.getStringExtra("ext"));
                }
            }
            abortBroadcast();
        }
    }



    public interface ReceiveUserInfoListener {
        void onGotUserInfo(WxUser user);
    }

    public interface ReceivePayResultListener {
        void onPayResult(boolean payResult,String ext);
    }

    /**
     * 微信登录返回json
     * {
     "openid": "ojqJ70S6dOIOvfCVCFT6nf6oX00M",
     "nickname": "hello_v7",
     "sex": 1,
     "language": "zh_CN",
     "city": "Shenzhen",
     "province": "Guangdong",
     "country": "CN",
     "headimgurl": "http://wx.qlogo.cn/mmopen/vi_32/PvcHsY4ibqKEuZGvU7XYh8WefluOtRh9kb14kdFq7Zuj9SghxVsfAKQoMsYcKOjx0uyWqO0eaZicIZGibeHiaz7eVA/0",
     "privilege": [],
     "unionid": "oguHs1Iv0px2zhpQvi4BBojQJJk8"
     }
     */
    public static class WxUser implements Parcelable,Serializable {

        private String openid;
        private String nickname;
        private int sex;
        private String language;
        private String city;
        private String province;
        private String country;
        private String headimgurl;
        private String unionid;
        //privilege


        protected WxUser(Parcel in) {
            openid = in.readString();
            nickname = in.readString();
            sex = in.readInt();
            language = in.readString();
            city = in.readString();
            province = in.readString();
            country = in.readString();
            headimgurl = in.readString();
            unionid = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(openid);
            dest.writeString(nickname);
            dest.writeInt(sex);
            dest.writeString(language);
            dest.writeString(city);
            dest.writeString(province);
            dest.writeString(country);
            dest.writeString(headimgurl);
            dest.writeString(unionid);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<WxUser> CREATOR = new Creator<WxUser>() {
            @Override
            public WxUser createFromParcel(Parcel in) {
                return new WxUser(in);
            }

            @Override
            public WxUser[] newArray(int size) {
                return new WxUser[size];
            }
        };

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }
    }
}
