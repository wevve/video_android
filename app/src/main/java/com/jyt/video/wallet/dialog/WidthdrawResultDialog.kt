package com.jyt.video.wallet.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import android.view.WindowManager
import kotlinx.android.synthetic.main.dialog_width_draw_result.*


class WidthdrawResultDialog:DialogFragment(), View.OnClickListener {


    var money:String? = null

    override fun onClick(v: View?) {
        when(v){
            btn_done->{
                dismiss()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_width_draw_result,container,false)
    }

    override fun onStart() {
        super.onStart()
        dialog.setCanceledOnTouchOutside(true)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_done.setOnClickListener(this)

        tv_money.text = "¥${money}"
    }

}