package com.jyt.video.common.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jyt.video.common.adapter.BaseRcvAdapter
import kotlinx.android.extensions.LayoutContainer
import me.jessyan.autosize.AutoSize

open class BaseVH<T>(parent: View) : RecyclerView.ViewHolder(parent), LayoutContainer, View.OnClickListener {

    companion object{
        val EVENT_ITEM_CLICK = "itemClick"
    }


    var activity:AppCompatActivity?=null

    override fun onClick(v: View?) {
        when(v){
            itemView->{
                onTriggerListener?.onTrigger(this@BaseVH, EVENT_ITEM_CLICK)

            }

        }
    }

    override val containerView: View?



    var data:T? = null


    internal var onTriggerListener: BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>? = null

    val context: Context
        get() = itemView.context


    init {
        containerView = itemView
        itemView.setOnClickListener(this)
    }


    open fun bindData(data: T?) {
        this.data = data

        activity?.let {
            AutoSize.autoConvertDensityOfGlobal(it)
        }
    }

    fun setOnTriggerListener(onTriggerListener: BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>) {
        this.onTriggerListener = onTriggerListener
    }
}
