package com.jyt.video.common.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.jyt.video.R
import com.jyt.video.common.util.StatusBarUtil
import kotlinx.android.synthetic.main.act_base.*
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseAct :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_base)


        var layoutId = getLayoutId()
        if(layoutId!=0){
            ll_base_content.addView(LayoutInflater.from(this).inflate(layoutId,ll_base_content,false))
        }


        baseInit()

        initView()
    }

    private fun baseInit(){

        if (title.isNotEmpty()){
            tv_title?.text = title
        }

        img_back?.setOnClickListener {
            onBackPressed()
        }
    }

    abstract fun initView()
    fun showToolbar(){
        rl_toolbar?.visibility = View.VISIBLE
    }

    fun hideToolbar(){
        rl_toolbar?.visibility = View.GONE
    }

    abstract fun getLayoutId():Int
}