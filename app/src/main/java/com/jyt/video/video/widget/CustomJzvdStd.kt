package com.jyt.video.video.widget

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.view.View
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.video.entity.VideoDetail
import kotlinx.android.synthetic.main.jz_layout_std_custom.view.*
import java.util.logging.Handler

class CustomJzvdStd : JzvdStd {

    companion object{
        val EVENT_BUY_VIDEO = "EVENT_BUY_VIDEO"
        val EVENT_PLAYING = "EVENT_PLAYING"
        val EVENT_PAUSE = "EVENT_PAUSE"
    }

    var playerStateListener:PlayerStateListener? = null

    var videoDetail:VideoDetail? = null

    var firstPlayAD = false

    internal var isEndFreedTime = false
    lateinit var vipTimer:TimeHelper

    var curFeedTime = 0

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setListener()
        initVIPTimer()
    }
    private fun initFeedPlayTimer(){

    }

    public fun initWithData(){
//        var image = "http://pic.sc.chinaz.com/files/pic/pic9/201901/zzpic16093.jpg"
//        Glide.with(this).load(data.ad?.before?.img).into(img_ad_before)
//        Glide.with(this).load(image).into(img_ad_before)
//        Glide.with(this).load(image).into(img_ad_pause)

        btn_buy_video.text = "${videoDetail?.videoInfo?.gold}金币购买"

        img_ad_before.setOnClickListener(this)
        img_ad_pause.setOnClickListener(this)
        Glide.with(this).load(videoDetail?.ad?.before?.img).into(img_ad_before)
        Glide.with(this).load(videoDetail?.ad?.pause?.img).into(img_ad_pause)
    }

    private fun setListener(){
        ll_vip_jump_ad.setOnClickListener(this)
        img_video_play.setOnClickListener(this)


        btn_buy_mumber.setOnClickListener(this)
        btn_buy_video.setOnClickListener(this)
        //前广告 拦截点击
        fl_before.setOnClickListener(this)
        fl_end_free.setOnClickListener(this)
    }

    //定时器
    private fun initVIPTimer(){
        vipTimer = TimeHelper(tv_not_member_timer)
        vipTimer.setTime(5)
        vipTimer.needRecord = false
        vipTimer.setOriText("60")
        vipTimer.setTimerText("%s")
        vipTimer.setTimerListener {
            when(it){
                "end"->{
                    hideBeforeAD()
                    startButton.callOnClick()

                }
            }
        }
    }






    override fun onClick(v: View?) {
        when(v){
            startButton->{
                var beforeAD = videoDetail?.ad?.before
                //有广告
                if (beforeAD!=null){
                    //未播放广告
                    if (!firstPlayAD){
                        showBeforeAD()
                        firstPlayAD = true
                        return
                    }
                }else{
                    firstPlayAD = true
                }
            }
        }
        super.onClick(v)
        when(v){
            img_video_play->{
                startButton.callOnClick()
            }
            img_ad_before->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.before?.url).navigation()
            }
            img_ad_pause->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.pause?.url).navigation()
                img_ad_pause.visibility = View.GONE
            }
            ll_vip_jump_ad->{
                if (videoDetail?.isVip==true){
                    vipTimer.stop()
                }else{
                    //提醒开通vip
                    ARouter.getInstance().build("/recharge/member").navigation()
                }
            }
            btn_buy_mumber->{
                ARouter.getInstance().build("/recharge/member").navigation()
            }
            btn_buy_video->{
                playerStateListener?.onStateEventChange(EVENT_BUY_VIDEO)
            }
            img_ad_before->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.before?.url).navigation()
            }
            img_ad_pause->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.pause?.url).navigation()

            }

        }
    }

    override fun onProgress(progress: Int, position: Long, duration: Long) {
        super.onProgress(progress, position, duration)

        //收费视频
        if((videoDetail?.videoInfo?.gold?:0)!=0){
            //VIP 跟 已购买  跳过判断
            if (videoDetail?.isVip==true || videoDetail?.alreadyBuy==1){

            }else {
                var freeTime = (videoDetail?.feeLook ?: "0").toLong() * 1000
//                freeTime = 5
//                curFeedTime += 1
                if (freeTime <= curFeedTime) {
                    isEndFreedTime = true
                    Jzvd.goOnPlayOnPause()
                }
            }
        }
    }

    override fun onStatePlaying() {
        super.onStatePlaying()
        playerStateListener?.onStateEventChange(EVENT_PLAYING)


        hidePauseAD()



    }


    override fun onStatePause() {
        super.onStatePause()

        if (isEndFreedTime){
            showBuyVip()
        }else {
            showPauseAD()
        }

        playerStateListener?.onStateEventChange(EVENT_PAUSE)
    }

    override fun getLayoutId(): Int {


        return R.layout.jz_layout_std_custom

    }

    override fun updateStartImage() {
        super.updateStartImage()
        if (currentState == Jzvd.CURRENT_STATE_PLAYING) {
            img_video_play.visibility = View.VISIBLE
            img_video_play.setImageResource(R.mipmap.video_pause)
            replayTextView.visibility = View.GONE
        } else if (currentState == Jzvd.CURRENT_STATE_ERROR) {
            img_video_play.visibility = View.INVISIBLE
            replayTextView.visibility = View.GONE
        } else if (currentState == Jzvd.CURRENT_STATE_AUTO_COMPLETE) {
            img_video_play.visibility = View.VISIBLE
            img_video_play.setImageResource(R.mipmap.video_replay)
            replayTextView.visibility = View.VISIBLE
        } else {
            img_video_play.setImageResource(R.mipmap.video_play)
            replayTextView.visibility = View.GONE
        }
    }

    public interface PlayerStateListener{
        fun onStateEventChange(event:String)
    }



    private fun showBeforeAD(){
        fl_before.visibility = View.VISIBLE
        img_ad_before.visibility = View.VISIBLE
        vipTimer.start()
    }

    private fun hideBeforeAD(){
        fl_before.visibility = View.GONE
        img_ad_before.visibility = View.GONE


    }

    private fun showPauseAD(){
        img_ad_pause.visibility = View.VISIBLE
    }
    private fun hidePauseAD(){
        img_ad_pause.visibility = View.GONE
    }

    public fun showBuyVip(){
        fl_end_free.visibility = View.VISIBLE
    }

    public fun hideBuyVip(){
        fl_end_free.visibility = View.GONE

    }



}
