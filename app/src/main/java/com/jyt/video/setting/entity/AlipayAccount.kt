package com.jyt.video.setting.entity

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class AlipayAccount() :WithdrawAccount ,Serializable{


    constructor(id: Long?, alipayAccount: String?):this() {
        this.id = id
        this.alipayAccount = alipayAccount
    }

    override fun getDisplayName(): String {
        return "支付宝(${alipayAccount.toString()})"
    }

    /**
     * id : 55
     * alipayAccount : hedaqiong2
     */

      var id: Long? = null
      var alipayAccount: String? = null




}
