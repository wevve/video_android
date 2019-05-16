package com.jyt.video.video

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.video.vh.VideoCollectionItemVH
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.adapter.CollectionVideoAdapter
import com.jyt.video.video.entity.CollectionVideo
import kotlinx.android.synthetic.main.act_collection_video.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/video/collection")
class CollectionVideoAct:BaseAct(),BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>>, View.OnClickListener {



    var adapter: CollectionVideoAdapter? = null


    var curPage = 1

    var videoService:VideoService? = null


    //选择的item
    var selItem:ArrayList<Any> = ArrayList()

    override fun onClick(v: View?) {
        when(v){
            tv_right_function->{
                if (tv_right_function.text=="编辑"){
                    tv_right_function.text = "取消"
                    bottom_view.visibility = View.VISIBLE
                    adapter?.showCheckBox = true
                    adapter?.notifyDataSetChanged()
                }else{
                    tv_right_function.text = "编辑"
                    bottom_view.visibility = View.GONE
                    adapter?.showCheckBox = false
                    adapter?.notifyDataSetChanged()
                }
            }
            tv_delete->{
                var alertDialog = AlertDialog()
                alertDialog.message = "是否删除视频"
                alertDialog.onClickListener = {
                    dialogFragment, s ->
                    dialogFragment.dismiss()
                }
                alertDialog.show(supportFragmentManager,"")
            }
            ll_select_all->{
                cb_sel_all.isChecked = !cb_sel_all.isChecked
                adapter?.data?.forEach {
                    (it as CollectionVideo).isSel = cb_sel_all.isChecked
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
        when(holder){
            is VideoCollectionItemVH->{
                when(event){
                    "click"->{
                        //TODO 视频详情
                    }
                    "sel"->{
                        selItem.add(holder.data!!)
                        if (selItem.size!=0){
                            tv_delete.text = "删除（${selItem.size}）"
                        }else{
                            tv_delete.text = "删除"
                        }
                        if (selItem?.size==adapter?.data?.size){
                            cb_sel_all.isChecked = true
                        }
                    }
                    "disSel"->{
                        selItem.remove(holder.data!!)
                        if (selItem.size!=0){
                            tv_delete.text = "删除（${selItem.size}）"
                        }else{
                            tv_delete.text = "删除"
                        }
                        cb_sel_all.isChecked = false
                    }
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_collection_video
    }

    override fun initView() {
        videoService = VideoServiceImpl()
        adapter = CollectionVideoAdapter()
        adapter?.setOnTriggerListener(this)

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                getData(curPage + 1)
            }

            override fun onRefreshing() {
                getData(1)
            }

        })

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter


        refresh_layout.autoRefresh()

        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)

        ll_select_all.setOnClickListener(this)
        tv_delete.setOnClickListener(this)
    }


    private fun getData(page:Int){
        videoService?.getCollectionVideoList(page, ServiceCallback{
            code, data ->
            if (data!=null){
                if (page==1){
                    adapter?.data?.clear()
                }
                adapter?.data?.addAll(data)
                adapter?.notifyDataSetChanged()
            }
            refresh_layout.refreshComplete()
        })

    }
}
