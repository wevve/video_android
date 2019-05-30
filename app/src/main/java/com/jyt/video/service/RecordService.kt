package com.jyt.video.service

import com.jyt.video.deal.entity.Record

interface RecordService{


    fun getDealRecordList(typeId:Int,lastId:String,callback: ServiceCallback<List<Record>>)
}