package com.jyt.video.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.arialyy.aria.core.download.DownloadEntity
import com.arialyy.aria.core.download.DownloadTask
import com.arialyy.aria.core.inf.IEntity
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.db.bean.Video
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.video.util.DownLoadHelper
import com.liulishuo.filedownloader.FileDownloader
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.vh_cache_video_item.*
import java.math.BigDecimal
import java.math.RoundingMode

class DownLoadVideoAdapter :BaseRcvAdapter<Video>(){

    var showCheckBox = false

    override fun createCustomViewHolder(viewGroup: ViewGroup, i: Int): BaseVH<Video>? {
        var holder = Holder(viewGroup)

        return holder
    }

    override fun onBindViewHolder(holder: BaseVH<Video>, i: Int) {
        super.onBindViewHolder(holder, i)

        if (holder is Holder) {
            holder.setCheckBoxVisible(showCheckBox)
        }

    }

    inner class Holder(viewGroup: ViewGroup):BaseVH<Video>(LayoutInflater.from(viewGroup.context).inflate(
        R.layout.vh_cache_video_item,viewGroup,false)), CompoundButton.OnCheckedChangeListener {

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

        override fun onClick(v: View?) {
            when(v){
                btn_start->{
                    var task = data!!.ext as DownloadEntity

                    if (btn_start.text=="暂停"){
                        btn_start.text = "开始"
                        DownLoadHelper.getInstance().stopMission(data!!)
                    }else{
                        btn_start.text = "暂停"
                        DownLoadHelper.getInstance().startMission(data as Video)
                    }
                    Logger.d(task.state)
//                    if (task.state==IEntity.STATE_RUNNING){
//                        btn_start.text = "开始"
//                        DownLoadHelper.getInstance().stopMission(data!!)
//                    } else {
//                        btn_start.text = "暂停"
//                        DownLoadHelper.getInstance().startMission(data as Video)
//
//
//                    }

                }
                btn_delete->{
                    onTriggerListener?.onTrigger(this, CacheVideoAdapter.EVENT_DELETE)

                    var dialog = AlertDialog()
                    dialog.message = "是否删除视频缓存"
                    dialog?.leftBtnText = "删除"
                    dialog?.rightBtnText = "取消"
                    dialog.onClickListener={
                            dialogFragment, s ->
                        if ("删除"==s){
                            DownLoadHelper.getInstance().delMission(data!!)
                            onTriggerListener?.onTrigger(this,"del")
                        }else {
                        }
                        dialogFragment.dismiss()

                    }
                    dialog.show(activity?.supportFragmentManager,"")
                }
                itemView->{
                    var entity = data!!.ext as DownloadEntity?

                    if (entity?.state==IEntity.STATE_COMPLETE && !showCheckBox){
                        onTriggerListener?.onTrigger(this,"click")
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

        init {
            btn_start.setOnClickListener(this)
            btn_delete.setOnClickListener(this)
            cb_sel.setOnCheckedChangeListener(this)


        }

        override fun bindData(data: Video?) {
            super.bindData(data)
            var data = data as Video

            cb_sel.isChecked =  data.isSel
            tv_name.text = data.title

            var options = GlideHelper.centerCrop()
            options.placeholder(R.mipmap.video_holder)
            Glide.with(itemView).load(data.cover).apply(options).into(img_cover)

            if (data.ext!=null){
                setup(data!!.ext as DownloadEntity)
            }
        }


        private fun setup(entity: DownloadEntity){
            progress.setPercent(entity.percent*1f/100)

            if (entity.state==IEntity.STATE_COMPLETE){
                tv_size?.visibility = View.GONE
                tv_speed?.visibility = View.GONE
                tv_state?.visibility = View.GONE
                progress.visibility = View.GONE

                tv_total_size?.visibility = View.VISIBLE
                tv_time_length?.visibility = View.VISIBLE

                btn_start?.visibility = View.GONE
                btn_delete?.visibility = View.GONE
                img_next?.visibility = View.VISIBLE


                tv_total_size.text = "${String.format("%.2f",entity.fileSize*1f/1024/1024)}MB"
                tv_time_length.text = data?.play_time
            }else{

                tv_speed?.text = "${(BigDecimal.valueOf(entity.speed*1.0/1024).setScale(2,RoundingMode.HALF_UP))}KB/s"
                progress?.setPercent(entity.percent*1f/100)

                if (data?.url?.contains("m3u8")==true){
                    tv_size?.visibility = View.INVISIBLE
                }else {
                    var curSize = entity.percent / 100f * (data?.size?:0)
                    tv_size?.text = "${String.format("%.2f", curSize * 1f / 1024 / 1024)}MB/${String.format(
                        "%.2f",
                        (data?.size?:0) * 1f / 1024 / 1024
                    )}MB"
                    tv_size?.visibility = View.VISIBLE
                }
                tv_speed?.visibility = View.VISIBLE
                tv_state?.visibility = View.VISIBLE

                tv_total_size?.visibility = View.GONE
                tv_time_length?.visibility = View.GONE

                btn_start?.visibility = View.VISIBLE
                btn_delete?.visibility = View.VISIBLE
                img_next?.visibility = View.GONE

                when (entity.state){
                    IEntity.STATE_STOP->{
                        tv_state.text = "暂停"
                        btn_start.text = "开始"

                    }
                    IEntity.STATE_FAIL->{
                        tv_state.text = "失败"
                        btn_start.text = "开始"
                    }
                    else->{
                        tv_state.text = "正在下载"
                        btn_start.text = "暂停"
                    }
                }
            }

//            when (entity.state){
//                IEntity.STATE_FAIL->{
//
//                }
//                IEntity.STATE_COMPLETE->{
//
//
//                }
//                IEntity.STATE_RUNNING->{
//
//                }
//                IEntity.STATE_STOP,IEntity.STATE_CANCEL->{
//
//                }
//                IEntity.STATE_POST_PRE->{
//
//                }
//            }

        }


    }

}