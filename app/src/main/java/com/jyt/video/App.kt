package com.jyt.video

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.common.db.AppDatabase
import android.arch.persistence.room.Room
import android.util.Log
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.liulishuo.filedownloader.util.FileDownloadLog
import com.liulishuo.filedownloader.util.FileDownloadUtils
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class App : Application(){


    override fun onCreate() {
        super.onCreate()

        app = this

        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)

        initHawk()
        initDB()
        initFileDownLoader()

        Logger.addLogAdapter(AndroidLogAdapter())
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
    }

}