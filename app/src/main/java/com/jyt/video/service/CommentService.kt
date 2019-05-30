package com.jyt.video.service

import com.jyt.video.video.entity.CommentItem
import com.jyt.video.video.entity.CommentVO

interface CommentService {

    fun getCommentList(videoId:Long,lastCommentId:Long?,callback: ServiceCallback<CommentVO>)

    fun addComment(comment:String,videoId: Long,callback: ServiceCallback<Any>)
}