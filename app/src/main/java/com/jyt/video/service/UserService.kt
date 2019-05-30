package com.jyt.video.service

import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.login.entity.LoginResult
import com.jyt.video.wallet.entity.WalletIndexInfo
import java.io.File

interface UserService{
    fun register(account:String,pwd:String,pwd2: String, callback: ServiceCallback<Any>)

    fun login(account:String,pwd:String,callback: ServiceCallback<LoginResult>)

    fun getUserHomeInfo(callback: ServiceCallback<PersonHomeResult>)

    fun modifyAvatar(avatar: File,callback: ServiceCallback<Any>)

    fun modifyNickName(name:String,callback: ServiceCallback<Any>)

}