package com.jyt.video.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxBus
import com.jyt.video.event.RefreshEvent
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_login.*

@Route(path = "/login/index")
class LoginAct:BaseAct(), View.OnClickListener {

    lateinit var userService:UserServiceImpl

    override fun initView() {
        userService = UserServiceImpl()

        btn_login.setOnClickListener (this)
        btn_to_register.setOnClickListener (this)
    }
    override fun onClick(v: View?) {
        when(v){
            btn_login->{
                var account = input_account.text.toString()
                var pwd = input_psd.text.toString()
                userService.login(account,pwd, ServiceCallback{
                    code, data ->
                    if (data!=null) {
                        UserInfo.setUserId(data.member_id)
                        RxBus.getInstance().post(RefreshEvent(RefreshEvent.RefreshType.LOGIN))
                        finish()
                    }
                })
            }
            btn_to_register->{
                ARouter.getInstance().build("/register/index").navigation()
            }
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.act_login
    }

}