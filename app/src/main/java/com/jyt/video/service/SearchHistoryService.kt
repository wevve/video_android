package com.jyt.video.service

import com.jyt.video.common.db.bean.SearchText
import com.jyt.video.video.entity.SearchVideoResult

interface SearchHistoryService {

    fun getSearchHistory(callback: ServiceCallback<List<SearchText>>)

    fun doSearch(text:String,callback: ServiceCallback<SearchVideoResult>)

    fun delSearchHistory(vararg text: SearchText)


}