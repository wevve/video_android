package com.jyt.video.video.widget

import android.content.Context
import android.util.AttributeSet
import cn.jzvd.JzvdStd

class CustomJzvdStd : JzvdStd {

    var playerStateListener:PlayerStateListener? = null

    constructor(context: Context?) : this(context,null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onStatePlaying() {
        super.onStatePlaying()
        playerStateListener?.onStateEventChange("PLAYING")
    }


    override fun onStatePause() {
        super.onStatePause()

        playerStateListener?.onStateEventChange("PAUSE")
    }


    public interface PlayerStateListener{
        fun onStateEventChange(event:String)
    }
}
