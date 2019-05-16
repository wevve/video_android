package com.jyt.video.service

import com.jyt.video.common.db.bean.SearchText

interface SearchHistoryService {

    fun getSearchHistory(callback: ServiceCallback<List<SearchText>>)

    fun doSearch(text:String,callback: ServiceCallback<Any>)

    fun delSearchHistory(vararg text: SearchText)


}