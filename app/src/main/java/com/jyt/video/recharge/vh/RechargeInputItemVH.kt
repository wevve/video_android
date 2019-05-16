package com.jyt.video.recharge.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.SoftInputUtil
import com.jyt.video.recharge.entity.RechargeItem
import kotlinx.android.synthetic.main.vh_recharge_input_item.*

class RechargeInputItemVH(viewGroup: ViewGroup) :BaseRechargeItemVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_recharge_input_item,viewGroup,false)),
    View.OnClickListener {


    var count =0


    override fun onClick(v: View?) {
        super.onClick(v)
    }


    override fun getPrice(): Double {

        var text = input_price.text.toString()
        if (text.isNullOrEmpty()){
            return  0.0
        }
        return text.toDouble()
    }

    init {
//        input_price.inputType = EditorInfo.TYPE_CLASS_NUMBER and EditorInfo.TYPE_NUMBER_FLAG_SIGNED
        input_price.setOnTouchListener({
            v, event ->
            itemView.callOnClick()
            false
        })
//      input_price.isClickable  = false
//        input_price.setOnClickListener(this)
//        input_price.onFocusChangeListener = View.OnFocusChangeListener {
//            v, hasFocus ->
//            println(" hasFocus "+hasFocus)
//            if (hasFocus){
//                count = 1
//            }
//            if (!hasFocus && count==1){
//                SoftInputUtil.hideSoftKeyboard(itemView.context,v)
//                count = 0
//            }
//        }
    }

    override fun bindData(data: RechargeItem?) {
        super.bindData(data)

        v_background.background = if (data?.sel==true){
            input_price.requestFocus()
            SoftInputUtil.showSoftKeyboard(itemView.context,input_price)

            itemView.resources.getDrawable(R.drawable.shape_recharge_item_sel)
        }else{
            input_price.clearFocus()
            SoftInputUtil.hideSoftKeyboard(itemView.context,input_price)
            itemView.resources.getDrawable(R.drawable.shape_recharge_item_nor)
        }
    }
}