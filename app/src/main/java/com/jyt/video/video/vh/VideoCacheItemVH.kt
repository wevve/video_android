package com.jyt.video.video.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.entity.CacheVideo
import com.liulishuo.filedownloader.model.FileDownloadStatus
import kotlinx.android.synthetic.main.vh_cache_video_item.*

class VideoCacheItemVH: BaseVH<Any>, View.OnClickListener {

    companion object{
        val EVENT_START = "start"
        val EVENT_PAUSE = "pause"
        val EVENT_DELETE = "delete"
    }

    override fun onClick(v: View?) {
        when(v){
            btn_start->{
                when (btn_start.text) {
                    "开始" -> {
                        onTriggerListener?.onTrigger(this, EVENT_START)
                        btn_start.text = "暂停"
                    }
                    "暂停"->{
                        onTriggerListener?.onTrigger(this, EVENT_PAUSE)
                        btn_start.text = "开始"
                    }
                }
            }
            btn_delete->{
                onTriggerListener?.onTrigger(this, EVENT_DELETE)

            }
        }
    }


    constructor(parent:ViewGroup):super(LayoutInflater.from(parent.context).inflate(R.layout.vh_cache_video_item,parent,false)){
        btn_start.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
    }


//    fun bindDownLoadData( data: CacheVideo,status:Int,  sofar:Long,  total:Long){
//
//        when(status.toByte()){
//            FileDownloadStatus.paused->{
//                //暂停
//            }
//            FileDownloadStatus.error->{
//                //出错
//            }
//
//            FileDownloadStatus.pending->{
//                //队列中
//            }
//            FileDownloadStatus.started->{
//                //开始
//            }
//            FileDownloadStatus.connected->{
//                //连接中
//            }
//            FileDownloadStatus.progress->{
//                //下载中
//            }
//            FileDownloadStatus.INVALID_STATUS-> {
//                //未下载
//            }
//
//            else->{
//                //下载中 其他状态
//
//            }
//
//        }
//    }


    override fun bindData(data: Any?) {
        super.bindData(data)
        var data = data as CacheVideo

        if (data.status==4){
            group_down_load_finish.visibility = View.VISIBLE
            group_down_loading.visibility = View.GONE
        }else{
            group_down_load_finish.visibility = View.GONE
            group_down_loading.visibility =  View.VISIBLE
        }

    }

    fun updateDownloading(status: Int, sofar: Long, total: Long) {
        val percent = sofar / total.toFloat()
//        taskPb.setMax(100)
//        taskPb.setProgress((percent * 100).toInt())

        when (status.toByte()) {
            FileDownloadStatus.pending -> {

            }

            FileDownloadStatus.started ->{

            }
            FileDownloadStatus.connected -> {

            }
            FileDownloadStatus.progress -> {

            }
            else -> {

            }
        }

        btn_start.text = "暂停"
    }


    fun updateNotDownloaded(status: Int, sofar: Long, total: Long) {
        if (sofar > 0 && total > 0) {
            val percent = sofar / total.toFloat()
//            taskPb.setMax(100)
//            taskPb.setProgress((percent * 100).toInt())
        } else {
//            taskPb.setMax(1)
//            taskPb.setProgress(0)
        }

        when (status.toByte()) {
            FileDownloadStatus.error -> {

            }
            FileDownloadStatus.paused -> {

            }
            else -> {

            }
        }
        btn_start.text = "开始"
    }

    fun updateDownloaded() {
//        taskPb.setMax(1)
//        taskPb.setProgress(1)

//        taskStatusTv.setText(R.string.tasks_manager_demo_status_completed)


            group_down_load_finish.visibility = View.VISIBLE
            group_down_loading.visibility = View.GONE

    }


    public fun setCheckBoxVisible(visible:Boolean){
        cb_sel.visibility = if (visible){View.VISIBLE}else{View.GONE}
    }
}