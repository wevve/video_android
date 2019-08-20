package com.jyt.video.service

import com.jyt.video.api.entity.WxPayParamResult
import com.jyt.video.api.entity.WxPayResult
import com.jyt.video.recharge.entity.CardInfo
import com.jyt.video.recharge.entity.CreateOrderResult
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.entity.RechargeDataResult
import com.jyt.video.video.entity.Gift
import com.jyt.video.wallet.entity.WalletIndexInfo

interface WalletService{

    fun aliPay(orderSn:String,callback: ServiceCallback<String>)

    fun wxPay(orderSn:String,callback: ServiceCallback<WxPayParamResult>)

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

    fun getPayWayList(callback: ServiceCallback<ArrayList<PayWay>>)

    fun createOrder(payCode:String,price:Double,buyType:Int,packageId:Int?,gold:Double?,callback: ServiceCallback<CreateOrderResult>)

}