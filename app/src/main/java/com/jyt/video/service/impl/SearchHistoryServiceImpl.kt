package com.jyt.video.service.impl

import com.jyt.video.App
import com.jyt.video.api.ApiService
import com.jyt.video.common.db.AppDatabase
import com.jyt.video.common.db.bean.SearchText
import com.jyt.video.common.db.dao.SearchTextDao
import com.jyt.video.common.util.RxHelper
import com.jyt.video.service.SearchHistoryService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.video.entity.SearchVideoResult

class SearchHistoryServiceImpl : SearchHistoryService{

    var searchDao:SearchTextDao? = null

    init {
        searchDao = App.getAppDataBase().searchTextDao()
    }

    override fun getSearchHistory(callback: ServiceCallback<List<SearchText>>) {
        callback.result(200,searchDao?.loadAll())
    }

    override fun doSearch(text: String, callback: ServiceCallback<SearchVideoResult>) {
        addSearchHistory(text)
        callback.result(200,null)
//        RxHelper.simpleResult(ApiService.getInstance().api.searchVideo(text),callback)

    }


    override fun delSearchHistory(vararg text: SearchText) {

        searchDao?.delete(*text)
//        text?.forEach {
//            searchDao?.delete(it)
//        }
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