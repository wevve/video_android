package com.jyt.video.service

import com.jyt.video.recharge.entity.CardInfo
import com.jyt.video.recharge.entity.RechargeDataResult
import com.jyt.video.video.entity.Gift
import com.jyt.video.wallet.entity.WalletIndexInfo

interface WalletService{
    fun getMyWalletIndexInfo(callback: ServiceCallback<WalletIndexInfo>)

    fun withdraw(accountId:Long,money:String,callback: ServiceCallback<Any>)


    fun checkCardInfo(cardNum:String,callback: ServiceCallback<CardInfo>)

    fun rechargeCard(cardNum:String,callback: ServiceCallback<Any>)

    fun getBuyCardUrl(callback: ServiceCallback<String>)

    //充值数据
    fun getRechargeInfo(callback: ServiceCallback<RechargeDataResult>)

//    fun getPayWay()

    fun getGiftList(callback: ServiceCallback<ArrayList<Gift>>)

    fun sendGift(videoId:Long,giftId:Long,callback: ServiceCallback<Any>)

}