package com.jyt.video.login

import android.Manifest
import android.app.Activity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fm.openinstall.OpenInstall
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.DeviceIdUtil
import com.jyt.video.common.util.PermissionUtil
import com.jyt.video.common.util.RxBus
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.event.RefreshEvent
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_register.*
import com.fm.openinstall.model.AppData
import com.fm.openinstall.listener.AppWakeUpAdapter
import android.content.Intent
import com.jyt.video.main.MainActivity
import com.orhanobut.logger.Logger


@Route(path = "/register/index")
class RegisterAct:BaseAct(), View.OnClickListener {


    lateinit var userService:UserService


    override fun onClick(v: View?) {
        when(v){
            btn_register->{

                var account = input_account.text.toString()
                var psd1 = input_psd1.text.toString()
                var psd2 = input_psd2.text.toString()

                RxPermissions(this).request(Manifest.permission.READ_PHONE_STATE).subscribe {
                    if (it){
                        var deviceId = DeviceIdUtil.getDeviceId(this)
                        userService.register(account,psd1,psd2,
                            MainActivity.pid ,deviceId,ServiceCallback { code, data ->
                            if (code==BaseJson.CODE_SUCCESS){

                                OpenInstall.reportRegister();

                                userService.login(account,psd1, ServiceCallback { code, data ->
                                    if (data != null) {
                                        UserInfo.setUserId(data.member_id)
                                        getUserHomeInfo()
                                    }
                                })
                            }
                        })
                    }else{
                        ToastUtil.showShort(this,"请授予权限")
                        PermissionUtil.gotoPermission(this)
                    }
                }

            }
            tv_to_protocol->{

            }
        }
    }

    override fun initView() {
        userService = UserServiceImpl()
        btn_register.setOnClickListener(this)
        tv_to_protocol.setOnClickListener(this)


    }

    override fun getLayoutId(): Int {
        return R.layout.act_register
    }


    private fun getUserHomeInfo(){
        userService.getUserHomeInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                UserInfo.setUserHomeInfo(data)
                RxBus.getInstance().post(RefreshEvent(RefreshEvent.RefreshType.LOGIN))

                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

    }




}
