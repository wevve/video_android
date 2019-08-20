package com.jyt.video.video

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTask
import com.jyt.video.App
import com.jyt.video.R
import com.jyt.video.common.Constant
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.db.bean.Video
import com.jyt.video.video.adapter.DownLoadVideoAdapter
import com.jyt.video.video.entity.VideoDetail
import com.jyt.video.video.util.DownLoadHelper
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.act_cache.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_video_list_empty.*
import me.dkzwm.widget.srl.SmoothRefreshLayout
@Route(path = "/video/cache")
class DownLoadVideoAct : BaseAct(),View.OnClickListener {

    lateinit var videoAdapter: DownLoadVideoAdapter

    var selItem:ArrayList<Video> = ArrayList()



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
                var alertDialog = com.jyt.video.common.dialog.AlertDialog()
                alertDialog.message = "是否删除视频"
                alertDialog.onClickListener = {
                        dialogFragment, s ->
                    if(s=="确定") {
//                        var videoIdList = ArrayList<Video>()
//                        videoAdapter?.data?.forEach {
//                            var data = (it as Video)
//                            if (data.isSel) {
//                                videoIdList.add(data)
//                            }
//                        }
//                        if (videoIdList.isNotEmpty()) {
////                            videoAdapter.deleteVideo(*(videoIdList.toTypedArray()))
//                            videoAdapter?.notifyDataSetChanged()
//                        }
                        DownLoadHelper.getInstance().delMission(*selItem.toTypedArray())
                        refresh_layout?.autoRefresh()
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
        DownLoadHelper.getInstance().triggerUpdate = {
            task,position->
            if(videoAdapter.data.isEmpty() || position>videoAdapter.data.size ){

            }else{
                videoAdapter.data.get(position).ext = task.entity

                videoAdapter.notifyDataSetChanged()
            }

        }
        videoAdapter = DownLoadVideoAdapter()
        videoAdapter.onTriggerListener = object : BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>{
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                var video = (holder as DownLoadVideoAdapter.Holder).data !!
                when(event){
                    "del"->{
                        selItem.remove(video)

                        cb_sel_all.isChecked = false
                        updateDelText()
                        refresh_layout?.autoRefresh()
                        if (tv_right_function.text=="取消") {
                            tv_right_function.callOnClick()
                        }
                    }
                    "click"->{
                        ARouter.getInstance().build("/video/play").withLong("videoId",video.id).navigation()
                    }
                    "sel"->{
                        selItem.add(video)
                        updateDelText()
                        if (selItem?.size==videoAdapter?.data?.size){
                            cb_sel_all.isChecked = true
                        }
                    }
                    "disSel"->{
                        selItem.remove(video)
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

        refresh_layout.setOnRefreshListener(object : SmoothRefreshLayout.OnRefreshListener{
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


        Aria.download(this).register()
    }

    override fun onDestroy() {
        super.onDestroy()
        Aria.download(this).unRegister()

    }

    override fun getLayoutId(): Int {
        return R.layout.act_cache
    }

    private fun getData(page:Int){

        var data = DownLoadHelper.getInstance().getVideoList()

        Logger.d(data.size)
        selItem.clear()

        videoAdapter.data.clear()
        videoAdapter.data.addAll(data)
        videoAdapter.notifyDataSetChanged()

        refresh_layout?.refreshComplete()

        if (videoAdapter.data.isEmpty()){
            ll_empty.visibility = View.VISIBLE
        }else{
            ll_empty.visibility = View.GONE

        }
        updateDelText()
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




//            CacheVideoAdapter.taskMap.put(cacheVideo?.id!!,task)

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


    @Download.onWait
    fun onWait(task: DownloadTask) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)

    }

    @Download.onPre
    fun onPre(task: DownloadTask) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)

    }

    @Download.onTaskStart
    fun taskStart(task: DownloadTask) {
        Logger.d(task.convertFileSize)

        DownLoadHelper.getInstance().trigger(task)

    }

    @Download.onTaskRunning
    fun running(task: DownloadTask) {
        Logger.d(task)
        Logger.d(task.percent)
        DownLoadHelper.getInstance().trigger(task)
    }

    @Download.onTaskResume
    fun taskResume(task: DownloadTask) {
        Logger.d(task)

        DownLoadHelper.getInstance().trigger(task)


    }

    @Download.onTaskStop
    fun taskStop(task: DownloadTask) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)

    }

    @Download.onTaskCancel
    fun taskCancel(task: DownloadTask) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)


    }

    @Download.onTaskFail
    fun taskFail(task: DownloadTask, e: Exception) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)

    }

    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        Logger.d(task)
        DownLoadHelper.getInstance().trigger(task)

    }
}