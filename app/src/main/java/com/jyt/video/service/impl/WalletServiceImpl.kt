package com.jyt.video.service.impl

import com.jyt.video.api.ApiService
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.wallet.entity.WalletIndexInfo

class WalletServiceImpl : WalletService {
    override fun withdraw(accountId: Long, money: String, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.withdraw(UserInfo.getUserId(),accountId,money),callback)

    }

    override fun getMyWalletIndexInfo(callback: ServiceCallback<WalletIndexInfo>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getWalletInfo(UserInfo.getUserId()),callback)
    }

}