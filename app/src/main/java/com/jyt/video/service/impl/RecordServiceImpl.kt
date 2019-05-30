package com.jyt.video.service.impl

import com.jyt.video.api.ApiService
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.RxHelper
import com.jyt.video.deal.entity.Record
import com.jyt.video.service.RecordService
import com.jyt.video.service.ServiceCallback

class RecordServiceImpl :RecordService{

    override fun getDealRecordList(typeId: Int, lastId: String,callback: ServiceCallback<List<Record>>) {
        RxHelper.simpleResult(ApiService.getInstance().api.getRecordList(UserInfo.getUserId(),typeId,lastId),callback)
    }

}