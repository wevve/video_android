package com.jyt.video.video

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.Mock
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.adapter.CacheVideoAdapter
import com.jyt.video.video.entity.CacheVideo
import com.jyt.video.video.vh.VideoCacheItemVH
import kotlinx.android.synthetic.main.act_cache.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout
import com.jyt.video.common.Constant
import com.jyt.video.common.dialog.AlertDialog


@Route(path = "/video/cache")
class CacheVideoAct: BaseAct(), View.OnClickListener,BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>> {


    var videoAdapter: CacheVideoAdapter? = null

    var curPage = 1



    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is VideoCacheItemVH->{
                when(event){
                    VideoCacheItemVH.EVENT_DELETE->{
                        var dialog = AlertDialog()
                        dialog.message = "是否删除视频缓存"
                        dialog.onClickListener={
                            dialogFragment, s ->
                            dialogFragment.dismiss()
                        }
                        dialog.show(supportFragmentManager,"")
                    }
                    VideoCacheItemVH.EVENT_PAUSE->{

                    }
                    VideoCacheItemVH.EVENT_START->{

                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
                if (tv_right_function.text=="编辑"){
                    tv_right_function.text = "取消"
                    bottom_view.visibility = View.VISIBLE
                    videoAdapter?.showCheckBox = true
                    videoAdapter?.notifyDataSetChanged()
                }else{
                    tv_right_function.text = "编辑"
                    bottom_view.visibility = View.GONE
                    videoAdapter?.showCheckBox = false
                    videoAdapter?.notifyDataSetChanged()

                }
            }
        }
    }


    override fun initView() {
        videoAdapter = CacheVideoAdapter()
        videoAdapter!!.setOnTriggerListener(this)
        recycler_view.adapter = videoAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(curPage +1)
            }

            override fun onRefreshing() {
                getData(1)
            }

        })

        refresh_layout.autoRefresh()

        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_cache
    }

    private fun getData(page:Int){
        var v1 = CacheVideo()
        v1.url = Mock.video_url
        v1.title = "v1av"
        v1.status = 1


        var v2 = CacheVideo()
        v2.url = Mock.video_url
        v2.title = "v2av"
        v2.status = 1


        videoAdapter?.data?.add(v1)
        videoAdapter?.data?.add(v2)
        videoAdapter?.notifyDataSetChanged()

        refresh_layout.refreshComplete()
    }


    private fun startDownLoad(video:CacheVideo){

    }
}
