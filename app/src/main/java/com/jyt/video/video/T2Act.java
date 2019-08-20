package com.jyt.video.video;

import android.Manifest;
import android.view.View;
import com.arialyy.annotations.Download;
import com.arialyy.annotations.M3U8;
import com.arialyy.aria.core.download.DownloadTask;
import com.jyt.video.R;
import com.jyt.video.common.base.BaseAct;
import com.jyt.video.common.db.bean.Video;
import com.jyt.video.video.util.DownLoadHelperJ;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;

public class T2Act extends BaseAct implements View.OnClickListener {
    Video m3v;
    Video vv;

    @Override
    public void initView() {
        hideToolbar();

        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
        DownLoadHelperJ.setRegister(this);
        DownLoadHelperJ.getInstance().init();
        m3v = new Video();

//        m3v.setUrl("https://node.imgio.in/demo/birds.m3u8");
        m3v.setUrl("http://y.sqxpd.com/juy-642-C/playlist.m3u8?sign=b59b6d6c7262bad235d76ae4fb12db8ece84b15b699490c36e06427e3f53e6cef6afd48a94f538111f9d0b9478027c966fc435fca745afa2be7547756947e89d");

        vv = new Video();
        vv.setUrl("http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4");


        findViewById(R.id.btn_end).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_start_m3).setOnClickListener(this);
        findViewById(R.id.btn_stop_m3).setOnClickListener(this);
        findViewById(R.id.btn_cancel_m3).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end:
                DownLoadHelperJ.getInstance().stopMission(vv);

                break;
            case R.id.btn_start:
                DownLoadHelperJ.getInstance().startMission(vv);
                break;
            case R.id.btn_start_m3:
                DownLoadHelperJ.getInstance().startMission(m3v);
                break;
            case R.id.btn_stop_m3:
                DownLoadHelperJ.getInstance().stopMission(m3v);
                break;
            case R.id.btn_cancel_m3:
                DownLoadHelperJ.getInstance().delMission(m3v);
                break;
            case R.id.btn_cancel:
                DownLoadHelperJ.getInstance().delMission(vv);
                break;

        }
    }
    @M3U8.onPeerStart
    public void onPeerStart(String m3u8Url, String peerPath, int peerIndex) {
        //ALog.d(TAG, "peer start, path: " + peerPath + ", index: " + peerIndex);
    }

    @M3U8.onPeerComplete
    public void onPeerComplete(String m3u8Url, String peerPath, int peerIndex) {
    }

    @M3U8.onPeerFail
    public void onPeerFail(String m3u8Url, String peerPath, int peerIndex) {
        //ALog.d(TAG, "peer fail, path: " + peerPath + ", index: " + peerIndex);
    }

    @Download.onWait
    public void onWait(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onPre
    public void onPre(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskStart
    public void taskStart(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskRunning
    public void running(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskResume
    public void taskResume(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskStop
    public void taskStop(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskCancel
    public void taskCancel(DownloadTask task) {
        Logger.d(task);

    }

    @Download.onTaskFail
    public void taskFail(DownloadTask task, Exception e) {
        Logger.d(task);

    }

    @Download.onTaskComplete
    public void taskComplete(DownloadTask task) {

        Logger.d(task);
    }
}
