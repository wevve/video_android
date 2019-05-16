package com.jyt.video.common.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jyt.video.common.adapter.BaseRcvAdapter
import kotlinx.android.extensions.LayoutContainer

open class BaseVH<T>(parent: View) : RecyclerView.ViewHolder(parent), LayoutContainer, View.OnClickListener {

    companion object{
        val EVENT_ITEM_CLICK = "itemClick"
    }

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
    }

    fun setOnTriggerListener(onTriggerListener: BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>) {
        this.onTriggerListener = onTriggerListener
    }
}
