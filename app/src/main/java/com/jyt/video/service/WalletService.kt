package com.jyt.video.service

import com.jyt.video.wallet.entity.WalletIndexInfo

interface WalletService{
    fun getMyWalletIndexInfo(callback: ServiceCallback<WalletIndexInfo>)

    fun withdraw(accountId:Long,money:String,callback: ServiceCallback<Any>)

}