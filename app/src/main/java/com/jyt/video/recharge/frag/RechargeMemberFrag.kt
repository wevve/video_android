package com.jyt.video.recharge.frag

import android.os.Bundle
import android.view.View
import com.jyt.video.common.dialog.ActionSheetDialog
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.entity.RechargeItem
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import android.content.Intent
import android.net.Uri
import com.jyt.video.api.Constans
import com.jyt.video.common.Constant


class RechargeMemberFrag:BaseRechargeFrag(){

    var items:ArrayList<RechargeItem>? = null

    lateinit var walletService: WalletService

    override fun pay(item:  ActionSheetDialog.Item,price:Double,rechargeItem: RechargeItem){

//        alertDialog?.message
//        alertDialog?.onClickListener={
//                dialogFragment, s ->
//            dialogFragment.dismiss()
//        }
//        alertDialog?.show(childFragmentManager,"")

        var payWay = item.extra as PayWay
        walletService?.createOrder(payWay.payCode,price,2,rechargeItem.vipId!!.toInt(),null, ServiceCallback{
            code, data ->
            if (data!=null){
                val uri = Uri.parse(Constans.BaseUrl+"/appapi/pay/orderSn/${data.order_sn}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getData()

        adapter?.data.clear()
        if (items?.isNotEmpty()==true) {
            adapter?.data.addAll(items!!)
        }
        adapter?.notifyDataSetChanged()
    }




}