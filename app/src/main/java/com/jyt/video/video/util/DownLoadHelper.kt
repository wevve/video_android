package com.jyt.video.video.util

import com.arialyy.annotations.Download
import com.arialyy.annotations.M3U8
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadEntity
import com.arialyy.aria.core.download.DownloadGroupEntity
import com.arialyy.aria.core.download.DownloadReceiver
import com.arialyy.aria.core.download.DownloadTask
import com.arialyy.aria.core.download.m3u8.IBandWidthUrlConverter
import com.arialyy.aria.core.download.m3u8.ITsMergeHandler
import com.arialyy.aria.core.download.m3u8.IVodTsUrlConverter
import com.arialyy.aria.core.inf.AbsEntity
import com.arialyy.aria.core.inf.IEntity
import com.jyt.video.App
import com.jyt.video.common.Constant
import com.jyt.video.common.db.bean.Video
import com.orhanobut.logger.Logger
import java.io.File
import java.util.concurrent.ConcurrentHashMap


class DownLoadHelper {


    var mData = ArrayList<AbsEntity>()
    var mTask = ConcurrentHashMap<String, Video>()
    var mPosition = ConcurrentHashMap<String, Int>()
    var register:Any? = null
    lateinit var videos :ArrayList<Video>

    companion object {
        private var helper = DownLoadHelper()
        fun getInstance(): DownLoadHelper {
            return helper
        }
    }

    var triggerUpdate:((task:DownloadTask,position:Int)->Unit)? = null

    public fun init() {
//        App.getAppDataBase().videoDao().loadAllVideos()
        getAria().register()

        getVideoList()

    }

    public fun getAria(): DownloadReceiver {
        return Aria.download(this)
    }




    public fun getVideoList():ArrayList<Video>{
        if (!::videos.isInitialized){
            refreshData()
        }


        return videos

    }

    private fun refreshData(){
        videos = App.getAppDataBase().videoDao().loadAllVideos() as ArrayList<Video>
        var entitys = getAria().totalTaskList


        for(i in 0 until videos.size){
            var video = videos[i]
            mTask.put(video.url,video)
            mPosition.put(video.url,i)
        }
        entitys.forEach {
            var key = getKey(it)
            var v = mTask.get(key)
            v?.ext = it
        }
    }
//    public fun getMissionList(): ArrayList<Video> {
//        return App.getAppDataBase().videoDao().loadAllVideos() as ArrayList<Video>
//    }

    public fun pauseMission(video: Video){
        getAria().load(video.url).stop()

    }

    public fun startMission(video: Video) {
        var savePath = ""
        var absEntity: AbsEntity? = null
        if (video.url.contains(".m3u8")) {
            var fileName =
                video.url.substring(video.url.lastIndexOf("/") + 1, video.url.lastIndexOf(".")) + "_"+System.currentTimeMillis()

            savePath = File(Constant.getAppCacheFile().absolutePath, fileName).absolutePath

            var mUrl = video.url
            Logger.d(savePath)
            getAria().load(video.url).setFilePath(savePath,true).asM3U8()
                .setBandWidthUrlConverter( IBandWidthUrlConverter() {
                    bandWidthUrl->
                        var index = mUrl.lastIndexOf("/");
                    mUrl.substring(0, index + 1) + bandWidthUrl;
                })
                .setTsUrlConvert( IVodTsUrlConverter() {
                    m3u8Url , tsUrls->
                    var index = m3u8Url.lastIndexOf("/");
                    var parentUrl = m3u8Url.substring(0, index + 1);
                    var newUrls =  java.util.ArrayList<String>();
                    tsUrls.forEach {
                        url->
                        newUrls.add(parentUrl + url);
                    }

                     newUrls;
                })
                .setMergeHandler( ITsMergeHandler() {
                    keyInfo ,tsPath ->
                        true
                })
                .merge(true)
                .start()

        } else {
            var fileName = video.url.substring(video.url.lastIndexOf("/") + 1, video.url.lastIndexOf("."))
            var fileNameAndSuffix = video.url.substring(video.url.lastIndexOf("/") + 1, video.url.length)

            savePath = Constant.getAppCacheFile().absolutePath + "/" + fileName+ "_"+System.currentTimeMillis() + "/" +fileNameAndSuffix

            getAria().load(video.url).useServerFileName(true)
                .setFilePath(savePath, true).start()

        }
        var hadVideo = false
        App.getAppDataBase().videoDao().loadAllVideos().forEach {
            if (it.url==video.url){
                hadVideo = true
            }
        }
        if(hadVideo) {
            App.getAppDataBase().videoDao().updateVideos(video)
        }else{
            App.getAppDataBase().videoDao().insertVideos(video)
        }
        refreshData()


    }



    public fun stopMission(video: Video) {
        getAria().load(video.url).stop()
    }

    public fun delMission(vararg videos: Video) {

        videos.forEach {
            video->
            App.getAppDataBase().videoDao().deleteVideos(video)

            getAria().load(video.url).stop()
            getAria().load(video.url).cancel(true)
        }


        refreshData()
    }



    @M3U8.onPeerStart
    fun onPeerStart(m3u8Url: String, peerPath: String, peerIndex: Int) {
        //ALog.d(TAG, "peer start, path: " + peerPath + ", index: " + peerIndex);
//        Logger.d(m3u8Url)
//        Logger.d(peerPath)
//        Logger.d(peerIndex)
    }

    @M3U8.onPeerComplete
    fun onPeerComplete(m3u8Url: String, peerPath: String, peerIndex: Int) {
        //ALog.d(TAG, "peer complete, path: " + peerPath + ", index: " + peerIndex);
//        Logger.d(m3u8Url)
//        Logger.d(peerPath)
//        Logger.d(peerIndex)
    }

    @M3U8.onPeerFail
    fun onPeerFail(m3u8Url: String, peerPath: String, peerIndex: Int) {
//        Logger.d(m3u8Url)
//        Logger.d(peerPath)
//        Logger.d(peerIndex)
    }

    @Download.onWait
    fun onWait(task: DownloadTask) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onPre
    protected fun onPre(task: DownloadTask) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onTaskStart
    fun taskStart(task: DownloadTask) {
        Logger.d(task.entity.fileSize)
        Logger.d(task.entity.fileName)
        trigger(task)

    }

    @Download.onTaskRunning
    protected fun running(task: DownloadTask) {
        Logger.d(task.downloadEntity.currentProgress)
        Logger.d(task.downloadEntity.getPercent())
        trigger(task)

    }

    @Download.onTaskResume
    fun taskResume(task: DownloadTask) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onTaskStop
    public fun taskStop(task: DownloadTask) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onTaskCancel
    public fun taskCancel(task: DownloadTask) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onTaskFail
    public fun taskFail(task: DownloadTask, e: Exception) {
        Logger.d(task)
        trigger(task)

    }

    @Download.onTaskComplete
    public fun taskComplete(task: DownloadTask) {
        Logger.d(task)

        trigger(task)

    }

     fun trigger(task: DownloadTask){
//        var video = mTask.get(getKey(task.entity))
         Logger.d(task.convertFileSize)
         var position = getPosition(task)
         if (position<videos.size) {
             var video = videos.get(position)
             video?.ext = task.entity
             triggerUpdate?.invoke(task, getPosition(task))
         }

    }

     fun getPosition(task: DownloadTask):Int{
        var key = task.entity.url
        return mPosition.get(key)?:0
    }

    fun getDownLoadFinishVideoLocalPath(url:String):String?{
//        var position = mPosition.get(url)?:-1
//        if (position==-1){
//            return null
//        }
        var entity = mTask.get(url)?.ext as DownloadEntity?

        if (entity!=null){
            if (entity.state==IEntity.STATE_COMPLETE){

                return entity.filePath
            }else{
                return null

            }
        }else{
            return null
        }
    }


     fun getKey(entity: AbsEntity): String {
        if (entity is DownloadEntity) {
            return entity.url
        } else if (entity is DownloadGroupEntity) {
            return entity.groupHash
        }
        return ""
    }

}

