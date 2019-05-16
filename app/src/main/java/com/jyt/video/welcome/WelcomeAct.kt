package com.jyt.video.welcome

import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_welcome.*

class WelcomeAct : BaseAct(){
    override fun initView() {
        tv_next.setOnClickListener {
            toMainAct()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_welcome
    }



    private fun toMainAct(){
        ARouter.getInstance().build("/main/index").navigation()
    }
}
