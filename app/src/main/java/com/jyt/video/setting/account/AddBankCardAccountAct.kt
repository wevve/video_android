package com.jyt.video.setting.account

import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.setting.entity.Bank
import kotlinx.android.synthetic.main.act_add_bank_card_account.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.jyt.video.main.MainActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.setting.entity.BankCardAccount
import com.orhanobut.logger.Logger
import java.util.ArrayList


@Route(path = "/setting/account/bankcard/add")
class AddBankCardAccountAct:BaseAct(), View.OnClickListener {

    lateinit var accountService: AccountService

    var bankList:ArrayList<Bank> ?= null

    var bankCardAccount:BankCardAccount? = null


    var selBank:Bank? = null
    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
//                onBackPressed()
                addBankAccount()
            }
            img_show_bank->{

                if(bankList?.isNotEmpty()==false){
                    return
                }
                //条件选择器
                val pvOptions = OptionsPickerBuilder(this,
                    OnOptionsSelectListener { options1, option2, options3, v ->
                        //返回的分别是三个级别的选中位置

                        selBank = bankList?.get(options1)

                        tv_bank_name.text = selBank?.name

                    })
                    .build<Any>()

                pvOptions.setPicker(bankList as List<Any>?)
                pvOptions.show()
            }
            img_clear_person_name->{
                input_person_name.setText("")
            }
            img_clear_card_number->{
                input_card_number.setText("")
            }
        }
    }

    override fun initView() {
        accountService = AccountServiceImpl()


        img_show_bank.setOnClickListener(this)
        img_clear_person_name.setOnClickListener(this)
        img_clear_card_number.setOnClickListener(this)

        tv_right_function.text = "添加"
        tv_right_function.setOnClickListener(this)


        getBankInfo()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_add_bank_card_account
    }


    private fun getBankInfo(){
        accountService .getPopBankList(ServiceCallback{
            code, data ->
            bankList = data
        })
    }


    private fun addBankAccount(){
        if(bankCardAccount==null){
            bankCardAccount = BankCardAccount()
        }
        bankCardAccount?.bankName = tv_bank_name.text.toString()
        bankCardAccount?.cardUser = input_person_name.text.toString()
        bankCardAccount?.cardNum = input_card_number.text.toString()

        if ((bankCardAccount?.cardNum?.length?:0)<16 ){
            ToastUtil.showShort(this,"请输入正确的卡号")
            return
        }

        accountService.addBankCard(bankCardAccount!!,ServiceCallback{
            code, data ->
            if (code==BaseJson.CODE_SUCCESS){
                finish()
            }
        })
    }

}