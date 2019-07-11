package com.jyt.video.home.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.Advertising
import kotlinx.android.synthetic.main.vh_advertising.*

class AdvertisingVH(parent: View) : BaseVH<Any>(LayoutInflater.from(parent.context).inflate(R.layout.vh_advertising,parent as ViewGroup,false)) {

    var adapter:BaseRcvAdapter<Any>? = null

    init {
        img_close.setOnClickListener(this)
        img_advertising.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            img_close->{
                adapter?.data?.remove(data)
                adapter?.notifyDataSetChanged()

            }
            img_advertising->{
                ARouter.getInstance().build("/web/index").withString("url",(data as Advertising).url).navigation()
            }
            else->{
//                super.onClick(v)
            }
        }
    }

    override fun bindData(data: Any?) {
        super.bindData(data)

        data as Advertising

        var options = RequestOptions().centerCrop()
        options.placeholder(R.mipmap.ad_holder)
        options.error(R.mipmap.ad_holder)
        Glide.with(itemView).load(data.img).apply(options).into(img_advertising)


    }
}