package com.jyt.video.common.adapter

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jyt.video.common.base.BaseVH

import java.util.ArrayList

abstract class BaseRcvAdapter<T> : RecyclerView.Adapter<BaseVH<T>>() {


    var data: ArrayList<T> = ArrayList()

    var activity:AppCompatActivity ? = null

    internal var onTriggerListener: OnViewHolderTriggerListener<BaseVH<*>>? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):BaseVH<T> {
        val vh = createCustomViewHolder(viewGroup, i)
        if (vh != null && onTriggerListener != null) {
            vh.setOnTriggerListener(onTriggerListener!!)
        }
        vh?.activity = activity
        return vh!!
    }

    override fun onBindViewHolder(holder: BaseVH<T>, i: Int) {
        holder.bindData(data!![i])
    }

    override fun getItemCount(): Int {
        return if (data != null) {
            data!!.size
        } else 0
    }

    abstract fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<T>?

    interface OnViewHolderTriggerListener<out T : BaseVH<*>> {
        fun  <T:BaseVH<*>>onTrigger( holder: T, event: String)
    }

    fun setOnTriggerListener(onTriggerListener: OnViewHolderTriggerListener<BaseVH<*>>) {
        this.onTriggerListener = onTriggerListener
    }
}
