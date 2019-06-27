package com.jyt.video.web

import android.os.Bundle
import android.view.View
import android.webkit.*
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.frag_web.*

class WebFrag : BaseFrag() {


    var url:String?=null
    override fun getLayoutId(): Int {
        return R.layout.frag_web
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()


    }

    override fun onResume() {
        super.onResume()


        if (!url.isNullOrBlank() &&  web_view.url.isNullOrBlank()){
            web_view.loadUrl(url)
        }

    }


    private fun initWebView(){
        var setting = web_view.settings
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.useWideViewPort = true; //将图片调整到适合webview的大小
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        setting.javaScriptEnabled = true



        web_view.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }

}