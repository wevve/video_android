package com.jyt.video.home.frag

import com.jyt.video.home.adapter.BaseVideoListAdapter
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.video.entity.ThumbVideo
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*

class TypeVideoFrag:BaseVideoListFrag(){
    override fun createAdapter(): BaseVideoListAdapter {
        return BaseVideoListAdapter()
    }

    override fun getData(page: Int) {
        var list = ArrayList<Any>()
        if (page==1){
            adapter?.data?.clear()
            list.add(VideoType())
            list.add(VideoGroupTitle())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(VideoGroupTitle())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
        }else{
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
            list.add(ThumbVideo())
        }
        adapter?.data?.addAll(list)
        adapter?.notifyDataSetChanged()
        refresh_layout.refreshComplete()

    }

    override fun setEmptyViewVisible(visible: Boolean) {

    }

}