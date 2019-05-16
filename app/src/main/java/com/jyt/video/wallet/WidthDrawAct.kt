package com.jyt.video.wallet

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.wallet.dialog.ChooseWidthdrawWayDialog
import com.jyt.video.wallet.dialog.WidthdrawResultDialog
import kotlinx.android.synthetic.main.act_width_draw.*


@Route(path = "/wallet/widthdraw")
class WidthDrawAct :BaseAct(), View.OnClickListener {

    var chooseWidthdrawWayDialog: ChooseWidthdrawWayDialog? = null
    var widthdrawResultDialog: WidthdrawResultDialog?=null
    override fun onClick(v: View?) {
        when(v){
            tv_bank_card->{
                chooseWidthdrawWayDialog?.show(supportFragmentManager,"")
            }
            btn_width_draw->{
                widthdrawResultDialog?.show(supportFragmentManager,"")
            }
        }
    }

    override fun getLayoutId():Int {
        return R.layout.act_width_draw
    }

    override fun initView() {
        chooseWidthdrawWayDialog = ChooseWidthdrawWayDialog()
        widthdrawResultDialog = WidthdrawResultDialog()
        tv_bank_card.setOnClickListener(this)
        btn_width_draw.setOnClickListener(this)
    }

}