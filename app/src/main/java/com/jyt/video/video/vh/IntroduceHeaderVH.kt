package com.jyt.video.video.vh

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.service.impl.WalletServiceImpl
import com.jyt.video.video.CacheVideoAct
import com.jyt.video.video.adapter.VideoCaptureAdapter
import com.jyt.video.video.dialog.AwardDialog
import com.jyt.video.video.entity.Gift
import com.jyt.video.video.entity.VideoDetail
import kotlinx.android.synthetic.main.vh_introduce_header.*

class IntroduceHeaderVH(viewGroup: ViewGroup) :BaseVH<Any>(LayoutInflater.from(viewGroup.context).inflate(R.layout.vh_introduce_header,viewGroup,false)){


    var captureAdapter:VideoCaptureAdapter = VideoCaptureAdapter()

    var videoService:VideoService = VideoServiceImpl()

    var walletService:WalletService = WalletServiceImpl()

    init {
        rcv_capture.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rcv_capture.adapter = captureAdapter

        img_collection.setOnClickListener(this)
        img_like.setOnClickListener(this)

        img_show_all_info.setOnClickListener(this)

        img_dasang.setOnClickListener(this)
        tv_dasang.setOnClickListener(this)
        v_dasang.setOnClickListener(this)

        img_jinbi.setOnClickListener(this)
        tv_video_price.setOnClickListener(this)
        v_goumai.setOnClickListener(this)

        img_download.setOnClickListener(this)

        rcv_capture.setFocusableInTouchMode(false); //设置不需要焦点
        rcv_capture.requestFocus();
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
          else->{
              if (!UserInfo.isLogin()){
                  ToastUtil.showShort(context,"请先登录")
                  ARouter.getInstance().build("/login/index").navigation()
                  return
              }else{
                  when(v){
                      img_download->{
                          if ((data as VideoDetail)?.videoInfo?.url?.endsWith(".m3u8")==true){
                              ToastUtil.showShort(itemView.context,"暂不支持此格式")
                              return
                          }

                          if ((data as VideoDetail).isVip){

                              CacheVideoAct.startNew(data as VideoDetail)
                              var dialog = AlertDialog()
                              dialog.message = "已加入缓存"
                              dialog.leftBtnText = "确定"
                              dialog.onClickListener = {
                                  dialogFragment, s ->
                                  dialogFragment.dismissAllowingStateLoss()
                              }
                              dialog.show(activity?.supportFragmentManager,"")
//                            ARouter.getInstance().build("/video/cache")
//                                .withSerializable("videoDetail",data as VideoDetail)
//                                .navigation()
                          }else{
                              var dialog = AlertDialog()
                              dialog.message="只有会员才能下载"
                              dialog.leftBtnText = "确定"
                              dialog.onClickListener={
                                  dialogFragment, s ->
                                  dialogFragment.dismissAllowingStateLoss()
                              }
                              dialog.show(activity?.supportFragmentManager,"")
                          }
                      }
                      img_collection->{
                          doCollection()
                      }
                      img_like->{
                          doLike()
                      }
                      img_dasang,tv_dasang,v_dasang->{
                          var dialog= AwardDialog()
                          dialog.onGiftClick={
                              gift ->
                              dialog.dismiss()
                              var comfirm = AlertDialog()
                              comfirm.message = "是否赠送${gift.name}"
                              comfirm.leftBtnText = "确定"
                              comfirm.rightBtnText = "取消"
                              comfirm.onClickListener = {
                                  dialogFragment, s ->
                                  if (s=="确定") {
                                      sendGift(gift)
                                  }
                                  dialogFragment.dismissAllowingStateLoss()
                              }
                              comfirm.show(activity?.supportFragmentManager,"")
                          }
                          dialog.show(activity?.supportFragmentManager,"")

                      }
                      img_jinbi,tv_video_price,v_goumai->{
                          onTriggerListener?.onTrigger(this,"BUY")
                      }
                      else->{
                        super.onClick(v)

                    }
                  }
              }

          }
        }

    }
    override fun bindData(data: Any?) {
        super.bindData(data)

        data as VideoDetail

        tv_title.text = data.videoInfo?.title


        if (data.videoInfo?.info.isNullOrBlank()){
            tv_introduce.visibility = View.GONE
            img_show_all_info.visibility = View.GONE

        }else{
            tv_introduce.visibility = View.VISIBLE
            img_show_all_info.visibility = View.VISIBLE
            tv_introduce.text = data.videoInfo?.info
        }

        tv_video_price.text = "${data.videoInfo?.gold}金币购买"


        changeLike()
        changeCollection()



        if (!data.videoCut.isNullOrEmpty()) {
            tv_capture_label.visibility = View.VISIBLE
            rcv_capture.visibility = View.VISIBLE
            captureAdapter.data.addAll(data.videoCut!!)
            captureAdapter.activity = activity
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


    private fun sendGift(gift: Gift){
        data as VideoDetail
        walletService.sendGift((data as VideoDetail)?.videoId!!,gift.id,ServiceCallback{
            code, data ->
            if (code==BaseJson.CODE_SUCCESS){
                ToastUtil.showShort(itemView.context,"赠送成功")
            }
        })

    }
}