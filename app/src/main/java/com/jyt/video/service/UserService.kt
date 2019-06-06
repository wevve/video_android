package com.jyt.video.service

import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.main.entity.HomeDialogResult
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.promotion.entity.PromotionUserListResult
import com.jyt.video.wallet.entity.WalletIndexInfo
import com.jyt.video.welcome.entity.WelcomeResult
import java.io.File

interface UserService{
    fun register(account:String,pwd:String,pwd2: String, callback: ServiceCallback<Any>)

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

}