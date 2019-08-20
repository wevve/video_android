package com.jyt.video.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jyt.video.App;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 需在android manifest 上配置 android:exported="true"
 * 处理微信支付
 * Created by chenweiqi on 2017/12/12.
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    WeChartHelper weChartHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weChartHelper = new WeChartHelper();
        weChartHelper.init(this, App.Companion.getWxpayAppid());
        weChartHelper.registerToWx();
        weChartHelper.handleIntent(getIntent(),this);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        weChartHelper.sendPayResultBroadcast(this,resp);
        finish();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weChartHelper.handleIntent(getIntent(),this);

    }
}
