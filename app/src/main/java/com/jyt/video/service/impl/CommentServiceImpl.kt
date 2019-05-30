package com.jyt.video.service.impl

import com.jyt.video.api.ApiService
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.service.CommentService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.video.entity.CommentVO

class CommentServiceImpl :CommentService{
    override fun addComment(comment: String, videoId: Long, callback: ServiceCallback<Any>) {
        RxHelper.simpleResult(ApiService.getInstance().api.addComment(videoId,UserInfo.getUserId(),comment),callback)
    }

    override fun getCommentList(videoId: Long, lastCommentId: Long?, callback: ServiceCallback<CommentVO>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getCommentList(videoId,lastCommentId),callback)
    }

}