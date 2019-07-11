package com.jyt.video.common.helper

import android.os.Handler
import com.jyt.video.App
import com.jyt.video.common.util.ToastUtil

class DoubleClickExitHelper {


    var handler = Handler()

    var count = 0

    var resetRunnable = Runnable {
        count = 0

    }
    companion object{
        val doubleClickExitHelper = DoubleClickExitHelper()
        fun getInstance():DoubleClickExitHelper{
            return doubleClickExitHelper

        }
    }



    fun canExit():Boolean{

        handler.removeCallbacks(resetRunnable)
        handler.postDelayed( resetRunnable,2000)

        count+=1
        if (count==2){
            return true
        }else{
            ToastUtil.showShort(App.app,"再按一次退出")
        }

        return false
    }




}