package com.jyt.video.video.widget

import android.content.Context
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.R
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.video.entity.VideoDetail
import kotlinx.android.synthetic.main.jz_layout_std_custom.view.*
import java.util.logging.Handler
import cn.jzvd.JZUtils.getWindow
import android.os.Build
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.constraint.ConstraintSet
import android.widget.SeekBar
import cn.jzvd.JZUtils
import com.commit451.nativestackblur.NativeStackBlur
import com.jyt.video.common.util.NetSpeed
import com.jyt.video.common.util.NetSpeedTimer
import com.orhanobut.logger.Logger
import com.wingjay.blurimageviewlib.FastBlurUtil
import kotlinx.android.synthetic.main.act_width_draw.view.*
import kotlinx.android.synthetic.main.include_end_free_watch.view.*
import java.math.BigDecimal
import java.math.RoundingMode


class CustomJzvdStd : JzvdStd {

    companion object{
        val EVENT_BUY_VIDEO = "EVENT_BUY_VIDEO"
        val EVENT_BUY_VIP = "EVENT_BUY_VIP"
        val EVENT_PLAYING = "EVENT_PLAYING"
        val EVENT_PAUSE = "EVENT_PAUSE"
        val EVENT_TO_LOGIN = "EVENT_TO_LOGIN"
    }

    var playerStateListener:PlayerStateListener? = null

    var videoDetail:VideoDetail? = null

    var firstPlayAD = false

    internal var isEndFreedTime = false
    lateinit var vipTimer:TimeHelper

    var activity:Activity?=null

    var mNetSpeedTimer: NetSpeedTimer? = null

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    var netSpeedHandler =  object :android.os.Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val speed = msg?.obj as String
            //打印你所需要的网速值，单位默认为kb/s
            var value = ""
            if (speed.toDouble()>1024){
                value = BigDecimal(speed.toDouble()/1024).setScale(1,RoundingMode.HALF_UP).toString()
                value = value + "M/S"
            }else{
                value = speed + "KB/S"
            }
            tv_speed.text = value
        }
    }

    init {
//        WIFI_TIP_DIALOG_SHOWED = true
        setListener()
        initVIPTimer()
        initNetSpeed()
    }


    fun initNetSpeed(){
        //创建NetSpeedTimer实例
        mNetSpeedTimer =  NetSpeedTimer(context,  NetSpeed(), netSpeedHandler).setDelayTime(0).setPeriodTime(500);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer?.startSpeedTimer();

    }


    override fun setAllControlsVisiblity(
        topCon: Int,
        bottomCon: Int,
        startBtn: Int,
        loadingPro: Int,
        thumbImg: Int,
        bottomPro: Int,
        retryLayout: Int
    ) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, thumbImg, bottomPro, retryLayout)

        if (loadingProgressBar.visibility==View.VISIBLE){
            showLoading(true)
        }else{
            showLoading(false)
        }
    }


    public fun initWithData(){
//        var image = "http://pic.sc.chinaz.com/files/pic/pic9/201901/zzpic16093.jpg"
//        Glide.with(this).load(data.ad?.before?.img).into(img_ad_before)
//        Glide.with(this).load(image).into(img_ad_before)
//        Glide.with(this).load(image).into(img_ad_pause)
        firstPlayAD = false

        isEndFreedTime = false

        vipTimer.setTime(videoDetail?.adTime?.toInt()?:0)

        tv_free_play_hint.text = "试看${videoDetail?.feeLook}秒结束，观看完整版请开通vip"
        btn_buy_video.text = "${videoDetail?.price}元原价购买单片"
        tv_validity.text = "单点付费${videoDetail?.buyTimeExists}小时内有效"

        img_ad_before.setOnClickListener(this)
        img_ad_pause.setOnClickListener(this)

        var options = RequestOptions().centerCrop()
        options.error(R.mipmap.ad_holder)
        options.placeholder(R.mipmap.ad_holder)
        Glide.with(this).load(videoDetail?.ad?.before?.img).apply(options).into(img_ad_before)
        Glide.with(this).load(videoDetail?.ad?.pause?.img).apply(options).into(img_ad_pause)

        fl_end_free.visibility = View.GONE
        fl_before.visibility = View.GONE
        img_ad_pause.visibility = View.GONE
        img_ad_before.visibility = View.GONE

    }

    private fun setListener(){
        replay_group.setOnClickListener(this)
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


    override fun startVideo() {
        var beforeAD = videoDetail?.ad?.before
        //有广告
        if (beforeAD!=null){
            //未播放广告
            if (!firstPlayAD){
//                        if (videoDetail?.isVip==false)
                showBeforeAD()
                firstPlayAD = true
                return
            }
        }else{
            firstPlayAD = true
        }
        super.startVideo()
    }

    override fun onClick(v: View?) {
        when(v){
            replay_group->{
                hideBuyVip()
                isEndFreedTime = false
                startVideo()
            }
            startButton->{

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
                    ToastUtil.showShort(context,"您还不是VIP会员")
//                    if (UserInfo.isLogin()){
//                        //提醒开通vip
//                        ARouter.getInstance().build("/recharge/member").navigation()
//                    }else{
//                        ARouter.getInstance().build("/login/index").navigation()
//
//                    }

                }
            }
            btn_buy_mumber->{
                if (UserInfo.isLogin()) {
                    playerStateListener?.onStateEventChange(EVENT_BUY_VIP)

                }else{
                    playerStateListener?.onStateEventChange(EVENT_TO_LOGIN)
//                    ARouter.getInstance().build("/login/index").navigation()
                }
            }
            btn_buy_video->{
                playerStateListener?.onStateEventChange(EVENT_BUY_VIDEO)
            }
            img_ad_before->{
                if (videoDetail?.ad?.before?.url?.isNotEmpty()==true) {
                    ARouter.getInstance().build("/web/index")
                        .withString("url", videoDetail?.ad?.before?.url).navigation()
                }
            }
            img_ad_pause->{
                if (videoDetail?.ad?.pause?.url?.isNotEmpty()==true) {
                    ARouter.getInstance().build("/web/index")
                        .withString("url", videoDetail?.ad?.pause?.url).navigation()
                }

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
                if (freeTime <= position) {
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

    //恢复正常屏幕
    override fun setScreenNormal() {


        var constraintSet =  ConstraintSet()
        constraintSet.clone(cl)
        constraintSet.constrainPercentWidth(R.id.img_ad_before,0.8f)
        constraintSet.constrainPercentWidth(R.id.img_ad_pause,0.8f)
        constraintSet.applyTo(cl)
        super.setScreenNormal()
        backButton.visibility = View.VISIBLE

        activity?.let {
            showNavigationBar(it)
        }

    }

    override fun setScreenFullscreen() {
        var constraintSet =  ConstraintSet()
        constraintSet.clone(cl)
        constraintSet.constrainPercentWidth(R.id.img_ad_before,0.5f)
        constraintSet.constrainPercentWidth(R.id.img_ad_pause,0.5f)
        constraintSet.applyTo(cl)
        super.setScreenFullscreen()

        activity?.let {
            hideNavigationBar(it)
        }


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

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        super.onStopTrackingTouch(seekBar)
        setAllControlsVisiblity(
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE
        )
    }

    override fun onStatePreparing() {
        super.onStatePreparing()

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


            startButton.visibility = View.GONE
        }
    }

    public interface PlayerStateListener{
        fun onStateEventChange(event:String)
    }



    private fun showBeforeAD(){
        if(videoDetail?.ad?.before?.img?.isNotEmpty()==true) {
            fl_before.visibility = View.VISIBLE
            img_ad_before.visibility = View.VISIBLE
            vipTimer.start()
        }

    }

    private fun hideBeforeAD(){
        fl_before.visibility = View.GONE
        img_ad_before.visibility = View.GONE

    }

    private fun showPauseAD(){
        if(videoDetail?.ad?.pause?.img?.isNotEmpty()==true){
            img_ad_pause.visibility = View.VISIBLE
        }
    }
    private fun hidePauseAD(){
        img_ad_pause.visibility = View.GONE
    }

    public fun showBuyVip(){

        if (fl_end_free.visibility==View.GONE) {
            var bitmap = textureView.bitmap
            var blurBitmap = FastBlurUtil.doBlur(bitmap, 80, true)
//        var blurBitmap =  NativeStackBlur.process(bitmap, 80);
            blurImage.setImageBitmap(blurBitmap)
        }
//        this.setDrawingCacheEnabled(true);
//        var bitmap = Bitmap.createBitmap(this.getDrawingCache());
//        //如果不调用这个方法，每次生成的bitmap相同
//        this.setDrawingCacheEnabled(false);
        fl_end_free.visibility = View.VISIBLE
    }

    public fun hideBuyVip(){
        fl_end_free.visibility = View.GONE

    }


    /**
     * 隐藏虚拟按键，并且全屏
     */
    fun hideNavigationBar(activity: Activity) {
        val uiOptions = activity.window.decorView.systemUiVisibility
        var newUiOptions = uiOptions

        val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        //之前如果是IMMERSIVE状态，就不用再隐藏了
        if (!isImmersiveModeEnabled) {

            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }

            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }

            activity.window.decorView.systemUiVisibility = newUiOptions
        }
    }

    /**
     * 显示虚拟按键
     */
    fun showNavigationBar(activity: Activity) {

        val uiOptions = activity.window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        //之前如果是IMMERSIVE状态，就显示NavigationBar
        if (isImmersiveModeEnabled) {

            //先取 非 后再 与， 把对应位置的1 置成0，原本为0的还是0

            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
            }

            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
            }

            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            activity.window.decorView.systemUiVisibility = newUiOptions

        }
    }



    override fun onPrepared() {
        super.onPrepared()
    }

    public fun showLoading(show:Boolean){
        if (show){

            loading_group.visibility = View.VISIBLE
            loading.visibility = View.VISIBLE
            tv_speed.visibility = View.VISIBLE
        }else{
            loading_group.visibility = View.INVISIBLE
            loading.visibility = View.INVISIBLE
            tv_speed.visibility = View.INVISIBLE



        }
    }


    /**
     * 通过canvas复制view的bitmap
     *
     * @param view
     * @return
     */
    private fun copyByCanvas2( view:View) :Bitmap{
        var width = view.getMeasuredWidth();
        var height = view.getMeasuredHeight();
        var bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        var canvas =  Canvas(bp);
        view.draw(canvas);
        canvas.save();
        return bp;
    }

    /**
     * 通过drawingCache获取bitmap
     *
     * @param view
     * @return
     */
    private fun convertViewToBitmap2( view:View):Bitmap {
        view.setDrawingCacheEnabled(true);
        var bitmap = Bitmap.createBitmap(view.getDrawingCache());
        //如果不调用这个方法，每次生成的bitmap相同
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    override fun showProgressDialog(
        deltaX: Float,
        seekTime: String?,
        seekTimePosition: Long,
        totalTime: String?,
        totalTimeDuration: Long
    ) {
        var deltaX = (deltaX * 0.5).toFloat()
        val totalTimeDuration = duration
        mSeekTimePosition =
            (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth).toInt().toLong()
        if (mSeekTimePosition > totalTimeDuration)
            mSeekTimePosition = totalTimeDuration
        val seekTime = JZUtils.stringForTime(mSeekTimePosition)
        val totalTime = JZUtils.stringForTime(totalTimeDuration)

        super.showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration)

    }

}
