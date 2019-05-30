package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_add_bank_card_account.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@Route(path = "/setting/account/bankcard/add")
class AddBankCardAccountAct:BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
//                onBackPressed()
            }
            img_show_bank->{

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
        img_show_bank.setOnClickListener(this)
        img_clear_person_name.setOnClickListener(this)
        img_clear_card_number.setOnClickListener(this)

        tv_right_function.text = "添加"
        tv_right_function.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_add_bank_card_account
    }

}