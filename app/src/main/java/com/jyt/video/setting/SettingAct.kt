package com.jyt.video.setting

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import kotlinx.android.synthetic.main.act_setting.*

@Route(path = "/setting/index")
class SettingAct : BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            fl_to_about->{

            }
            fl_to_account_detail->{
               ARouter.getInstance().build("/setting/account").navigation()
            }
            tv_logout->{
                UserInfo.logout()
                finish()
//                ARouter.getInstance().build("/setting/account/cs").navigation()
            }
        }
    }

    override fun initView() {
        fl_to_about.setOnClickListener(this)
        fl_to_account_detail.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_setting
    }
}
