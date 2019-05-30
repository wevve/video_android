package com.jyt.video.video.vh

import android.support.v4.util.TimeUtils
import android.text.Layout
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.video.entity.CommentItem
import com.jyt.video.video.entity.CommentVO
import kotlinx.android.synthetic.main.vh_comment.*
import java.text.SimpleDateFormat

class CommentVH(parent: View) : BaseVH<Any>(LayoutInflater.from(parent.context).inflate(R.layout.vh_comment,parent as ViewGroup,false)) {

    var simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")

    override fun bindData(data: Any?) {
        super.bindData(data)
        data as CommentItem

        tv_name.text = data.nickname
        tv_vip.visibility = View.GONE

        tv_content.text = data.content
        tv_date.text = simpleDateFormat.format(data.last_time*1000)


        Glide.with(itemView).load(data.headimgurl).apply(GlideHelper.avatar()).into(img_avatar)
    }
}
