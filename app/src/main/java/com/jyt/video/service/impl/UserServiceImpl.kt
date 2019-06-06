package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.api.Api
import com.jyt.video.api.ApiService
import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.main.entity.HomeDialogResult
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.promotion.entity.PromotionUserListResult
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.wallet.entity.WalletIndexInfo
import com.jyt.video.welcome.entity.WelcomeResult
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder

class UserServiceImpl :UserService{
    override fun getPromotionUserList(callback: ServiceCallback<PromotionUserListResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getPromotionUserList(UserInfo.getUserId()),callback)

    }

    override fun getWelcomePhoto(callback: ServiceCallback<WelcomeResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.welcomePhoto,callback)

    }

    override fun getHomeDialogData(callback: ServiceCallback<HomeDialogResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.homeDialog,callback)

    }

    override fun getVipInfo(callback: ServiceCallback<Map<String, String>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.vipUrl,callback)
    }

    override fun getXuanChuanInfo(callback: ServiceCallback<Map<String, String>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getXuanChuanData(UserInfo.getUserId()),callback)

    }

    override fun getDaiLiInfo(callback: ServiceCallback<Map<String, String>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getDailiData(UserInfo.getUserId()),callback)

    }

    override fun getPromotionInfo(callback: ServiceCallback<PromotionBean>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getPromotionInfo(UserInfo.getUserId()),callback)
    }

    override fun modifyAvatar(avatar: File, callback: ServiceCallback<Any>) {

        var pm = HashMap<String,RequestBody>()

        var fileType =  RequestBody.create(
        MediaType.parse("multipart/form-data"),"image")
        pm.put("fileType",fileType)

        var requestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),avatar)
        var fileName = avatar.name
        var filesp = fileName.split(".")
        var suffix = filesp[1]
        val body = MultipartBody.Part.createFormData("fileName", URLEncoder.encode(filesp[0],"utf-8") +"."+ suffix, requestBody)

        ApiService.getInstance().api.uploadFile(pm,body).compose(RxHelper.schedulersTransformer())
            .subscribe({
                baseJson ->
                var baseJson = baseJson as BaseJson<String>?
                if (baseJson?.code==BaseJson.CODE_SUCCESS){
                    RxHelper.simpleResult(ApiService.getInstance().api.modifyUserInfo(baseJson.data,UserInfo.getUserId(),0),callback)
                }else{
                    ToastUtil.showShort(App.app,"头像上传失败")
                }
            },{
                it.printStackTrace()
                ToastUtil.showShort(App.app,"头像上传失败")
            })

    }

    override fun modifyNickName(name: String, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.modifyUserInfo(name,UserInfo.getUserId(),1),callback)
    }

    override fun getUserHomeInfo(callback: ServiceCallback<PersonHomeResult>) {
        ApiService.getInstance().api.getUserInfo(UserInfo.getUserId().toString()).compose(RxHelper.schedulersTransformer()).subscribe(
            RxHelper.SimpleConsume(callback), RxHelper.ErrorConsume(callback))
    }

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