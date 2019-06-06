package com.jyt.video.service.impl

import com.jyt.video.api.ApiService
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.recharge.entity.CardInfo
import com.jyt.video.recharge.entity.RechargeDataResult
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.video.entity.Gift
import com.jyt.video.wallet.entity.WalletIndexInfo

class WalletServiceImpl : WalletService {
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