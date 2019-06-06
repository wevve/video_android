package com.jyt.video.recharge.frag

import android.os.Bundle
import android.view.View
import com.jyt.video.recharge.entity.RechargeItem

class RechargeCoinFrag:BaseRechargeFrag(){
    var items:ArrayList<RechargeItem>? = null

    var coinMoneyRate:Double? = 1.0

    override fun pay(type:String,price: Double) {



        alertDialog?.onClickListener={
            dialogFragment, s ->
            dialogFragment.dismiss()
        }
        alertDialog?.show(childFragmentManager,"")
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
            input.canInput = true
            adapter?.data.add(input)
        }
        adapter?.notifyDataSetChanged()

    }


}