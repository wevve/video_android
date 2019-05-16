package com.jyt.video.common.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import kotlinx.android.synthetic.main.dialog_alert.*

class AlertDialog : DialogFragment(), View.OnClickListener {


    var onClickListener:((DialogFragment,String)->Unit)? = null

    var message:String? = null
    var leftBtnText:String? = null
    var rightBtnText:String? = null

    override fun onClick(v: View?) {
        when(v){
            tv_cancel->{
                onClickListener?.invoke(this,tv_cancel.text.toString())
            }
            tv_confirm->{
                onClickListener?.invoke(this,tv_confirm.text.toString())

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_cancel.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)

        if (leftBtnText?.isNotEmpty()==true){
            tv_confirm.text = leftBtnText
            tv_confirm.visibility = View.VISIBLE
            line_ver.visibility =  View.VISIBLE
        }else{
            tv_confirm.visibility = View.GONE
            line_ver.visibility =  View.GONE
        }
        if (rightBtnText?.isNotEmpty()==true){
            tv_cancel.text = rightBtnText
            tv_cancel.visibility = View.VISIBLE
            line_ver.visibility =  View.VISIBLE
        }else{
            tv_cancel.visibility = View.GONE
            line_ver.visibility =  View.GONE
        }
        tv_message.text = message

    }



}
