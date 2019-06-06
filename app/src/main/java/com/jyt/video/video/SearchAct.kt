package com.jyt.video.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.db.bean.SearchText
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.impl.SearchHistoryServiceImpl
import kotlinx.android.synthetic.main.act_search.*

@Route(path = "/video/search")
class SearchAct :BaseAct(), View.OnClickListener {

    var searchService:SearchHistoryServiceImpl? = null


    override fun onClick(v: View?) {
        when(v){
            img_back2->{
                onBackPressed()
            }

            tv_clear_history->{
                searchService?.delSearchHistory()
                flow_layout.removeAllViews()
                ll_empty.visibility = View.VISIBLE
                tv_clear_history.visibility = View.GONE
            }
            is ImageView->{
                var searchText =v.tag
                if (searchText!=null){
                    searchText as SearchText
                    searchService?.delSearchHistory(searchText)
                    (v.parent.parent as ViewGroup).removeView(v.parent as View)
                }
            }
            is RelativeLayout->{
                if (v.childCount==2){
                    var keyWord = (v.getChildAt(0) as TextView).text.toString()
                    ARouter.getInstance().build("/search/result").withString("keyWord",keyWord).navigation()

                }
            }
        }
    }

    override fun initView() {
        searchService = SearchHistoryServiceImpl()
        hideToolbar()
        getData()
        search_view.input?.imeOptions = EditorInfo.IME_ACTION_SEARCH
        search_view.input?.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                searchService?.doSearch(search_view.input?.text.toString(),ServiceCallback{
                    code, data ->
                    ARouter.getInstance().build("/search/result").withString("keyWord",(search_view.input?.text.toString())).navigation()
                })
            }
            false
        }
        img_back2.setOnClickListener(this)
        tv_clear_history.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_search
    }

    private fun getData(){
        searchService?.getSearchHistory(ServiceCallback {
                code, data ->
            if (data.isNullOrEmpty()){
                ll_empty.visibility = View.VISIBLE
                tv_clear_history.visibility = View.GONE

            }else{
                ll_empty.visibility = View.GONE
                tv_clear_history.visibility = View.VISIBLE

                flow_layout.removeAllViews()
                data.forEach {
                    flow_layout.addView(createItem(it))
                }
            }
        })
    }

    fun createItem(searchText:SearchText):View{
        var item = LayoutInflater.from(this).inflate(R.layout.layout_search_history_item,flow_layout,false)
        var title = item.findViewById<TextView>(R.id.tv_text)
        title.text = searchText.content

        var image = item.findViewById<ImageView>(R.id.img_del)
        image.tag = searchText
        image.setOnClickListener(this)
        item.setOnClickListener(this)
        return item
    }

}