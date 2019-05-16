package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_cs_account.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@Route(path = "/setting/account/cs")
class CSAccountAct : BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            fl_to_alipay->{
                ARouter.getInstance().build("/setting/account/alipay").navigation()
            }
            fl_to_bank_card->{
                ARouter.getInstance().build("/setting/account/bankcard").navigation()
            }


        }
    }

    override fun initView() {
        fl_to_alipay.setOnClickListener (this)
        fl_to_bank_card.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_cs_account
    }

}