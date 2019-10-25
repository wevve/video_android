package com.jyt.video.service

import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.api.entity.VersionBean
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.login.entity.WxLoginParamResult
import com.jyt.video.main.entity.HomeDialogResult
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.promotion.entity.PromotionUserListResult
import com.jyt.video.welcome.entity.WelcomeResult
import com.ysj.video.wxapi.WeChartHelper
import java.io.File

interface UserService{

    fun wxLogin(user: WeChartHelper.WxUser, pid: String, did: String, callback: ServiceCallback<LoginResult>)

    fun getWxloginParam(callback: ServiceCallback<WxLoginParamResult>)

    fun getVersion(callback: ServiceCallback<VersionBean>)

    fun register(account:String,pwd:String,pwd2: String,pid:String?,did:String, callback: ServiceCallback<Any>)

    fun login(account:String,pwd:String,callback: ServiceCallback<LoginResult>)

    fun getUserHomeInfo(callback: ServiceCallback<PersonHomeResult>)

    fun modifyAvatar(avatar: File,callback: ServiceCallback<Any>)

    fun modifyNickName(name:String,callback: ServiceCallback<Any>)

    fun getPromotionInfo(callback: ServiceCallback<PromotionBean>)

    fun getDaiLiInfo(callback: ServiceCallback<Map<String,String>>)

    fun getXuanChuanInfo(callback: ServiceCallback<Map<String,String>>)

    fun getVipInfo(callback: ServiceCallback<Map<String,String>>)


    fun getWelcomePhoto(callback: ServiceCallback<WelcomeResult>)

    fun getHomeDialogData(callback: ServiceCallback<HomeDialogResult>)

    fun getPromotionUserList(callback: ServiceCallback<PromotionUserListResult>)

    fun modifyPsd(oldPsd:String,newPsd:String,callback: ServiceCallback<Any>)

}