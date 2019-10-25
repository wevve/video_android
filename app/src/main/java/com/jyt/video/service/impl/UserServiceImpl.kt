package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.api.ApiService
import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.api.entity.VersionBean
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.DeviceIdUtil
import com.jyt.video.common.util.RxHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.login.entity.WxLoginParamResult
import com.jyt.video.main.MainActivity
import com.jyt.video.main.entity.HomeDialogResult
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.promotion.entity.PromotionUserListResult
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.welcome.entity.WelcomeResult
import com.ysj.video.wxapi.WeChartHelper
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder

class UserServiceImpl :UserService{
    override fun wxLogin(user: WeChartHelper.WxUser, pid:String, did:String, callback: ServiceCallback<LoginResult>) {


        var map = mapOf(Pair("nickname",user.nickname)
            ,Pair("sex",user.sex)
            ,Pair("openid",user.openid)
        ,Pair("headimgurl",user.headimgurl)
        ,Pair("unionid",user.unionid)
        ,Pair("pid",pid)
        ,Pair("did",did)
        )

        RxHelper.simpleResult(ApiService.getInstance().api.wxLogin(map),callback)

    }

    override fun getWxloginParam(callback: ServiceCallback<WxLoginParamResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.wxThirdLoginParam(),callback)
    }

    override fun getVersion(callback: ServiceCallback<VersionBean>) {

        var deviceId = DeviceIdUtil.getDeviceId(App.app)
        Logger.d(deviceId)
        var pid = if (MainActivity.pid.isNullOrBlank()){
            "0"
        }else{
            MainActivity.pid
        }
        RxHelper.simpleResult(ApiService.getInstance().api.getVersion(pid,deviceId),callback)
    }

    override fun modifyPsd(oldPsd: String, newPsd: String, callback: ServiceCallback<Any>) {

        var map = mapOf(Pair("oldPwd",oldPsd),Pair("newPwd",newPsd),Pair("confirm",newPsd),Pair("userId",UserInfo.getUserId()))
        RxHelper.simpleResult(ApiService.getInstance().api.modifyPsd(map as Map<String, String>?),callback)

    }


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

    override fun register(account: String, pwd: String,pwd2: String,pid:String?,did:String, callback: ServiceCallback<Any>) {
        if (!validate(account,pwd,pwd2)){
            return
        }
        var map = hashMapOf(Pair("account",account)
        , Pair("pwd",pwd2)
        , Pair("did",did))
        if(!pid.isNullOrBlank()){
            map.put("pid",pid)
        }

        RxHelper.simpleResult(ApiService.getInstance().api.register(map),callback)
    }


    private fun validate(account:String?,psd1:String?,psd2:String?):Boolean{
        if (account.isNullOrBlank()){
            ToastUtil.showShort(App.app,"请输入登录账号")
            return false
        }
        if (psd1.isNullOrBlank()){
            ToastUtil.showShort(App.app,"请输入登录密码")
            return false
        }
        if (psd2.isNullOrBlank()){
            ToastUtil.showShort(App.app,"请再次输入密码")
            return false
        }
        if (psd1!=psd2){
            ToastUtil.showShort(App.app,"两次输入的密码不一致")
            return false
        }

            return true
    }


}