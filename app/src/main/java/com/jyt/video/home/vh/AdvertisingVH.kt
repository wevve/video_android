package com.jyt.video.home.vh

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.Advertising
import kotlinx.android.synthetic.main.vh_advertising.*
import android.opengl.ETC1.getWidth
import cn.jzvd.JZUtils.dip2px
import android.R.attr.resource
import android.opengl.ETC1.getHeight



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
                var url = (data as Advertising).url
                if (url.isNullOrBlank() || url=="#"){

                }else{
                    ARouter.getInstance().build("/web/index").withString("url",url).navigation()
                }
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
//        Glide.with(itemView).load(data.img).apply(options).into(img_advertising)
//        var url = "http://api.pinyanzhi.net/file/pic/cover/c64129c3-ea92-43af-929c-272721f3a656.jpg"
        var url = data.img
        Glide.with(itemView).asBitmap().load(url).apply(options)
            .into(object:SimpleTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                    val imageWidth = resource?.getWidth()?:0
                    val imageHeight = resource?.getHeight()?:0
                    val para = img_advertising.getLayoutParams()


                    var height = (img_advertising.getWidth() *1f / imageWidth * imageHeight).toInt()
                    para.height = height
                    img_advertising.setLayoutParams(para)
                    Glide.with(itemView).load(url).apply(options).into(img_advertising)
                }

            })


    }
}