package com.jyt.video.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.bumptech.glide.Glide
import com.jyt.video.App
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.db.bean.Video
import com.jyt.video.common.dialog.AlertDialog
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.vh_cache_video_item.*
import java.io.File


class CacheVideoAdapter:BaseRcvAdapter<Video>{

    var activity:BaseAct? = null

    var showCheckBox:Boolean = false
    companion object{
        val EVENT_START = "start"
        val EVENT_PAUSE = "pause"
        val EVENT_DELETE = "delete"

        var taskMap:HashMap<Long,BaseDownloadTask> = HashMap()

    }

    var dataMap:HashMap<Long,Video> = HashMap()




    public fun addData(video: Video, position:Int){
        data.add(position,video)
        dataMap.put(video.id,video)
    }

    public fun setData(dataList:List<Video>){
        data.addAll(dataList)
        dataList.forEach {
            dataMap.put(it.id,it)
        }

    }

    public fun removeItem(video: Video){
        data.remove(video)
        dataMap.remove(video.id)
    }

    public fun clearData(){
        data.clear()
        dataMap.clear()
    }

    var fileDownloadListener: FileDownloadListener


    constructor() : super(){
        fileDownloadListener = object : FileDownloadListener(){
            override fun warn(task: BaseDownloadTask?) {
            }

            override fun completed(task: BaseDownloadTask?) {
                //状态: 下载完成
               downloadFinish(task)
            }

            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //状态: 队列中
                downloading(task,soFarBytes, totalBytes)

            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                //状态: 出错
                downloading(task,0, 0)

            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                downloading(task,soFarBytes,totalBytes)

            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //状态: 暂停
                downloading(task,soFarBytes,totalBytes)

            }

            override fun started(task: BaseDownloadTask?) {
                super.started(task)
                //状态: 开始下载
                downloading(task,0,0)
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
                downloading(task,soFarBytes, totalBytes)
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

    private fun downloading(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int){
        if (task==null){
            return
        }
        var id = task?.tag as Long
        var video  = dataMap.get(id)
        var holder = getHolder(id)
        Logger.d(getDownloadStatusText(task.status))

        holder?.tv_state?.text = getDownloadStatusText(task.status)
        holder?.tv_speed?.text = "${task.speed}KB/s"
        holder?.progress?.setPercent(soFarBytes*1f/totalBytes)
        holder?.tv_size?.text = "${String.format("%.2f",(soFarBytes*1f/1024/1024))}MB/${String.format("%.2f",totalBytes*1f/1024/1024)}MB"
        holder?.progress?.visibility = View.VISIBLE

        holder?.tv_size?.visibility = View.VISIBLE
        holder?.tv_speed?.visibility = View.VISIBLE
        holder?.tv_state?.visibility = View.VISIBLE

        holder?.tv_total_size?.visibility = View.GONE
        holder?.tv_time_length?.visibility = View.GONE

        holder?.btn_start?.visibility = View.VISIBLE
        holder?.btn_delete?.visibility = View.VISIBLE
        holder?.img_next?.visibility = View.GONE

        video?.size = totalBytes
        video?.curSize = soFarBytes
        video?.status = if(task.status== FileDownloadStatus.paused ){
            2
        }else{
            1
        }

        if (video!=null)
            App.getAppDataBase().videoDao().updateVideos(video)

    }
    private fun downloadFinish(task: BaseDownloadTask?){
        if (task==null){
            return
        }
        Logger.d("下载完成")
        var id =  task?.tag as Long
        var video = dataMap.get(id)
        var holder =  getHolder(id)
//        holder?.tv_state?.text = "下载完成"
//        holder?.tv_total_size?.text = "${(video?.size?:"0").toFloat()/1024/1024}MB"
        holder?.tv_time_length?.text = "${video?.play_time}"

        holder?.tv_size?.visibility = View.GONE
        holder?.tv_speed?.visibility = View.GONE
        holder?.tv_state?.visibility = View.GONE
        holder?.progress?.visibility = View.GONE

        holder?.tv_total_size?.visibility = View.VISIBLE
        holder?.tv_time_length?.visibility = View.VISIBLE

        holder?.btn_start?.visibility = View.GONE
        holder?.btn_delete?.visibility = View.GONE
        holder?.img_next?.visibility = View.VISIBLE

        video?.status = 4
        if (video!=null)
            App.getAppDataBase().videoDao().updateVideos(video)

    }

    private fun getDownloadStatusText(status:Byte):String{
        return when(status){
            FileDownloadStatus.progress->{
              "正在下载"

            }
            FileDownloadStatus.paused->{
                "暂停"
            }
            FileDownloadStatus.connected->{
                "正在连接"
            }
            FileDownloadStatus.completed->{
                "下载完成"
            }
            FileDownloadStatus.started->{
                "开始"
            }
            FileDownloadStatus.error->{
                "出错了"
            }FileDownloadStatus.pending->{
                "队列中"
            }
            else->{
                ""
            }
        }
    }


    private fun getHolder(id:Long):VideoCacheItemVH?{
        var video  = dataMap.get(id)
        if (video!=null){
            return video?.holder
        }
        return null
    }


    public fun deleteVideo(vararg video:Video){
        video.forEach {
            var taks = taskMap.get(it?.id)
            if (taks!=null){
                taks?.pause()
            }

//            var delPath = it?.path.substring(0, it?.path.lastIndexOf("."))
//            Logger.d("del ${delPath}")
//            var file = File(delPath)

            var file = File(taks?.path)
            var tempFile = File(taks?.path+".temp")
            Logger.d(taks?.path)
            if(file.exists()) {
                file.delete()
                Logger.d("delete")
            }
            if (tempFile.exists()){
                tempFile.delete()
            }

            removeItem(it!!)
        }
        App.getAppDataBase().videoDao().deleteVideos(*video)
    }

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Video>? {

        var videoCacheItemVH = VideoCacheItemVH(viewGroup)
        return videoCacheItemVH
    }

    override fun onBindViewHolder(holder: BaseVH<Video>, i: Int) {
        super.onBindViewHolder(holder, i)
        if (holder is VideoCacheItemVH){
            holder.setCheckBoxVisible(showCheckBox)
        }

    }
    private fun setCheckBoxVisible(showCheckBox:Boolean){
        this.showCheckBox = showCheckBox
    }



    inner class VideoCacheItemVH: BaseVH<Video>, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        override fun onClick(v: View?) {
            when(v){
                btn_start->{
                            var task = taskMap?.get(data?.id!!)
                            Logger.d(task?.path)
                            if (task?.isRunning==true){
                                btn_start.text = "开始"
                                FileDownloader.getImpl().pause(task?.id!!)
                            } else {
                                btn_start.text = "暂停"
                                var task = FileDownloader.getImpl().create(data?.url).setTag(data?.id)
                                    .setPath(data?.path)
                                    .setListener(fileDownloadListener)

                                taskMap.put(data?.id!!,task)
                                task.start()

                            }

                }
                btn_delete->{
                    onTriggerListener?.onTrigger(this, EVENT_DELETE)

                    var dialog = AlertDialog()
                    dialog.message = "是否删除视频缓存"
                    dialog?.leftBtnText = "删除"
                    dialog?.rightBtnText = "取消"
                    dialog.onClickListener={
                            dialogFragment, s ->
                        if ("删除"==s){
                            onTriggerListener?.onTrigger(this,"del")
                            deleteVideo(data!!)
                            notifyDataSetChanged()
                        }else {
                        }
                        dialogFragment.dismiss()

                    }
                    dialog.show(activity?.supportFragmentManager,"")
                }
                itemView->{
                    if (data?.status==4 && !showCheckBox){
                        onTriggerListener?.onTrigger(this,"click")
                    }
                }
            }
        }


        constructor(parent: ViewGroup):super(LayoutInflater.from(parent.context).inflate(com.jyt.video.R.layout.vh_cache_video_item,parent,false)){
            btn_start.setOnClickListener(this)
            btn_delete.setOnClickListener(this)
            cb_sel.setOnCheckedChangeListener(this)

        }


        override fun bindData(data: Video?) {
            super.bindData(data)
            var data = data as Video
            data.holder = this
//            data.url = "http://bhs.proof.ovh.net/files/10Gb.dat"

            if (data.status==4){
//                group_down_load_finish.visibility = View.VISIBLE
//                group_down_loading.visibility = View.GONE

                tv_time_length?.text = "${data?.play_time}"
                tv_total_size?.text = "${String.format("%.2f",data.size*1f/1024/1024)}MB"
                tv_size?.visibility = View.GONE
                tv_speed?.visibility = View.GONE
                tv_state?.visibility = View.GONE
                progress.visibility = View.GONE

                tv_total_size?.visibility = View.VISIBLE
                tv_time_length?.visibility = View.VISIBLE

                btn_start?.visibility = View.GONE
                btn_delete?.visibility = View.GONE
                img_next?.visibility = View.VISIBLE
            }else{
//                group_down_load_finish.visibility = View.GONE
//                group_down_loading.visibility = View.VISIBLE


                tv_speed?.text = "0KB/s"
                progress?.setPercent(data.curSize*1f/data.size)
                tv_size?.text = "${String.format("%.2f",data.curSize*1f/1024/1024)}MB/${String.format("%.2f",data.size*1f/1024/1024)}MB"

                tv_size?.visibility = View.VISIBLE
                tv_speed?.visibility = View.VISIBLE
                tv_state?.visibility = View.VISIBLE

                tv_total_size?.visibility = View.GONE
                tv_time_length?.visibility = View.GONE

                btn_start?.visibility = View.VISIBLE
                btn_delete?.visibility = View.VISIBLE
                img_next?.visibility = View.GONE

                when (data.status){
                    1->{
                       tv_state.text = "正在下载"
                        btn_start.text = "暂停"

                    }
                    2->{
                        tv_state.text = "暂停"
                        btn_start.text = "开始"

                    }
                }

            }

            cb_sel.isChecked =  data?.isSel


            tv_name.text = data.title

            Glide.with(itemView).load(data.cover).into(img_cover)


        }


        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when(buttonView){
                cb_sel->{
                    data?.isSel = isChecked
                    if (isChecked){
                        onTriggerListener?.onTrigger(this,"sel")
                    }else{
                        onTriggerListener?.onTrigger(this,"disSel")

                    }
                }
            }
        }




        public fun setCheckBoxVisible(visible:Boolean){
            cb_sel.visibility = if (visible){
                View.VISIBLE
            }else{
                View.GONE
            }
        }
    }
}

