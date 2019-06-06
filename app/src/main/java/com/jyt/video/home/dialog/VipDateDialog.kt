package com.jyt.video.home.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import kotlinx.android.synthetic.main.dialog_vip_end_date.*

class VipDateDialog:DialogFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        dismissAllowingStateLoss()
    }

    private var endData:String? = null

    var title:String? = null
    var content:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,R.style.customDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_vip_end_date,container,false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_ok.setOnClickListener(this)
        setMsg(endData?:"")

        if (!title.isNullOrEmpty()){
            tv_title.text = title
        }
        if (!content.isNullOrEmpty()){
            tv_msg.text = content
        }
    }

    public fun setMsg(endDate:String){
        tv_msg.text = "尊贵的VIP用户，您的ViP会员特权将于三天后 ($endDate)到期，至此到期后将无法体验会员特权服务，立即续费吧"
    }




    override fun onStart() {
        super.onStart()

    }
}