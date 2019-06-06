package com.binbook.binbook.common.util

import com.bumptech.glide.request.RequestOptions
import com.jyt.video.R


class GlideHelper{


    companion object{
        fun centerCrop():RequestOptions{
            var requestOptions = RequestOptions()
            requestOptions.centerCrop()
            return requestOptions
        }

        fun avatar():RequestOptions{
           return  centerCrop().circleCrop()
               .error(R.mipmap.default_avatar)
        }

        fun tuiguangBanner():RequestOptions{
            return centerCrop().error(R.mipmap.tuiguang_banner)
        }
    }
}