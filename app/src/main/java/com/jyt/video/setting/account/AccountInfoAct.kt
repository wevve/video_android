package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_account_info.*

@Route(path = "/setting/account")
class AccountInfoAct :BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            fl_edit_avatar->{

            }
            fl_edit_name->{

            }
            fl_to_cs_account->{
                ARouter.getInstance().build("/setting/account/cs").navigation()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_account_info
    }

    override fun initView() {
        fl_edit_avatar.setOnClickListener(this)
        fl_edit_name.setOnClickListener(this)
        fl_to_cs_account.setOnClickListener(this)
    }

}