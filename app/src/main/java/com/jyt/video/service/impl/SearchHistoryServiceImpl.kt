package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.common.db.AppDatabase
import com.jyt.video.common.db.bean.SearchText
import com.jyt.video.common.db.dao.SearchTextDao
import com.jyt.video.service.SearchHistoryService
import com.jyt.video.service.ServiceCallback

class SearchHistoryServiceImpl : SearchHistoryService{

    var searchDao:SearchTextDao? = null

    init {
        searchDao = App.getAppDataBase().searchTextDao()
    }

    override fun getSearchHistory(callback: ServiceCallback<List<SearchText>>) {
        callback.result(200,searchDao?.loadAll())
    }

    override fun doSearch(text: String, callback: ServiceCallback<Any>) {
        addSearchHistory(text)

    }

    override fun delSearchHistory(vararg text: SearchText) {
        if (text.isNotEmpty()) {
            searchDao?.delete(*text)
        }
    }


    private fun addSearchHistory(text:String){
        var item = searchDao?.selectByContent(text)
        if (item!=null){
            item.weight = item.weight+1
            searchDao?.update(item)

        }else{
            item = SearchText()
            item.content = text
            searchDao?.insert(item)
        }
    }
}