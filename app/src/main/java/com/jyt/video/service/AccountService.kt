package com.jyt.video.service

import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount

interface AccountService {
    fun getBankCardList(page:Int,callback: ServiceCallback<List<BankCardAccount>>)

    fun getALiPayList(page: Int,callback: ServiceCallback<List<AlipayAccount>>)

    fun deleteALiPayAccount(account:AlipayAccount,callback: ServiceCallback<AlipayAccount>)

    fun deleteBankCardAccount(account:BankCardAccount,callback: ServiceCallback<BankCardAccount>)

    fun addALiPayAccount(account:AlipayAccount,callback: ServiceCallback<AlipayAccount>)
}