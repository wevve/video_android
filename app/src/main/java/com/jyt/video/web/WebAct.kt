package com.jyt.video.web

import android.webkit.*
import android.webkit.WebSettings.LOAD_NO_CACHE
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_web.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import me.dkzwm.widget.srl.SmoothRefreshLayout

@Route(path = "/web/index")
class WebAct :BaseAct(){

    var url:String? = null

    var title:String? = null
    override fun initView() {
        url = intent.getStringExtra("url")
        if(url?.isEmpty()==true){
            finish()
            return
        }
        title = intent.getStringExtra("title")
        if (title?.isNotEmpty()==true){
            tv_title?.text = title
        }


        refresh_layout.setOnRefreshListener(object :SmoothRefreshLayout.OnRefreshListener{
            override fun onLoadingMore() {
                refresh_layout.refreshComplete()
            }

            override fun onRefreshing() {
                web_view.reload()
                refresh_layout.refreshComplete()
            }

        })
        initWebView()
        web_view.loadUrl(url)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_web
    }

    private fun initWebView(){
        var setting = web_view.settings
        setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.useWideViewPort = true; //将图片调整到适合webview的大小
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        setting.javaScriptEnabled = true

        web_view.webChromeClient = object :WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if(this@WebAct.title.isNullOrEmpty()) {
                    tv_title?.text = title
                }

            }
        }

        web_view.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }



    }

    override fun onBackPressed() {
//        if (web_view.canGoBack()){
//            web_view.goBack()
//        }else {
            super.onBackPressed()
//        }
    }

}