package com.jyt.video.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.jyt.video.App;
import com.jyt.video.common.util.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 需在android manifest 上配置 android:exported="true"
 *
 * 处理 微信登录
 * Created by chenweiqi on 2017/12/12.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    WeChartHelper weChartHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weChartHelper = new WeChartHelper();
        weChartHelper.init(this, App.Companion.getWxloginKey());
        weChartHelper.registerToWx();
        weChartHelper.handleIntent(getIntent(),this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weChartHelper.handleIntent(getIntent(),this);

    }

    // 微信发送请求到第三方应用时，会回调到该方法

    @Override
    public void onReq(BaseReq baseReq) {

    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i("WXTest","onResp OK");

                if(resp instanceof SendAuth.Resp){
//                    if (resp.getType()== ConstantsAPI.COMMAND_SENDAUTH){
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                    getToken(code);
                    Log.i("WXTest","onResp code = "+code);
//                    }else {
//                        finish();
//                    }
                }else {
                    finish();
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i("WXTest","onResp ERR_USER_CANCEL ");
                //发送取消
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i("WXTest","onResp ERR_AUTH_DENIED");
                //发送被拒绝
                finish();
                break;
            default:
                Log.i("WXTest","onResp default errCode " + resp.errCode);
                finish();
                //发送返回
                break;
        }
    }

    //这个方法会取得accesstoken  和openID
    private void getToken(String code){
        OkHttpUtils.get().url("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ App.Companion.getWxloginKey()+"&secret="+ App.Companion.getWxloginAppSecret()+"&code="+code+"&grant_type=authorization_code")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String openid = jsonObject.getString("openid");
                    String access_token = jsonObject.getString("access_token");

                    getUserInfo(access_token,openid);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void  getUserInfo(String access_token, final String openId){
        OkHttpUtils.get().url("https://api.weixin.qq.com/sns/userinfo?access_token=" +access_token+"&openid=" +openId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showShort(WXEntryActivity.this,e.getMessage());
                finish();
            }

            @Override
            public void onResponse(String response, int id) {

                weChartHelper.sendLoginResultBroadcast(WXEntryActivity.this,response);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weChartHelper.unInit();
    }
}