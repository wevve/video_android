package com.jyt.video.service.impl

import com.jyt.video.api.ApiService
import com.jyt.video.api.entity.WxPayParamResult
import com.jyt.video.api.entity.WxPayResult
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.recharge.entity.CardInfo
import com.jyt.video.recharge.entity.CreateOrderResult
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.entity.RechargeDataResult
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.video.entity.Gift
import com.jyt.video.wallet.entity.WalletIndexInfo

class WalletServiceImpl : WalletService {
    override fun aliPay(orderSn: String, callback: ServiceCallback<String>) {
        RxHelper.simpleResult(ApiService.getInstance().api.aliPayParam(orderSn),callback)
    }

    override fun wxPay(orderSn: String, callback: ServiceCallback<WxPayParamResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.wxPayParam(orderSn),callback)
    }

    override fun createOrder(
        payCode: String,
        price: Double,
        buyType: Int,
        packageId: Int?,
        gold: Double?,
        videoId:String?,
        callback: ServiceCallback<CreateOrderResult>
    ) {
        var map = HashMap<String,Any>()
        map.put("userId",UserInfo.getUserId())
        map.put("payCode",payCode)
        map.put("price",price)
        map.put("buyType",buyType)
        if (buyType==1){
            map.put("gold",gold!!)
        }else if(buyType==2){
            map.put("packageId",packageId!!)
        }
        if (!videoId.isNullOrBlank()){
            map.put("videoId",videoId)
        }
        RxHelper.simpleResult(ApiService.getInstance().api.createOrder(map),callback)

    }

    override fun getPayWayList(callback: ServiceCallback<ArrayList<PayWay>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.payWayList(),callback)
    }




    override fun sendGift(videoId: Long, giftId: Long, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.sendGiftToVideo(videoId,giftId,UserInfo.getUserId()),callback)
    }

    override fun getGiftList(callback: ServiceCallback<ArrayList<Gift>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.giftList,callback)
    }

    override fun getRechargeInfo(callback: ServiceCallback<RechargeDataResult>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getRechargeData(),callback)
    }

    override fun getBuyCardUrl(callback: ServiceCallback<String>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getBuyCardUrl(),callback)
    }

    override fun checkCardInfo(cardNum: String, callback: ServiceCallback<CardInfo>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getChargeCardInfo(cardNum),callback)
    }

    override fun rechargeCard(cardNum: String, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.useChargeCard(cardNum,UserInfo.getUserId()),callback)

    }

    override fun withdraw(accountId: Long, money: String, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.withdraw(UserInfo.getUserId(),accountId,money),callback)

    }

    override fun getMyWalletIndexInfo(callback: ServiceCallback<WalletIndexInfo>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getWalletInfo(UserInfo.getUserId()),callback)
    }

}