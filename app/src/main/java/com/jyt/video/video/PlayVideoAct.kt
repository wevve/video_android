package com.jyt.video.video

import android.Manifest
import android.graphics.Color
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
import com.jyt.video.common.util.NetWorkUtil
import com.jyt.video.common.util.StatusBarUtil
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
import com.jyt.video.video.entity.CommentItem
import com.jyt.video.video.entity.Gift
import com.jyt.video.video.entity.ThumbVideo
import com.jyt.video.video.entity.VideoDetail
import com.jyt.video.video.util.DownLoadHelper
import com.jyt.video.video.util.JZMediaExo
import com.jyt.video.video.widget.CustomJzvdStd
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_play_video.*
import kotlinx.android.synthetic.main.layout_video_list_empty.*
import kotlinx.android.synthetic.main.vh_cache_video_item.view.*
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
//    lateinit var commentAdapter: VideoDetailAdapter

    var videoDetail:VideoDetail? = null

    var videoId:Long =0

    var ad:Advertising?=null


    var refreshVideo = true

    var videoCommentLastId:Long? = 0
    //本地地址
    var localPath:String? = null
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

        StatusBarUtil.setStatusBarColor(this,Color.BLACK)
        StatusBarUtil.setStatusBarDarkTheme(this,false)

        hideToolbar()
        initRcv()
        setListener()
        getData()
        getGiftList()
        getCommentData(null)
    }



    private fun setListener(){
        tv_send.setOnClickListener(this)
        img_close.setOnClickListener(this)
//        img_close.setOnClickListener(this)
        refresh_layout_detail.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onRefreshing() {
                getData()

            }

            override fun onLoadingMore() {

//                getCommentData()
                refresh_layout_detail.refreshComplete()
            }
        })

//        refresh_layout_comment.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
//            override fun onRefreshing() {
//                getCommentData(null)
//
//            }
//
//            override fun onLoadingMore() {
//                refresh_layout_comment.refreshComplete()
//            }
//        })

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

                if (introduceAdapter?.data?.isEmpty() || p0>=introduceAdapter?.data.size){
                    return 2
                }else {
                    var data = introduceAdapter?.data?.get(p0)

                    return when (data) {
                        is VideoDetail,
                        is Banner,
                        is VideoGroupTitle,
                        is Advertising,
                        is VideoType ,
                        is CommentItem-> {
                            2
                        }
                        else -> {
                            1
                        }
                    }
                }
            }
        }
        rcv_introduce.layoutManager = introduceLayoutManager

//        commentAdapter = VideoDetailAdapter()

//        rcv_comment.adapter = commentAdapter
//        rcv_comment.layoutManager = LinearLayoutManager(this)


    }

    private fun getData(){
        videoService.getVideoDetail(videoId, ServiceCallback{
                code, data ->
            videoService.videoHorAd(3,ServiceCallback{
                    _,ad->
                this@PlayVideoAct.ad = ad
                if (data!=null){
                    data.videoId = videoId
                    videoDetail = data

                    setupView(data,ad)
                }

                getCommentData(null)
            })

        })
    }

    private fun setupView(data:VideoDetail,ad:Advertising?){
        introduceAdapter.data.clear()
        if (ad!=null && ad.img?.isEmpty()==false){
            introduceAdapter.data.add(ad)
        }
        introduceAdapter.data.add(data)

        if (data.guess!=null && data.guess?.isNotEmpty()==true){

            introduceAdapter.data.add(VideoGroupTitle("猜你喜欢"))

            if (data.guess!!.size>6){
                data.guess = data.guess!!.subList(0,6)
            }
            introduceAdapter.data.addAll(data.guess!!)

        }

        introduceAdapter.notifyDataSetChanged()

        initVideo(data)


    }
    private fun initVideo(data:VideoDetail){
        if (!refreshVideo){
            return
        }
        refreshVideo = false
//        var url = "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4"


//        data?.videoInfo?.url = "https://v3.mjshcn.com:987/20190429/5hgQbFzH/index.m3u8"
        var url = data?.videoInfo?.url

        //竖屏地址
//        url = "http://flv.bn.netease.com/ee82c1205e5c4606be9f9e17f8a470dab6419091502a9a31d71edd012f07a9c15ec05354a2952c502b841a688b9d75393c212a3aee7ee72fb4cfc85c3f27a96beeda2897808f266940f5678bc81efbdefcf43d15cbc5bf67a7996e7856bdb6efcea3f511c4e62f5e0a1f75701db4bbb232b0db70d51343a6.mp4"
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
            if (it){
                if ((data as VideoDetail)?.videoInfo?.url?.contains(".m3u8")==true){
//                    ToastUtil.showShort(itemView.context,"暂不支持此格式")
                    var localPath = DownLoadHelper.getInstance().getDownLoadFinishVideoLocalPath(url?:"")

                    if (localPath?.isNotEmpty()==true) {
                        url  = localPath
                    }

                }else{
                    var localPath = DownLoadHelper.getInstance().getDownLoadFinishVideoLocalPath(url?:"")
                    if (localPath?.isNotEmpty()==true) {
                        url  = localPath
                    }
                    if (data.isVip){
                        //创建本地缓存 边下边播
//                        url = App.getProxy().getProxyUrl(url)
                    }else{

                    }
                }

            }else{

            }

            val jzDataSource = JZDataSource(url,data?.videoInfo?.title)
            jzDataSource.looping = true

            JzvdStd.SAVE_PROGRESS = videoDetail?.alreadyBuy==1 ||
                    videoDetail?.isVip==true

            Glide.with(this).asBitmap().load(data?.videoInfo?.thumbnail).into(videoplayer.thumbImageView)
            videoplayer.playerStateListener = this
            videoplayer.setUp(url, data?.videoInfo?.title,JzvdStd.SCREEN_NORMAL ,JZMediaExo(videoplayer));
            videoplayer.videoDetail = videoDetail
            videoplayer.activity = this
            videoplayer.initWithData()

            var isConnectWifi = NetWorkUtil.isWifiConnect(this)
            if (isConnectWifi){
                videoplayer.startButton.callOnClick()
            }
        }
    }

    override fun onStateEventChange(event: String) {
        when(event){
            CustomJzvdStd.EVENT_BUY_VIDEO->{
                videoplayer.setScreenNormal()
                buyVideo()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()

        videoplayer.mNetSpeedTimer?.stopSpeedTimer()
    }

    private fun getCommentData(lastId:Long?){

        commentService.getCommentList(videoId,lastId,ServiceCallback{
                code, data ->
            if (data!=null && data.list?.isNotEmpty()==true){
                if (lastId==null){

//                    commentAdapter.data.clear()
//                    introduceAdapter.data.clear()
                    var iterator = introduceAdapter.data.iterator()
                    while (iterator.hasNext()){
                        var item  = iterator.next()
                        if (item is VideoGroupTitle){
                            if (item.text == "网友评论"){
                                iterator.remove()
                            }
                        }else
                            if (item is CommentItem){
                                iterator.remove()
                            }
                    }

                    introduceAdapter.data.add(VideoGroupTitle("网友评论"))


                }
//                commentAdapter.data.addAll(data.list)
                introduceAdapter.data.addAll(data.list)
//                commentAdapter.notifyDataSetChanged()
                introduceAdapter.notifyDataSetChanged()
                videoCommentLastId = data.list.last()?.id
            }

            refresh_layout_detail.refreshComplete()

//            refresh_layout_comment.refreshComplete()
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
        if (videoDetail?.isVip==true){
            ToastUtil.showShort(this,"您是VIP会员 无需购买")
            return
        }
        if (videoDetail?.videoInfo?.gold==0){
            ToastUtil.showShort(this,"免费视频无需购买")
            return
        }
        if (videoDetail?.alreadyBuy==1){
//            ToastUtil.showShort(this,"您已购买视频")
            ToastUtil.showShort(this,"您已购买视频24小时内无需购买")
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

                        refreshVideo = true
                        setupView(videoDetail!!,ad)


                        var dialog = AlertDialog()
                        dialog.message = "购买成功"
                        dialog.leftBtnText = "确定"
                        dialog.onClickListener = {
                                dialogFragment, _ ->
                            dialogFragment.dismissAllowingStateLoss()
                        }
                        dialog.show(supportFragmentManager,"")

                    }

                    dialogFragment.dismissAllowingStateLoss()

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