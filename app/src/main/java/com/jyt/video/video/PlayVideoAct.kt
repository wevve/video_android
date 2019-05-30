package com.jyt.video.video

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.jzvd.JZDataSource
import cn.jzvd.JZUtils
import cn.jzvd.JzvdStd
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.entity.CommonTab
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.service.CommentService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.impl.CommentServiceImpl
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.video.adapter.VideoDetailAdapter
import com.jyt.video.video.entity.VideoDetail
import com.jyt.video.video.util.JZMediaExo
import com.jyt.video.video.widget.CustomJzvdStd
import kotlinx.android.synthetic.main.act_play_video.*
import me.dkzwm.widget.srl.SmoothRefreshLayout
import java.util.ArrayList

@Route(path = "/video/play")
class PlayVideoAct:BaseAct(), View.OnClickListener, CustomJzvdStd.PlayerStateListener, TimeHelper.TimerListener {


    lateinit var videoService:VideoService
    lateinit var commentService: CommentService
    lateinit var introduceAdapter: VideoDetailAdapter
    lateinit var commentAdapter: VideoDetailAdapter

    var videoDetail:VideoDetail? = null

    var videoId:Long =0

    lateinit var timer:TimeHelper

    var videoCommentLastId:Long? = 0
    override fun initView() {
        videoId = intent.getLongExtra("videoId",0)

        if (videoId==0L){
            ToastUtil.showShort(this,"无法播放该视频")
            finish()
            return
        }

        videoService = VideoServiceImpl()
        commentService = CommentServiceImpl()
        initTimer()
        hideToolbar()
        initRcv()
        initTab()
        setListener()
        getData()
        getCommentData(null)
    }

    private fun initTimer(){
        timer = TimeHelper(tv_not_member_timer)
        timer.needRecord = false
        timer.setOriText("60")
        timer.setTimerText("%s")
        timer.setTimerListener(this)
    }
    override fun timeStateChangeListener(state: String?) {
        when(state){
            "end"->{
                fl_before.visibility = View.GONE
            }
        }
    }

    private fun setListener(){
        img_close.setOnClickListener(this)
        img_ad_before.setOnClickListener(this)
        img_ad_pause.setOnClickListener(this)

        refresh_layout_detail.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData()

            }

            override fun onLoadingMore() {
                refresh_layout_detail.refreshComplete()
            }
        })

        refresh_layout_comment.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getCommentData(null)

            }

            override fun onLoadingMore() {
                refresh_layout_comment.refreshComplete()
            }
        })

        input_comment.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEND){
                addComment(input_comment.text.toString())
            }
            false
        }

    }
    override fun onClick(v: View?) {
        when(v){
            img_close->{
                finish()
            }

        }
        if (videoDetail==null){
            return
        }
        when(v){
            img_ad_before->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.before?.url).navigation()
            }
            img_ad_pause->{
                ARouter.getInstance().build("/web/index").withString("url",videoDetail?.ad?.pause?.url).navigation()
                img_ad_pause.visibility = View.GONE
            }
        }
    }

    private fun initRcv(){
        introduceAdapter = VideoDetailAdapter()
        rcv_introduce.adapter = introduceAdapter
        var introduceLayoutManager = GridLayoutManager(this,2)
        introduceLayoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(p0: Int): Int {

                var data = introduceAdapter?.data?.get(p0)

                return when(data){
                    is VideoDetail,
                    is VideoGroupTitle,
                    is VideoType ->{
                        2
                    }
                    else->{
                        1
                    }
                }
            }

        }
        rcv_introduce.layoutManager = introduceLayoutManager

        commentAdapter = VideoDetailAdapter()

        rcv_comment.adapter = commentAdapter
        rcv_comment.layoutManager = LinearLayoutManager(this)
    }

    private fun getData(){
        videoService.getVideoDetail(videoId, ServiceCallback{
         code, data ->
            if (data!=null){
                data.videoId = videoId
                setupView(data)
                initVideo(data)
            }
        })
    }
    private fun setupView(data:VideoDetail){
        introduceAdapter.data.add(data)
        introduceAdapter.notifyDataSetChanged()

    }
    private fun initVideo(data:VideoDetail){

        val jzDataSource = JZDataSource(data.videoInfo?.url)
        jzDataSource.looping = true
//        JZUtils.clearSavedProgress(this,data.videoInfo?.url)
        videoplayer.playerStateListener = this
        videoplayer.fullscreenButton.visibility = View.INVISIBLE
        videoplayer.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL , JZMediaExo(videoplayer));


        Glide.with(this).load(data.ad?.before?.img).into(img_ad_before)
        timer.start()
    }


    private fun initTab(){
        tab_layout.setTabData(
            arrayListOf(CommonTab("简介"),
            CommonTab("评论")) as ArrayList<CustomTabEntity>)

        tab_layout.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                if (position==0){
                    group_comment.visibility = View.GONE
                    refresh_layout_detail.visibility = View.VISIBLE
                }else{
                    group_comment.visibility = View.VISIBLE
                    refresh_layout_detail.visibility = View.GONE
                }
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun onStateEventChange(event: String) {
        when (event) {
            "PLAYING" -> {
                img_ad_pause.visibility = View.GONE
            }
            "PAUSE" -> {
                img_ad_pause.visibility = View.VISIBLE
            }
        }
    }

    private fun getCommentData(lastId:Long?){

        commentService.getCommentList(videoId,lastId,ServiceCallback{
            code, data ->
            if (data!=null && data.list.isNotEmpty()){
                if (lastId==null){
                    commentAdapter.data.clear()
                }
                commentAdapter.data.addAll(data.list)
                commentAdapter.notifyDataSetChanged()
                videoCommentLastId = data.list.last()?.id
            }
        })

    }


    private fun addComment(comment:String){
        if (comment.isNullOrEmpty()){
            ToastUtil.showShort(this,"请输入评论")
            return
        }

        commentService.addComment(comment,videoId,ServiceCallback{
            code, data ->
            if (code==BaseJson.CODE_SUCCESS){
                input_comment.setText("")
            }
            getCommentData(null)
        })


    }

    override fun getLayoutId(): Int {
        return R.layout.act_play_video
    }

}