package com.jyt.video.video

import android.Manifest
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.jzvd.JZDataSource
import cn.jzvd.JZUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jyt.video.App
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.base.BaseVH
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.entity.CommonTab
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.entity.Advertising
import com.jyt.video.home.entity.Banner
import com.jyt.video.home.entity.VideoGroupTitle
import com.jyt.video.home.entity.VideoType
import com.jyt.video.service.CommentService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.VideoService
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.CommentServiceImpl
import com.jyt.video.service.impl.VideoServiceImpl
import com.jyt.video.service.impl.WalletServiceImpl
import com.jyt.video.video.adapter.VideoDetailAdapter
import com.jyt.video.video.entity.Gift
import com.jyt.video.video.entity.ThumbVideo
import com.jyt.video.video.entity.VideoDetail
import com.jyt.video.video.util.JZMediaExo
import com.jyt.video.video.widget.CustomJzvdStd
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_play_video.*
import kotlinx.android.synthetic.main.vh_introduce_header.*
import me.dkzwm.widget.srl.SmoothRefreshLayout
import java.util.ArrayList

@Route(path = "/video/play")
class PlayVideoAct:BaseAct(), View.OnClickListener, CustomJzvdStd.PlayerStateListener {
    companion object{
        var giftList:ArrayList<Gift>? = null
    }

    lateinit var videoService:VideoService
    lateinit var walletService: WalletService
    lateinit var commentService: CommentService
    lateinit var introduceAdapter: VideoDetailAdapter
    lateinit var commentAdapter: VideoDetailAdapter

    var videoDetail:VideoDetail? = null

    var videoId:Long =0


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
        walletService = WalletServiceImpl()
        hideToolbar()
        initRcv()
        initTab()
        setListener()
        getData()
        getGiftList()
        getCommentData(null)
    }



    private fun setListener(){
        tv_send.setOnClickListener(this)
        img_close.setOnClickListener(this)
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
                return
            }



        }
        if (videoDetail==null){
            return
        }
        when(v){
            tv_send->{
                addComment(input_comment.text.toString())
            }

        }

    }

    private fun initRcv(){
        introduceAdapter = VideoDetailAdapter()
        introduceAdapter.activity = this
        introduceAdapter.onTriggerListener= object :BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>{
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                when(event){
                    "BUY"->{
                        buyVideo()
                    }
                }
            }

        }
        rcv_introduce.adapter = introduceAdapter
        var introduceLayoutManager = GridLayoutManager(this,2)
        introduceLayoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(p0: Int): Int {

                var data = introduceAdapter?.data?.get(p0)

                return when(data){
                    is VideoDetail,
                    is Banner,
                    is VideoGroupTitle,
                    is Advertising,
                    is VideoType->{
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
                videoDetail = data
                setupView(data)
            }
            refresh_layout_detail.refreshComplete()
        })
    }

    private fun setupView(data:VideoDetail){
        introduceAdapter.data.clear()
        introduceAdapter.data.add(data)

        if (data.guess!=null && data.guess?.isNotEmpty()==true){

            introduceAdapter.data.add(VideoGroupTitle("猜你喜欢"))

            introduceAdapter.data.addAll(data.guess!!)

        }

        introduceAdapter.notifyDataSetChanged()

        initVideo(data)


    }
    private fun initVideo(data:VideoDetail){
//        var url = "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4"
//
        var url = data?.videoInfo?.url
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
            if (it){
                if (data.isVip){
                    //创建本地缓存 边下边播
                    url = App.getProxy().getProxyUrl(url)
                }else{

                }
            }else{

            }

            val jzDataSource = JZDataSource(url)
            jzDataSource.looping = true

            JzvdStd.SAVE_PROGRESS = videoDetail?.alreadyBuy==1 ||
                    videoDetail?.isVip==true


            videoplayer.playerStateListener = this
            videoplayer.setUp(url, "",JzvdStd.SCREEN_NORMAL );
            videoplayer.videoDetail = videoDetail
            videoplayer.initWithData()
        }
    }

    override fun onStateEventChange(event: String) {
        when(event){
            CustomJzvdStd.EVENT_BUY_VIDEO->{
                buyVideo()
            }
        }
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
            refresh_layout_comment.refreshComplete()
        })

    }


    private fun addComment(comment:String){
        if (!UserInfo.isLogin()){
            ToastUtil.showShort(this,"请先登录")
            ARouter.getInstance().build("/login/index").navigation()
            return
        }

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


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }


    private fun buyVideo(){
        if (!UserInfo.isLogin()){
            ToastUtil.showShort(this,"请先登录")
            ARouter.getInstance().build("/login/index").navigation()
            return
        }
        if (videoDetail?.alreadyBuy==1){
            ToastUtil.showShort(this,"您已购买视频")
            return
        }
        var dialog  = AlertDialog()
        dialog.message = "是否购买视频"
        dialog.rightBtnText = "取消"
        dialog.leftBtnText = "确定"
        dialog.onClickListener={
            dialogFragment, s ->
            if (s=="确定"){
                videoService.buyVideo(videoDetail?.videoId!!,ServiceCallback{
                        code, data ->
                    if (code==BaseJson.CODE_SUCCESS){
                        videoDetail?.alreadyBuy = 1

                        setupView(videoDetail!!)

                        dialogFragment.dismissAllowingStateLoss()

                        var dialog = AlertDialog()
                        dialog.message = "购买成功"
                        dialog.leftBtnText = "确定"
                        dialog.onClickListener = {
                            dialogFragment, _ ->
                            dialogFragment.dismissAllowingStateLoss()
                        }
                        dialog.show(supportFragmentManager,"")

                    }
                })
            }else{
                dialogFragment.dismissAllowingStateLoss()
            }

        }

        dialog.show(supportFragmentManager,"")
    }

    private fun getGiftList(){
        walletService.getGiftList(ServiceCallback{
            code, data ->
            giftList = data
        })
    }
}