package com.jyt.video.wallet

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.dialog.AlertDialog
import kotlinx.android.synthetic.main.act_my_wallet.*

@Route(path = "/wallet/index")
class MyWalletAct:BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            img_width_draw->{
                ARouter.getInstance().build("/wallet/widthdraw").navigation()
            }
            img_deal_record->{

            }
            btn_recharge->{
                var dialog = AlertDialog()
                dialog.message = "提现需要填写收款账户"
                dialog.leftBtnText = "填写"
                dialog.onClickListener={
                    dialogFragment, s ->

                }
                dialog.show(supportFragmentManager,"")

            }
        }
    }

    override fun initView() {

        img_width_draw.setOnClickListener(this)
        img_deal_record.setOnClickListener(this)
        btn_recharge.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_my_wallet
    }

}