package com.jyt.video

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.common.db.AppDatabase
import android.arch.persistence.room.Room
import android.util.Log
import com.danikula.videocache.HttpProxyCacheServer
import com.jyt.video.common.Constant
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.liulishuo.filedownloader.util.FileDownloadLog
import com.liulishuo.filedownloader.util.FileDownloadUtils
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import com.fm.openinstall.OpenInstall


class App : Application(){


    var proxy:HttpProxyCacheServer? = null

    override fun onCreate() {
        super.onCreate()


        if(isMainProcess()){
            app = this
            ARouter.openDebug()
            ARouter.openLog()
            ARouter.init(this)

            initHawk()
            initDB()
            initFileDownLoader()

            Logger.addLogAdapter(AndroidLogAdapter())

            OpenInstall.init(this);

        }

    }


    private fun initDB(){
        mAppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "android_room_dev.db")
            .allowMainThreadQueries()
            .build()
    }

    private fun initFileDownLoader() {
        // just for open the log in this demo project.
        FileDownloadLog.NEED_LOG = true

        /**
         * just for cache Application's Context, and ':filedownloader' progress will NOT be launched
         * by below code, so please do not worry about performance.
         * @see FileDownloader#init(Context)
         */
        FileDownloader.setupOnApplicationOnCreate(this)
            .connectionCreator(
                FileDownloadUrlConnection.Creator(
                    FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                )
            )
            .commit()


    }



    private fun initHawk(){
        Hawk.init(this)
            .build()
    }
    companion object{

        lateinit var app:App

        private var mAppDatabase: AppDatabase? = null

        public fun getAppDataBase():AppDatabase{
            return mAppDatabase!!
        }


        fun getProxy():HttpProxyCacheServer{
            var proxy = App.app.proxy
            if (proxy==null){
                proxy = app.createHttpProxy()
            }
            return proxy
        }

    }


    public fun createHttpProxy():HttpProxyCacheServer{
//        var file = Constant.getAppCacheFile()
//        if (!file.exists()){
//            file.mkdirs()
//        }
        return HttpProxyCacheServer.Builder(this)
            .cacheDirectory(Constant.getAppCacheFile())
            .fileNameGenerator {
                url->
                var fileName = url.substring(url.lastIndexOf("/")+1,url.length)
                fileName
            }
            .maxCacheFilesCount(999)
            .build()
    }

    fun isMainProcess(): Boolean {
        val pid = android.os.Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return applicationInfo.packageName == appProcess.processName
            }
        }
        return false
    }

}