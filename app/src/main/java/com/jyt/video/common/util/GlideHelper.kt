package com.binbook.binbook.common.util

import com.bumptech.glide.request.RequestOptions


class GlideHelper{


    companion object{
        fun centerCrop():RequestOptions{
            var requestOptions = RequestOptions()
            requestOptions.centerCrop()
            return requestOptions
        }
    }
}