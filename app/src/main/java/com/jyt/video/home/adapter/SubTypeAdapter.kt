package com.jyt.video.home.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.DensityUtil
import com.jyt.video.home.entity.VideoType

class SubTypeAdapter : BaseRcvAdapter<VideoType.Type>() {

    var curSel: VideoType.Type? = null


    init {

    }

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<VideoType.Type>? {
        var holder = Holder(TextView(viewGroup.context))


        return holder
    }



    inner class Holder(view: View):BaseVH<VideoType.Type>(view){
        var tv = itemView as TextView

        init {
            var dp13 =  DensityUtil.dpToPx(view.context,13)
            var dp3 = DensityUtil.dpToPx(view.context,3)
            tv.setPadding(dp13,dp3,dp13,dp3)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)
            tv.gravity = Gravity.CENTER
        }

        override fun onClick(v: View?) {
            when(v){
                itemView->{
                    curSel = data
                    notifyDataSetChanged()
                    super.onClick(v)
                }
            }
        }

        override fun bindData(data: VideoType.Type?) {
            super.bindData(data)

            tv.text = data?.name

            if (curSel == data){
                tv.setTextColor(itemView.resources.getColor(R.color.colorPrimary))
            }else{
                tv.setTextColor(Color.BLACK)
            }
        }
    }
}
