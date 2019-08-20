package com.jyt.video.video.util;

import android.support.annotation.Nullable;
import com.arialyy.annotations.Download;
import com.arialyy.annotations.M3U8;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadReceiver;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.download.m3u8.IBandWidthUrlConverter;
import com.arialyy.aria.core.download.m3u8.ITsMergeHandler;
import com.arialyy.aria.core.download.m3u8.IVodTsUrlConverter;
import com.arialyy.aria.core.download.m3u8.M3U8KeyInfo;
import com.arialyy.aria.core.inf.AbsEntity;
import com.arialyy.aria.util.ALog;
import com.jyt.video.App;
import com.jyt.video.common.Constant;
import com.jyt.video.common.db.bean.Video;
import com.orhanobut.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static cn.jzvd.Jzvd.TAG;

public class DownLoadHelperJ {

    ArrayList mData = new ArrayList<AbsEntity>();
    ConcurrentHashMap mTask = new ConcurrentHashMap<String, AbsEntity>();
    ArrayList<Video> videos  = new ArrayList<>();

    private static DownLoadHelperJ helper = new DownLoadHelperJ();
    private static Object register;
    public static DownLoadHelperJ getInstance() {
        return helper;
    }

    public void init() {
//        App.getAppDataBase().videoDao().loadAllVideos()
        getAria().register();
        List temps = getAria().getTotalTaskList();
        if (temps != null && !temps.isEmpty()) {
            mData.addAll(temps);
        }

    }

    public void startMission(Video video) {
        String savePath = "";
        if (video.getUrl().contains(".m3u8")) {
            String mUrl = video.getUrl();

            String fileName =
                    mUrl.substring(mUrl.lastIndexOf("/") + 1, mUrl.lastIndexOf("."));

            savePath = new File(Constant.getAppCacheFile().getAbsolutePath(), fileName).getAbsolutePath();

            Logger.d(savePath);
            getAria().load(mUrl).asM3U8()
                    .setBandWidthUrlConverter(new IBandWidthUrlConverter() {
                        @Override
                        public String convert(String bandWidthUrl) {
                            int index = mUrl.lastIndexOf("/");
                            return mUrl.substring(0, index + 1) + bandWidthUrl;
                        }
                    })
                    .setTsUrlConvert(new IVodTsUrlConverter() {
                        @Override
                        public List<String> convert(String m3u8Url, List<String> tsUrls) {
                            int index = m3u8Url.lastIndexOf("/");
                            String parentUrl = m3u8Url.substring(0, index + 1);
                            List<String> newUrls = new ArrayList<>();
                            for (String url : tsUrls) {
                                newUrls.add(parentUrl + url);
                            }

                            return newUrls;
                        }
                    })
                    .setMergeHandler(new ITsMergeHandler() {
                        @Override
                        public boolean merge(@Nullable M3U8KeyInfo keyInfo, List<String> tsPath) {
                            ALog.d(TAG, "合并TS....");
                            return false;
                        }
                    })
                    .merge(true)
                    .start();

        } else {
            String mUrl = video.getUrl();

            String fileName =
                    mUrl.substring(mUrl.lastIndexOf("/") + 1, mUrl.lastIndexOf("."));
            String fileNameAndSuffix = mUrl.substring(mUrl.lastIndexOf("/") + 1, mUrl.length());
            savePath = Constant.getAppCacheFile().getAbsolutePath() + "/" + fileName + "/" + fileNameAndSuffix;

            getAria().load(video.getUrl()).useServerFileName(true)
                    .setFilePath(savePath, true).start();

        }


    }

    public void stopMission(Video video) {
        getAria().load(video.getUrl()).stop();
    }

    public void delMission(Video video) {
        getAria().load(video.getUrl()).stop();
        getAria().load(video.getUrl()).cancel(true);
        App.Companion.getAppDataBase().videoDao().deleteVideos(video);

    }

    public  DownloadReceiver getAria() {

        if (register==null){
            return Aria.download(this);

        }else {
            return Aria.download(register);

        }
    }
    public static void setRegister(Object register){
        DownLoadHelperJ.register = register;
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

    @NotNull
    public Collection<Video> getVideoList() {

        return null;
    }
}
