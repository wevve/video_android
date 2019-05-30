package com.jyt.video.service.impl

import com.jyt.video.api.Api
import com.jyt.video.api.ApiService
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount

class AccountServiceImpl:AccountService{


    override fun addBankCard(account: BankCardAccount, callback: ServiceCallback<BankCardAccount>) {
        var map = HashMap<String,String>()
        map.put("userId",UserInfo.getUserId().toString())
        map.put("bankName",account.bankName)
        map.put("cardUser",account.cardUser)
        map.put("cardNum",account.cardNum)
        if (account.carId!=null) {
            map.put("cardId", account.carId.toString())
            map.put("type","2")
        }else{
            map.put("type","1")

        }
        RxHelper.simpleResult(ApiService.getInstance().api.saveBankCard(map),callback)

    }

    override fun deleteBankCardAccount(account: BankCardAccount, callback: ServiceCallback<BankCardAccount>) {
        RxHelper.simpleResult(ApiService.getInstance().api.delBankCard(UserInfo.getUserId(),account.carId,2),callback)
    }

    override fun addALiPayAccount(account: AlipayAccount, callback: ServiceCallback<AlipayAccount>) {


        var map = HashMap<String,String>()
        map.put("userId",UserInfo.getUserId().toString())
        map.put("alipayAccount",account.alipayAccount.toString())
        if(account.id!=null) {
            map.put("alipayId", account.id.toString())
            map.put("type","4")
        }else{
            map.put("type","3")

        }
        RxHelper.simpleResult(ApiService.getInstance().api.saveAlipayAccount(map),callback)


    }


    override fun deleteALiPayAccount(account: AlipayAccount, callback: ServiceCallback<AlipayAccount>) {

        RxHelper.simpleResult(ApiService.getInstance().api.delALiAccount(UserInfo.getUserId(),account.id,1),callback)

    }

    override fun getALiPayList(callback: ServiceCallback<List<AlipayAccount>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getALiPayAccountList(UserInfo.getUserId()),callback)

    }

    override fun getBankCardList( callback: ServiceCallback<List<BankCardAccount>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getBankCardList(UserInfo.getUserId()),callback)
    }
}