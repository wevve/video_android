package com.jyt.video.login

import android.app.Activity
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jyt.video.App
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.AppUtils
import com.jyt.video.common.util.RxBus
import com.jyt.video.event.RefreshEvent
import com.jyt.video.login.entity.WxParam
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.UserServiceImpl
import com.jyt.video.wxapi.WeChartHelper
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.act_login.*

@Route(path = "/login/index")
class LoginAct:BaseAct(), View.OnClickListener {

    lateinit var userService:UserServiceImpl

    var wxhelper = WeChartHelper()
    override fun initView() {
        userService = UserServiceImpl()

//        Glide.with(this).load(R.mipmap.logo).into(logo)

        logo.setImageBitmap(AppUtils.getBitmap(this))



        btn_login.setOnClickListener (this)
        btn_to_register.setOnClickListener (this)
        wxlogin.setOnClickListener(this)

        userService.getWxloginParam(
            ServiceCallback { code, data ->
                if (data != null) {
                    if (data.status == 1) {
                        thrid_login.visibility = View.VISIBLE

                        var bean = data.list.firstOrNull()

                        if (bean != null) {
                            var nc = bean.config.replace("\\\"", "\"")
                            if (bean.login_code == "wechat") {

                                var ls = Gson().fromJson<ArrayList<WxParam>>(nc,
                                    object : TypeToken<ArrayList<WxParam>>() {}.type)
                                ls.forEach {
                                    if (it.name == "Appid") {
                                        App.wxloginKey = it.value
                                    } else
                                        if (it.name == "AppSecret") {
                                            App.wxloginAppSecret = it.value
                                        }
                                }
                                wxhelper.init(this, App.wxloginKey)
                                wxhelper.registerToWx()
                                wxhelper.setReceiveUserInfoListener {
                                    Logger.d(it)
                                    userService.wxLogin(it, ServiceCallback { code, data ->
                                        if (data != null) {
                                            UserInfo.setUserId(data.member_id)
                                            getUserHomeInfo()

                                        }
                                    })
                                }

                                wxlogin.visibility = View.VISIBLE
                            } else {
                                wxlogin.visibility = View.GONE
                            }

                        }
                    } else {
                        thrid_login.visibility = View.GONE
                    }
                } else {
                    thrid_login.visibility = View.GONE

                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        wxhelper.unInit()
    }

    override fun onClick(v: View?) {
        when(v){
            wxlogin -> {
                wxhelper.login()
            }
            btn_login->{
                var account = input_account.text.toString()
                var pwd = input_psd.text.toString()
                userService.login(account,pwd, ServiceCallback{
                    code, data ->
                    if (data!=null) {
                        UserInfo.setUserId(data.member_id)
                        getUserHomeInfo()

                    }
                })
            }
            btn_to_register->{
                ARouter.getInstance().build("/register/index").navigation(this,1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1&& resultCode==Activity.RESULT_OK){
            finish()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.act_login
    }


    private fun getUserHomeInfo(){
        userService.getUserHomeInfo(ServiceCallback{
            code, data ->
                if (data!=null) {
                    UserInfo.setUserHomeInfo(data)
                    RxBus.getInstance().post(RefreshEvent(RefreshEvent.RefreshType.LOGIN))
                    finish()
                }
        })
    }
}