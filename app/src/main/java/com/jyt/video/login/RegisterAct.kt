package com.jyt.video.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_register.*

@Route(path = "/register/index")
class RegisterAct:BaseAct(), View.OnClickListener {

    lateinit var userService:UserService
    override fun onClick(v: View?) {
        when(v){
            btn_register->{

                var account = input_account.text.toString()
                var psd1 = input_psd1.text.toString()
                var psd2 = input_psd2.text.toString()

                userService.register(account,psd1,psd2, ServiceCallback { code, data ->
                    if (code==BaseJson.CODE_SUCCESS){
                        userService.login(account,psd1, ServiceCallback { code, data ->
                            if (data != null) {
                                ARouter.getInstance().build("/main/index").navigation()
                            }
                        })
                    }
                })
            }
            tv_to_protocol->{

            }
        }
    }

    override fun initView() {
        userService = UserServiceImpl()
        btn_register.setOnClickListener(this)
        tv_to_protocol.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_register
    }



}
