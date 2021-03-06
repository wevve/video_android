package com.jyt.video.recharge.frag

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.common.util.DensityUtil
import com.jyt.video.recharge.adapter.RechargeItemAdapter
import com.jyt.video.common.dialog.ActionSheetDialog
import com.jyt.video.recharge.entity.RechargeItem
import com.jyt.video.recharge.vh.BaseRechargeItemVH
import kotlinx.android.synthetic.main.frag_recharge_base.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*

abstract class BaseRechargeFrag :BaseFrag(),BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<RechargeItem>>,
    View.OnClickListener {


    internal lateinit var adapter:RechargeItemAdapter

    var selHolder:BaseRechargeItemVH? = null

    var alertDialog:AlertDialog? = null

    var choosePayWayDialog: ActionSheetDialog? = null

    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        holder as BaseRechargeItemVH

        if (holder==selHolder){
            return
        }
        holder.isCheck = true

        selHolder?.isCheck = false

        selHolder = holder

        tv_total_price.text =  "支付金额：${holder.getPrice()}"

    }
    override fun onClick(v: View?) {
        when(v){
            btn_pay->{
                if (selHolder==null){
                    return
                }

                choosePayWayDialog?.show(childFragmentManager,"")
            }
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.frag_recharge_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RechargeItemAdapter()

        adapter.setOnTriggerListener(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(context,3)


        btn_pay.setOnClickListener(this)

        (recycler_view.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = DensityUtil.dpToPx(context,13)
        (recycler_view.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = DensityUtil.dpToPx(context,13)

        alertDialog = AlertDialog()
        alertDialog?.message = "充值成功"
        alertDialog?.leftBtnText = "确定"


        choosePayWayDialog = ActionSheetDialog()
        choosePayWayDialog?.items = arrayOf(
            ActionSheetDialog.Item("微信支付",R.mipmap.weixin)
            , ActionSheetDialog.Item("支付宝支付",R.mipmap.zhifubao))
        choosePayWayDialog?.onItemClickListener = object : ActionSheetDialog.OnItemClickListener{
            override fun onClick(dialogFragment: DialogFragment, item: String) {
                pay(item,selHolder!!.getPrice())
                dialogFragment.dismiss()
            }

        }
    }



    abstract fun pay(type:String,price:Double)


}