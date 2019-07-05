package com.jyt.video.video.adapter

import android.support.design.card.MaterialCardView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.DensityUtil
import com.jyt.video.video.dialog.BigImageDialog
import kotlinx.android.synthetic.main.vh_video_capture_item.*

class VideoCaptureAdapter : BaseRcvAdapter<String>() {
    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<String>? {


        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_video_capture_item,viewGroup,false))
    }

    inner class ViewHolder(view: View): BaseVH<String>(view){

        override fun onClick(v: View?) {

            var dialog = BigImageDialog()
            dialog.url = data?:""
            try {
                dialog.show(activity?.supportFragmentManager,"")

            }catch (e:Exception){
                e.printStackTrace()
            }
//            ARouter.getInstance().build("/browse/image")
//                .withInt("index",adapterPosition)
//                .withStringArrayList("image",this@VideoCaptureAdapter.data).navigation()
        }
        override fun bindData(data: String?) {
            super.bindData(data)

            Glide.with(itemView).load(data).apply(RequestOptions().centerCrop()).into(image)
        }
    }
}