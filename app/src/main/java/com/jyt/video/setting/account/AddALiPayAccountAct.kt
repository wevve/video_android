package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.AccountServiceImpl
import com.jyt.video.setting.entity.AlipayAccount
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.act_add_alipay_account.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@Route(path = "/setting/account/alipay/add")
class AddALiPayAccountAct:BaseAct(), View.OnClickListener {

    var accountService:AccountService? = null

    var alipayAccount:AlipayAccount? = null

    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
                if (alipayAccount==null){
                    alipayAccount = AlipayAccount()
                }
                alipayAccount?.alipayAccount = input_aliaccount.text.toString()
                accountService?.addALiPayAccount(alipayAccount!!, ServiceCallback{
                    code, data ->
                    if (code==BaseJson.CODE_SUCCESS){
                        onBackPressed()

                    }else{

                    }
                })
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_add_alipay_account
    }

    override fun initView() {

        accountService = AccountServiceImpl()



        alipayAccount = intent.getSerializableExtra("alc") as AlipayAccount?
        if (alipayAccount!=null){
            input_aliaccount.setText(alipayAccount?.alipayAccount)
            input_aliaccount.setSelection(alipayAccount?.alipayAccount?.length?:0)
        }


        tv_right_function.text= "保存"
        tv_right_function.setOnClickListener(this)
    }

}