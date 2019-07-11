package com.jyt.video.video

import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.App
import com.jyt.video.Mock
import com.jyt.video.R
import com.jyt.video.common.Constant
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.db.bean.Video
import com.jyt.video.video.adapter.CacheVideoAdapter
import kotlinx.android.synthetic.main.act_cache.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.video.entity.CollectionVideo
import com.jyt.video.video.entity.VideoDetail
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.act_cache.bottom_view
import kotlinx.android.synthetic.main.act_cache.cb_sel_all
import kotlinx.android.synthetic.main.act_cache.ll_select_all
import kotlinx.android.synthetic.main.act_cache.recycler_view
import kotlinx.android.synthetic.main.act_cache.refresh_layout
import kotlinx.android.synthetic.main.act_cache.tv_delete
import kotlinx.android.synthetic.main.act_collection_video.*
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.*
import kotlinx.android.synthetic.main.layout_video_list_empty.*


@Route(path = "/video/cache")
class CacheVideoAct: BaseAct(), View.OnClickListener {



    lateinit var videoAdapter: CacheVideoAdapter

    var curPage = 1

    var selItem:ArrayList<Any> = ArrayList()



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
           tv_delete->{
                var alertDialog = AlertDialog()
                alertDialog.message = "是否删除视频"
                alertDialog.onClickListener = {
                    dialogFragment, s ->
                    if(s=="确定") {
                        var videoIdList = ArrayList<Video>()
                        videoAdapter?.data?.forEach {
                            var data = (it as Video)
                            if (data.isSel) {
                                videoIdList.add(data)
                            }
                        }
                        if (videoIdList.isNotEmpty()) {
                            videoAdapter.deleteVideo(*(videoIdList.toTypedArray()))
                            videoAdapter?.notifyDataSetChanged()
                        }
                        tv_right_function.callOnClick()
                    }
                    dialogFragment.dismiss()
                }
                alertDialog.leftBtnText="取消"
                alertDialog.rightBtnText = "确定"
                alertDialog.show(supportFragmentManager,"")
            }
            ll_select_all->{
                cb_sel_all.isChecked = !cb_sel_all.isChecked
                videoAdapter?.data?.forEach {
                    (it as Video).isSel = cb_sel_all.isChecked
                }
                videoAdapter?.notifyDataSetChanged()


            }
        }
    }


    override fun initView() {
        videoAdapter = CacheVideoAdapter()
        videoAdapter.onTriggerListener = object :BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>{
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                when(event){
                    "del"->{
                        selItem.remove(holder.data!!)
                        updateDelText()
                        cb_sel_all.isChecked = false

                    }
                    "click"->{
                        ARouter.getInstance().build("/video/play").withLong("videoId",(holder.data as Video).id).navigation()
                    }
                    "sel"->{
                        selItem.add(holder.data!!)
                        updateDelText()
                        if (selItem?.size==videoAdapter?.data?.size){
                            cb_sel_all.isChecked = true
                        }
                    }
                    "disSel"->{
                        selItem.remove(holder.data!!)
                        updateDelText()
                        cb_sel_all.isChecked = false
                    }
                }
            }

        }
        videoAdapter.activity = this

        var vd =  intent.getSerializableExtra("videoDetail") as VideoDetail?
        if (vd!=null) {
            startNew(vd)
        }

        recycler_view.adapter = videoAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
//                getData(curPage +1)
                refresh_layout.refreshComplete()
            }

            override fun onRefreshing() {
                getData(1)
            }

        })

        refresh_layout.autoRefresh()
        ll_select_all.setOnClickListener(this)
        tv_delete.setOnClickListener(this)
        tv_right_function.text = "编辑"
        tv_right_function.setOnClickListener(this)

        CacheVideoAdapter.taskMap.forEach {
            it.value.setListener(videoAdapter.fileDownloadListener)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_cache
    }

    private fun getData(page:Int){

        var all = App.getAppDataBase().videoDao().loadAllVideos()
        all.forEach {
            video->
            var task = CacheVideoAdapter.taskMap.get(video.id)
            if (task==null){
                video.status = 2//默认为暂停状态
                var task = FileDownloader.getImpl().create(video?.url).setTag(video?.id).setPath(video.path)

                CacheVideoAdapter.taskMap.put(video.id,task)
            }else{


            }
        }
        selItem.clear()
        videoAdapter.clearData()
        videoAdapter.setData(all)

        videoAdapter?.notifyDataSetChanged()

        if (all.isEmpty()){
            ll_empty.visibility = View.VISIBLE
        }else{
            ll_empty.visibility = View.GONE
        }

        refresh_layout.refreshComplete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    companion object{
         fun startNew(videoDetail: VideoDetail){
            var cacheVideo = Video()
             cacheVideo.status = 1
             cacheVideo.play_time = videoDetail.videoInfo?.play_time
            cacheVideo.id = videoDetail.videoId!!
            cacheVideo.title = videoDetail.videoInfo?.title
            var url = videoDetail.videoInfo?.url?:""
            var fileName = url.substring(url.lastIndexOf("/")+1,url.length)
            cacheVideo.path = Constant.getAppCacheFile().absolutePath+"/"+fileName
            cacheVideo.url = url
            cacheVideo.cover = videoDetail.videoInfo?.thumbnail



             Logger.d(cacheVideo)
            App.getAppDataBase().videoDao().insertVideos(cacheVideo)

             var task = FileDownloader.getImpl().create(cacheVideo?.url).setTag(cacheVideo?.id)
                 .setListener(object :FileDownloadListener(){
                     override fun warn(task: BaseDownloadTask?) {

                     }

                     override fun completed(task: BaseDownloadTask?) {
                     }

                     override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                     }

                     override fun error(task: BaseDownloadTask?, e: Throwable?) {
                     }

                     override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                     }

                     override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                     }

                 })
                 .setPath(cacheVideo?.path)

             task.start()

             CacheVideoAdapter.taskMap.put(cacheVideo?.id!!,task)

//            videoAdapter.addData(cacheVideo,0)
//            videoAdapter.notifyDataSetChanged()


        }
    }




    override fun onPause() {
        super.onPause()
        App.getAppDataBase().videoDao().updateVideos(*videoAdapter.data.toTypedArray())
    }


    private fun updateDelText(){
        if (selItem.size!=0){
            tv_delete.text = "删除（${selItem.size}）"
        }else{
            tv_delete.text = "删除"
        }
    }
}
