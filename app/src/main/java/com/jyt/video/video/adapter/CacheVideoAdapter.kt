package com.jyt.video.video.adapter

import android.view.ViewGroup
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.vh.VideoCacheItemVH
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener

class CacheVideoAdapter:BaseRcvAdapter<Any>{


    var showCheckBox:Boolean = false

    constructor() : super(){
        fileDownloadListener = object : FileDownloadListener(){
            override fun warn(task: BaseDownloadTask?) {
            }

            override fun completed(task: BaseDownloadTask?) {
                //状态: 下载完成
            }

            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //状态: 队列中

            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                //状态: 出错
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //状态: 暂停
            }

            override fun started(task: BaseDownloadTask?) {
                super.started(task)
                //状态: 开始下载
            }

            override fun connected(
                task: BaseDownloadTask?,
                etag: String?,
                isContinue: Boolean,
                soFarBytes: Int,
                totalBytes: Int
            ) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes)
                //状态: 已连接上
            }

            override fun blockComplete(task: BaseDownloadTask?) {
                super.blockComplete(task)
            }

            override fun isInvalid(): Boolean {
                return super.isInvalid()
            }

            override fun retry(task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int, soFarBytes: Int) {
                super.retry(task, ex, retryingTimes, soFarBytes)
            }
        }
    }


    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Any>? {

        var videoCacheItemVH = VideoCacheItemVH(viewGroup)
        return videoCacheItemVH
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, i: Int) {
        super.onBindViewHolder(holder, i)
        if (holder is VideoCacheItemVH){
            holder.setCheckBoxVisible(showCheckBox)
        }

    }
    private fun setCheckBoxVisible(showCheckBox:Boolean){
        this.showCheckBox = showCheckBox
    }

    lateinit var fileDownloadListener: FileDownloadListener



}