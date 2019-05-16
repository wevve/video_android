package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.api.ApiService
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.RxHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService

class UserServiceImpl :UserService{
    override fun login(account: String, pwd: String, callback: ServiceCallback<LoginResult>) {
        ApiService.getInstance().api.login(account, pwd).compose(RxHelper.schedulersTransformer()).subscribe(
            RxHelper.SimpleConsume(callback), RxHelper.ErrorConsume(callback)
        )
    }

    override fun register(account: String, pwd: String,pwd2: String, callback: ServiceCallback<Any>) {
        if (!validate(account,pwd,pwd2)){
            return
        }

        ApiService.getInstance().api.register(account,pwd).compose(RxHelper.schedulersTransformer()).subscribe(
            RxHelper.SimpleConsume(callback),RxHelper.ErrorConsume(callback))
    }


    private fun validate(account:String?,psd1:String?,psd2:String?):Boolean{
        if (account.isNullOrBlank()){
            ToastUtil.showShort(App.app,"请输入账号")
            return false
        }
        if (psd1.isNullOrBlank()||psd2.isNullOrBlank()){
            ToastUtil.showShort(App.app,"请输入密码")
            return false
        }
        if (psd1!=psd2){
            ToastUtil.showShort(App.app,"两次密码不一致")
            return false
        }

            return true
    }


}