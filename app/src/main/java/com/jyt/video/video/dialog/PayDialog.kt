package com.jyt.video.video.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.video.entity.VideoDetail
import kotlinx.android.synthetic.main.dialog_pay_video.*

class PayDialog : DialogFragment(), View.OnClickListener {

    var detail: VideoDetail? = null
    var canUseWxPay = false
    var canUseALiPay = false


    var selIndex = -1


    var payTypeCallback: ((type: Int) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_pay_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()


        tv_jb.text = (detail?.videoInfo?.gold?:0).toString()+"金币"
        var coin = UserInfo.getUserHomeInfo().corn ?: 0
        if (coin >= detail?.videoInfo?.gold ?: 0 && coin != 0) {
            img_jb.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.mipmap.jinbi_enable,
                    null
                )
            )
            tv_msg.text = "剩余${UserInfo.getUserHomeInfo().corn ?: 0}金币，可以使用金币购买"

        } else {
            img_jb.isClickable = false

            img_jb.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.mipmap.jinbi_disable,
                    null
                )
            )

            tv_msg.text = "剩余${UserInfo.getUserHomeInfo().corn ?: 0}金币，金币不足 请使用其他方式购买"
        }
    }


    private fun setListener() {
        ly_jb.setOnClickListener(this)
        ly_wx.setOnClickListener(this)
        ly_ali.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
        tv_done.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            ly_jb -> {
                img_sel_jb.visibility = View.VISIBLE
                img_sel_wx.visibility = View.GONE
                img_sel_ali.visibility = View.GONE

                selIndex = 0
            }
            ly_wx -> {
                img_sel_jb.visibility = View.GONE
                img_sel_wx.visibility = View.VISIBLE
                img_sel_ali.visibility = View.GONE

                selIndex = 1
            }
            ly_ali -> {
                img_sel_jb.visibility = View.GONE
                img_sel_wx.visibility = View.GONE
                img_sel_ali.visibility = View.VISIBLE

                selIndex = 2
            }
            tv_cancel -> {
                dismissAllowingStateLoss()
            }
            tv_done -> {
                if (selIndex != -1) {
                    payTypeCallback?.invoke(selIndex)
                    dismissAllowingStateLoss()
                } else {
                    ToastUtil.showShort(context, "请选择支付方式")
                }
            }

        }
    }


}