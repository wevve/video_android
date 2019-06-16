package com.jyt.video.recharge.frag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.jyt.video.api.Constans
import com.jyt.video.common.dialog.ActionSheetDialog
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.entity.RechargeItem
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*

class RechargeCoinFrag:BaseRechargeFrag(){
    var items:ArrayList<RechargeItem>? = null

    var coinMoneyRate:Double? = 1.0

    lateinit var walletService:WalletService

    override fun pay(item:  ActionSheetDialog.Item,price:Double,rechargeItem: RechargeItem){
        var payWay = item.extra as PayWay
        walletService?.createOrder(payWay.payCode,price,1,null,rechargeItem.corn?.toDouble(), ServiceCallback{
                code, data ->
            if (data!=null){
                val uri = Uri.parse(Constans.BaseUrl+"/appapi/pay${data.order_sn}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })

//        alertDialog?.onClickListener={
//            dialogFragment, s ->
//            dialogFragment.dismiss()
//        }
//        alertDialog?.show(childFragmentManager,"")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }


    private fun getData(){

        adapter?.data.clear()
        if (items?.isNotEmpty()==true){
            adapter?.data.addAll(items!!)

            var input = RechargeItem()
            input.coinRate = coinMoneyRate?:1.0
            input.canInput = true
            adapter?.data.add(input)
        }
        adapter?.notifyDataSetChanged()


    }


}