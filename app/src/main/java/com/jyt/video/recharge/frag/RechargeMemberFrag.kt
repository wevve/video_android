package com.jyt.video.recharge.frag

import android.os.Bundle
import android.view.View
import com.jyt.video.recharge.entity.RechargeItem

class RechargeMemberFrag:BaseRechargeFrag(){

    var items:ArrayList<RechargeItem>? = null

    override fun pay(type:String,price: Double) {
        alertDialog?.onClickListener={
                dialogFragment, s ->
            dialogFragment.dismiss()
        }
        alertDialog?.show(childFragmentManager,"")

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



    private fun getData(){

//        adapter?.data.add(RechargeItem())
//        adapter?.data.add(RechargeItem())
//        adapter?.data.add(RechargeItem())
//        adapter?.data.add(RechargeItem())
//        adapter?.data.add(RechargeItem())

    }
}