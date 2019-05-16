package com.jyt.video.service

import com.jyt.video.common.entity.BaseJson
import com.jyt.video.login.entity.LoginResult

interface UserService{
    fun register(account:String,pwd:String,pwd2: String, callback: ServiceCallback<Any>)

    fun login(account:String,pwd:String,callback: ServiceCallback<LoginResult>)
}