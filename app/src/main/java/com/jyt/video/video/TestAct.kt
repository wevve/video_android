package com.jyt.video.video

import android.Manifest
import android.view.View
import com.arialyy.annotations.Download
import com.arialyy.annotations.M3U8
import com.arialyy.aria.core.download.DownloadTask
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.db.bean.Video
import com.jyt.video.video.util.DownLoadHelper
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_test.*

class TestAct : BaseAct(), View.OnClickListener {
    lateinit var m3v: Video
    lateinit var vv: Video

    override fun onClick(v: View?) {
        when (v) {
            btn_end -> {
                DownLoadHelper.getInstance().stopMission(vv)

            }
            btn_start -> {
                DownLoadHelper.getInstance().startMission(vv)

            }
            btn_cancel->{
                DownLoadHelper.getInstance().delMission(vv)

            }
            btn_start_m3 -> {
                DownLoadHelper.getInstance().startMission(m3v)

            }
            btn_stop_m3 -> {
                DownLoadHelper.getInstance().stopMission(m3v)

            }
            btn_cancel_m3->{
                DownLoadHelper.getInstance().delMission(m3v)


            }

        }
    }

    override fun initView() {
        hideToolbar()

        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {

        }
        DownLoadHelper.getInstance().register= this
        DownLoadHelper.getInstance().init()
        m3v = Video()

//        m3v.url = "https://node.imgio.in/demo/birds.m3u8"
        m3v.url = "http://y.sqxpd.com/juy-642-C/playlist.m3u8?sign=b59b6d6c7262bad235d76ae4fb12db8ece84b15b699490c36e06427e3f53e6cef6afd48a94f538111f9d0b9478027c966fc435fca745afa2be7547756947e89d"

        vv = Video()
        vv.url =
            "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4"


        btn_end.setOnClickListener(this)
        btn_start.setOnClickListener(this)
        btn_start_m3.setOnClickListener(this)
        btn_stop_m3.setOnClickListener(this)
        btn_cancel_m3.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_test
    }

    @M3U8.onPeerStart
      fun onPeerStart(m3u8Url: String, peerPath: String, peerIndex: Int) {
        //ALog.d(TAG, "peer start, path: " + peerPath + ", index: " + peerIndex);
    }

    @M3U8.onPeerComplete
      fun onPeerComplete(m3u8Url: String, peerPath: String, peerIndex: Int) {
    }

    @M3U8.onPeerFail
      fun onPeerFail(m3u8Url: String, peerPath: String, peerIndex: Int) {
        //ALog.d(TAG, "peer fail, path: " + peerPath + ", index: " + peerIndex);
    }

    @Download.onWait
      fun onWait(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onPre
    protected fun onPre(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskStart
      fun taskStart(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskRunning
    protected fun running(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskResume
      fun taskResume(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskStop
      fun taskStop(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskCancel
      fun taskCancel(task: DownloadTask) {
        Logger.d(task)

    }

    @Download.onTaskFail
     fun taskFail(task: DownloadTask, e: Exception) {
        Logger.d(task)

    }

    @Download.onTaskComplete
     fun taskComplete(task: DownloadTask) {

        Logger.d(task)
    }
}
