package com.jyt.video.video

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.CommonTab
import kotlinx.android.synthetic.main.act_play_video.*
import java.util.ArrayList

@Route(path = "/video/play")
class PlayVideoAct:BaseAct(){
    override fun initView() {

        hideToolbar()
        initTab()
    }

    private fun initTab(){
        tab_layout.setTabData(
            arrayListOf(CommonTab("简介"),
            CommonTab("评论")) as ArrayList<CustomTabEntity>)

        tab_layout.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                if (position==0){
                    group_comment.visibility = View.GONE
                    rcv_introduce.visibility = View.VISIBLE
                }else{
                    group_comment.visibility = View.VISIBLE
                    rcv_introduce.visibility = View.GONE
                }
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }


    override fun getLayoutId(): Int {
        return R.layout.act_play_video
    }

}