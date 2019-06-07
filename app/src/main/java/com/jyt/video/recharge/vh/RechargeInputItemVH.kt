package com.jyt.video.recharge.vh

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.SoftInputUtil
import com.jyt.video.recharge.entity.RechargeItem
import kotlinx.android.synthetic.main.act_add_alipay_account.*
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
        input_price.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                data as RechargeItem
                if (data==null || s.toString().isNullOrEmpty()){
                    tv_price.text = "¥"
                    return
                }
                data!!.inputCoin = s.toString().toDouble()
                 var price = data!!.inputCoin?:0  / data!!.coinRate

                tv_price.text = "¥${price}"

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        tv_discount.visibility = View.GONE
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

        input_price.setText(data?.inputCoin.toString())





    }
}