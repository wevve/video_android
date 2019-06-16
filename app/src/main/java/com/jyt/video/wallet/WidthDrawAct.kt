package com.jyt.video.wallet

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.service.impl.WalletServiceImpl
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount
import com.jyt.video.setting.entity.WithdrawAccount
import com.jyt.video.wallet.dialog.ChooseWidthdrawWayDialog
import com.jyt.video.wallet.dialog.WidthdrawResultDialog
import com.jyt.video.wallet.helper.WithdrawHelper
import kotlinx.android.synthetic.main.act_width_draw.*


@Route(path = "/wallet/widthdraw")
class WidthDrawAct :BaseAct(), View.OnClickListener {

    lateinit var accountService: AccountService
    lateinit var walletService: WalletService

    var chooseWidthdrawWayDialog: ChooseWidthdrawWayDialog? = null
    var widthdrawResultDialog: WidthdrawResultDialog?=null

    var bankLData:ArrayList<BankCardAccount> = ArrayList()
    var aliPayData:ArrayList<AlipayAccount> = ArrayList()


    var curSelAccount:Any? = null
    override fun onClick(v: View?) {
        when(v){
            btn_new_account->{
                ARouter.getInstance().build("/setting/account/cs").navigation()
            }
            tv_bank_card,tv_to_bank_card_label->{
                var accountList = ArrayList<WithdrawAccount>()
                accountList.addAll(aliPayData)
                accountList.addAll(bankLData)
                chooseWidthdrawWayDialog?.account = accountList
                chooseWidthdrawWayDialog?.show(supportFragmentManager,"")
            }
            btn_width_draw->{

                if (curSelAccount==null){
                    ToastUtil.showShort(this,"请选择提现的账号")
                    return
                }
                var money = input_money.text.toString()
                if(money.isNullOrEmpty() || money.toDouble()==0.toDouble()){
                    ToastUtil.showShort(this,"请输入有效金额")
                    return
                }
                var acId: Long? = if (curSelAccount is AlipayAccount){
                    (curSelAccount as AlipayAccount).id
                }else {
                     (curSelAccount as BankCardAccount).carId
                }

                walletService.withdraw(acId?:0,money,ServiceCallback{
                    code, data ->
                    if (code==BaseJson.CODE_SUCCESS){
                        getData()
                        widthdrawResultDialog?.money = money
                        widthdrawResultDialog?.show(supportFragmentManager,"")
                    }
                })
            }
            tv_all_width_draw->{
                input_money.setText( WithdrawHelper.balance)
            }
        }
    }

    override fun onResume() {
        super.onResume()


        getBankCardList()
        getALiPayAccountList()
    }

    override fun getLayoutId():Int {
        return R.layout.act_width_draw
    }

    override fun initView() {
        accountService = AccountServiceImpl()
        walletService = WalletServiceImpl()
        chooseWidthdrawWayDialog = ChooseWidthdrawWayDialog()
        chooseWidthdrawWayDialog?.itemClickListener={
            data->
            curSelAccount = data
            when(data){
                is AlipayAccount->{
                    tv_to_bank_card_label.text = "到账支付宝"
                    tv_bank_card.text ="支付宝（${data.alipayAccount})"
                }
                is BankCardAccount->{
                    tv_to_bank_card_label.text = "到账银行卡"
                    tv_bank_card.text = "${data.bankName}(${data.cardNum})"
                }
            }
        }
        widthdrawResultDialog = WidthdrawResultDialog()
        widthdrawResultDialog?.isCancelable = false


        btn_new_account.setOnClickListener(this)
        tv_to_bank_card_label.setOnClickListener(this)
        tv_bank_card.setOnClickListener(this)
        btn_width_draw.setOnClickListener(this)
        tv_all_width_draw.setOnClickListener(this)
        input_money.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    var input = s.toString().toDouble()
                    var max = WithdrawHelper.balance.toDouble()
                    if(input>max){
                        input_money.setText(max.toString())
                    }
                }else{
                    input_money.setText("0")
                }
            }
        })

        tv_total_balance.text = "零钱余额${WithdrawHelper.balance}"
    }


    private fun getBankCardList(){
        accountService.getBankCardList(ServiceCallback{
            code, data ->
            bankLData.clear()
            if (data!=null){
                bankLData.addAll(data)
            }
        })
    }
    private fun getALiPayAccountList(){
        accountService.getALiPayList(ServiceCallback{
            code, data ->
            aliPayData.clear()
            if (data!=null){
                aliPayData.addAll(data)
            }
        })
    }

    fun getData(){
        walletService.getMyWalletIndexInfo(ServiceCallback{
                code, data ->
            if (data!=null){


                WithdrawHelper.balance = data.money?:"0"

                tv_total_balance.text = "零钱余额${WithdrawHelper.balance}"
            }
        })
    }
}