package com.jyt.video.wallet

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.service.impl.UserServiceImpl
import com.jyt.video.service.impl.WalletServiceImpl
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount
import com.jyt.video.wallet.helper.WithdrawHelper
import kotlinx.android.synthetic.main.act_my_wallet.*

@Route(path = "/wallet/index")
class MyWalletAct:BaseAct(), View.OnClickListener {
    lateinit var walletService: WalletService
    lateinit var accountService:AccountService

    var bankLData:ArrayList<BankCardAccount> = ArrayList()
    var aliPayData:ArrayList<AlipayAccount> = ArrayList()


    override fun onClick(v: View?) {
        when(v){
            img_width_draw->{
                if (bankLData.isEmpty()&& aliPayData.isEmpty()){
                    var dialog = AlertDialog()
                    dialog.message = "提现需要填写收款账户"
                    dialog.leftBtnText = "填写"
                    dialog.onClickListener={
                            dialogFragment, s ->
                        ARouter.getInstance().build("/setting/account/cs").navigation()
                    }
                    dialog.show(supportFragmentManager,"")
                }else {
                    ARouter.getInstance().build("/wallet/widthdraw").navigation()
                }
            }
            img_deal_record->{
                ARouter.getInstance().build("/deal/record").navigation()
            }
            btn_recharge->{
                ARouter.getInstance().build("").navigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getBankCardList()
        getALiPayAccountList()
    }

    override fun initView() {
        walletService = WalletServiceImpl()
        accountService = AccountServiceImpl()
        img_width_draw.setOnClickListener(this)
        img_deal_record.setOnClickListener(this)
        btn_recharge.setOnClickListener(this)

        getData()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_my_wallet
    }


    fun getData(){
        walletService.getMyWalletIndexInfo(ServiceCallback{
            code, data ->
            if (data!=null){
                tv_balance.text = data.money
                tv_coin.text = data.corn

                WithdrawHelper.balance = data.money?:"0"

            }
        })
    }

    fun getBankCardList(){
        accountService.getBankCardList(ServiceCallback{
                code, data ->
            bankLData.clear()
            if (data!=null){
                bankLData.addAll(data)
            }
        })
    }
    fun getALiPayAccountList(){
        accountService.getALiPayList(ServiceCallback{
                code, data ->
            aliPayData.clear()
            if (data!=null){
                aliPayData.addAll(data)
            }
        })
    }
}