package com.jyt.video.api.entity

import com.google.gson.annotations.SerializedName
import com.jyt.video.video.entity.ThumbVideo

class FilterVideoListResult{
    var count:Long = 0

    var videolist:ArrayList<ThumbVideo>?=null
}