package com.jyt.video.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.BuildConfig
import com.jyt.video.R
import com.jyt.video.api.Constans
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.StatusBarUtil
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.daili.DaiLiFrag
import com.jyt.video.home.dialog.VipDateDialog
import com.jyt.video.home.frag.HomeFrag
import com.jyt.video.person.frag.PersonalFrag
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import com.jyt.video.web.WebFrag
import com.jyt.video.xuanchuan.XuanChuanFrag
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.parseDouble
import java.lang.Exception

@Route(path = "/main/index")
class MainActivity : BaseAct(), View.OnClickListener {


    val imagesNor = arrayOf(R.mipmap.home_nor,R.mipmap.proxy_nor,R.mipmap.publicity_nor,R.mipmap.member_nor,R.mipmap.personal_nor)
    val imagesSel = arrayOf(R.mipmap.home_sel,R.mipmap.proxy_sel,R.mipmap.publicity_sel,R.mipmap.member_sel,R.mipmap.personal_sel)

    var vipUrl:String?=null

    var curTab:LinearLayout? = null
    lateinit var userService: UserService

    var adapter:FragmentViewPagerAdapter? = null

    var vipDateDialog: VipDateDialog? = null

    lateinit var huiyuan:WebFrag
    override fun initView() {
//        vipDateDialog = VipDateDialog()
//        vipDateDialog?.show(supportFragmentManager,"")

        userService = UserServiceImpl()
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
//        var daili = DaiLiFrag()
//        daili.url = Constans.BaseUrl+"/appapi/delegate"
        adapter!!.addFragment(daili,null)

        var xuanchuan = WebFrag()
//        var xuanchuan = XuanChuanFrag()
//        xuanchuan.url = Constans.BaseUrl+"/appapi/introduce"
        adapter!!.addFragment(xuanchuan,null)

        huiyuan = WebFrag()
        huiyuan.url = vipUrl
        adapter!!.addFragment(huiyuan,null)

        adapter!!.addFragment(PersonalFrag(),null)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = adapter?.fragments?.size?:1

        StatusBarUtil.setStatusBarColor(this,Color.TRANSPARENT)

        getVipData()
        getDialogData()

        getDaiLiData()
        getXuanChuanData()

        checkVersion()
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

        if (!UserInfo.isLogin()){
            if(
//                tab==ll_tab_proxy
//                || tab==ll_tab_publicity
//                ||
                    tab==ll_tab_personal){
                ARouter.getInstance().build("/login/index").navigation()
                return
            }
        }
        if(vipUrl==null && tab==ll_tab_member){
            ToastUtil.showShort(this,"暂无数据")
            return
        }else{
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


    private fun checkVersion(){
        userService.getVersion(ServiceCallback{
            code, data ->
            if (data!=null){
                var version = BuildConfig.VERSION_NAME
                if ( data.apk_version?.compareTo( version)?:0  >0){

                    if(data?.android.isNullOrBlank()){
                        return@ServiceCallback
                    }
                    AlertDialog.Builder(this)
                        .setMessage("检测到新版本")
                        .setPositiveButton("去下载") { dialog, which ->
                            dialog.dismiss()

                            val uri = Uri.parse(data.android)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                        .setNegativeButton("取消") { dialog, which ->

                            dialog.dismiss()
                        }
                        .create().show()
                }
            }
        })
    }


    private fun getVipData(){
        userService.getVipInfo(ServiceCallback{
            code, data ->
            vipUrl = data?.get("vip")

            huiyuan.url = vipUrl

        })

    }

    private fun getDialogData(){
        userService.getHomeDialogData(ServiceCallback{
            code, data ->
            if(data!=null){
                vipDateDialog = VipDateDialog()
                vipDateDialog?.title = data.title
                vipDateDialog?.content = data.content
                try {
                    vipDateDialog?.show(supportFragmentManager,"")
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        })
    }


    private fun getDaiLiData(){
        userService.getDaiLiInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                var dailiUrl = data["url"]
                (adapter?.fragments?.get(1) as WebFrag).url = dailiUrl

            }
        })
    }

    private fun getXuanChuanData(){
        userService.getXuanChuanInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                var xuanchuanUrl = data["url"]
                (adapter?.fragments?.get(2) as WebFrag).url = xuanchuanUrl
            }
        })
    }
}
