package com.jyt.video.setting.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.setting.entity.AlipayAccount
import com.jyt.video.setting.entity.BankCardAccount
import com.jyt.video.setting.vh.ALiPayAccountItemVH
import com.jyt.video.setting.vh.AddAccountVH
import com.jyt.video.setting.vh.BankCardAccountItemVH

class AccountAdapter : BaseRcvAdapter<Any>(){
    companion object {
        val ADD_ACCOUNT = 0
        val ALIPAY_ACCOUNT = 1
        val BANK_CARD_ACCOUNT = 2
    }
    private var showDelete = false

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {
        return when(i){
            ALIPAY_ACCOUNT->{
                ALiPayAccountItemVH(viewGroup) as BaseVH<Any>
            }
            BANK_CARD_ACCOUNT->{
                BankCardAccountItemVH(viewGroup) as BaseVH<Any>
            }
            ADD_ACCOUNT->{
                AddAccountVH(viewGroup)
            }else ->{
                null
            }
        }
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, i: Int) {
        when(holder::class.java){
            ALiPayAccountItemVH::class.java->{
                (holder as ALiPayAccountItemVH).setDeleteVisibility(showDelete)
            }
            BankCardAccountItemVH::class.java->{
                (holder as BankCardAccountItemVH).setDeleteVisibility(showDelete)

            }
        }

        if (i<data?.size){
            super.onBindViewHolder(holder, i)
        }

    }

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }



    override fun getItemViewType(position: Int): Int {
        var item:Any? = null
        if (position<data?.size){
            item = data?.get(position)
        }
        when(item){
            is AlipayAccount->{
                return ALIPAY_ACCOUNT
            }
            is BankCardAccount->{
                return BANK_CARD_ACCOUNT
            }
            null->{
                return ADD_ACCOUNT
            }
        }
        return super.getItemViewType(position)
    }

    public fun showDelete(){
        showDelete = true
        notifyDataSetChanged()
    }
    public fun hideDelete(){
        showDelete = false
        notifyDataSetChanged()
    }

}