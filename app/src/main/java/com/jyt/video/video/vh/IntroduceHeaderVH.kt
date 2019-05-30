package com.jyt.video.video.vh

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.adapter.VideoCaptureAdapter
import com.jyt.video.video.entity.VideoDetail
import kotlinx.android.synthetic.main.vh_introduce_header.*

class IntroduceHeaderVH(viewGroup: ViewGroup) :BaseVH<Any>(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_introduce_header,viewGroup,false)){

    var captureAdapter:VideoCaptureAdapter = VideoCaptureAdapter()

    var videoService:VideoService = VideoServiceImpl()

    init {
        rcv_capture.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rcv_capture.adapter = captureAdapter

        img_collection.setOnClickListener(this)
        img_like.setOnClickListener(this)

        img_show_all_info.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            img_show_all_info->{
                if (img_show_all_info.rotation==0f){
                    tv_introduce.setSingleLine(false)
                    img_show_all_info.rotation = 180f
                }else{
                    tv_introduce.setSingleLine(true)
                    img_show_all_info.rotation = 0f
                }
            }
            img_collection->{
                doCollection()
            }
            img_like->{
                doLike()
            }
          else->{
              super.onClick(v)

          }
        }
    }
    override fun bindData(data: Any?) {
        super.bindData(data)

        data as VideoDetail

        tv_title.text = data.videoInfo?.title

        tv_introduce.text = data.videoInfo?.info

        tv_video_price.text = "${data.videoInfo?.gold}金币购买"


        changeLike()
        changeCollection()



        if (!data.videoCut.isNullOrEmpty()) {
            tv_capture_label.visibility = View.VISIBLE
            rcv_capture.visibility = View.VISIBLE
            captureAdapter.data.addAll(data.videoCut!!)
            captureAdapter.notifyDataSetChanged()
        }else{
            tv_capture_label.visibility = View.GONE
            rcv_capture.visibility = View.GONE

        }

    }

    private fun changeLike(){
        var data = data as VideoDetail

        if (data.isLike==1){
            img_like.setImageDrawable(context.resources.getDrawable(R.mipmap.like_sel))
        }else{
            img_like.setImageDrawable(context.resources.getDrawable(R.mipmap.like_nor))
        }
    }
    private fun changeCollection(){
        var data = data as VideoDetail
        if (data.isCollection==1){
            img_collection.setImageDrawable(context.resources.getDrawable(R.mipmap.collection_sel))
        }else{
            img_collection.setImageDrawable(context.resources.getDrawable(R.mipmap.collection_nor))
        }

    }

    private fun doLike(){
        var data = data as VideoDetail

        videoService.doLikeVideo(data.videoId, ServiceCallback{
            code, _ ->
            if (code==BaseJson.CODE_SUCCESS){
                data.isLike =  data.isLike.xor(1)
                changeLike()
            }
        })
    }

    private fun doCollection(){
        var data = data as VideoDetail

        videoService.doCollection(data.videoId,ServiceCallback{
            code, _ ->
            if (code==BaseJson.CODE_SUCCESS){
                data.isCollection =  data.isCollection.xor(1)
                changeCollection()
            }
        })
    }
}