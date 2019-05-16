package com.jyt.video.service.impl

import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount

class AccountServiceImpl:AccountService{
    override fun deleteBankCardAccount(account: BankCardAccount, callback: ServiceCallback<BankCardAccount>) {
        callback.result(200,account)
    }

    override fun addALiPayAccount(account: AlipayAccount, callback: ServiceCallback<AlipayAccount>) {
    }


    override fun deleteALiPayAccount(account: AlipayAccount, callback: ServiceCallback<AlipayAccount>) {
        callback.result?.invoke(200,account)
    }

    override fun getALiPayList(page: Int, callback: ServiceCallback<List<AlipayAccount>>) {
        var data = List<AlipayAccount>(10) {
                index ->
            var account = AlipayAccount()
            account.id = index.toLong()
            account.alipayAccount = "123$index"
                account
        }
        callback.result.invoke(200,data)
    }

    override fun getBankCardList(page: Int, callback: ServiceCallback<List<BankCardAccount>>) {
        var data = List<BankCardAccount>(10) {
                index ->
            BankCardAccount()
        }
        callback.result.invoke(200,data)
    }
}