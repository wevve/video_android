package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.setting.entity.AlipayAccount
import kotlinx.android.synthetic.main.layout_toolbar.*

@Route(path = "/setting/account/alipay/add")
class AddALiPayAccountAct:BaseAct(), View.OnClickListener {
    var accountService:AccountService? = null

    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
                accountService?.addALiPayAccount(AlipayAccount(), ServiceCallback{
                    code, data ->
                    onBackPressed()
                })
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_add_alipay_account
    }

    override fun initView() {

        accountService = AccountServiceImpl()

        tv_right_function.text= "保存"
        tv_right_function.setOnClickListener(this)
    }

}