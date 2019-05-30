package com.jyt.video.main

import android.graphics.Color
import android.media.Image
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.api.Constans
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.util.StatusBarUtil
import com.jyt.video.home.dialog.VipDateDialog
import com.jyt.video.home.frag.HomeFrag
import com.jyt.video.person.frag.PersonalFrag
import com.jyt.video.web.WebFrag
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/main/index")
class MainActivity : BaseAct(), View.OnClickListener {


    val imagesNor = arrayOf(R.mipmap.home_nor,R.mipmap.proxy_nor,R.mipmap.publicity_nor,R.mipmap.member_nor,R.mipmap.personal_nor)
    val imagesSel = arrayOf(R.mipmap.home_sel,R.mipmap.proxy_sel,R.mipmap.publicity_sel,R.mipmap.member_sel,R.mipmap.personal_sel)


    var curTab:LinearLayout? = null

    var adapter:FragmentViewPagerAdapter? = null

    var vipDateDialog: VipDateDialog? = null
    override fun initView() {
//        vipDateDialog = VipDateDialog()
//        vipDateDialog?.show(supportFragmentManager,"")


        hideToolbar()

        curTab = ll_tab_home

        ll_tab_home.setOnClickListener(this)
        ll_tab_member.setOnClickListener(this)
        ll_tab_personal.setOnClickListener(this)
        ll_tab_proxy.setOnClickListener(this)
        ll_tab_publicity.setOnClickListener(this)


        adapter = FragmentViewPagerAdapter(supportFragmentManager)
        adapter!!.addFragment(HomeFrag(),null)

        var daili = WebFrag()
        daili.url = Constans.BaseUrl+"/appapi/delegate"
        adapter!!.addFragment(daili,null)

        var xuanchuan = WebFrag()
        xuanchuan.url = Constans.BaseUrl+"/appapi/introduce"
        adapter!!.addFragment(xuanchuan,null)

        var huiyuan = WebFrag()
        huiyuan.url = Constans.BaseUrl+"/appapi/vip"
        adapter!!.addFragment(huiyuan,null)

        adapter!!.addFragment(PersonalFrag(),null)
        view_pager.adapter = adapter

        StatusBarUtil.setStatusBarColor(this,Color.TRANSPARENT)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onClick(v: View?) {
        if (v is LinearLayout) {
            changeStyle(v)
        }
    }


    private fun changeStyle(tab:LinearLayout){

        if (curTab==tab){
            return
        }
        var oldIndex = ll_bottom.indexOfChild(curTab)
        var index = ll_bottom.indexOfChild(tab)

        (tab.getChildAt(0) as ImageView).setImageDrawable(resources.getDrawable(imagesSel[index]))
        (tab.getChildAt(1) as TextView).setTextColor(resources.getColor(R.color.colorPrimary))


        (curTab?.getChildAt(0) as ImageView).setImageDrawable(resources.getDrawable(imagesNor[oldIndex]))
        (curTab?.getChildAt(1) as TextView).setTextColor(resources.getColor(R.color.unSelText))

        curTab = tab


        view_pager.currentItem = index



    }


    private fun getDailiUrl(){

    }



}
