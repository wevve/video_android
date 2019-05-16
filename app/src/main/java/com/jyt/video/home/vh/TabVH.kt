package com.jyt.video.home.vh

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.entity.TabEntity
import kotlinx.android.synthetic.main.vh_tab.*

class TabVH(parent: View) : BaseVH<TabEntity>(LayoutInflater.from(parent.context).inflate(R.layout.vh_tab,parent as ViewGroup,false)) {


    override fun bindData(data: TabEntity?) {
        super.bindData(data)

        tv_title.text = data?.tabName

        if (data?.sel==true){
            indicator.visibility = View.VISIBLE
            tv_title.setTextColor(Color.parseColor("#9C28B1"))
        }else{
            indicator.visibility = View.GONE
            tv_title.setTextColor(Color.parseColor("#505050"))

        }
    }
}