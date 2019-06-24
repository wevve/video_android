package com.jyt.video.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

class NetWorkUtil {


    companion object{

        /**
         * 检查wifi是否处开连接状态
         *
         * @return
         */
        public fun isWifiConnect(context: Context):Boolean {
            var connManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifiInfo.isConnected();
        }

    }
}