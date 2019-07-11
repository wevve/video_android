package com.jyt.video.main

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fm.openinstall.OpenInstall
import com.fm.openinstall.listener.AppInstallAdapter
import com.fm.openinstall.listener.AppWakeUpAdapter
import com.fm.openinstall.model.AppData
import com.fm.openinstall.model.Error
import com.jyt.video.App
import com.jyt.video.BuildConfig
import com.jyt.video.R
import com.jyt.video.api.Constans
import com.jyt.video.api.entity.VersionBean
import com.jyt.video.common.adapter.FragmentViewPagerAdapter
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.DialogHelper
import com.jyt.video.common.helper.DoubleClickExitHelper
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.service.DownloadReceiver
import com.jyt.video.common.util.DMUtil
import com.jyt.video.common.util.StatusBarUtil
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.home.dialog.VipDateDialog
import com.jyt.video.home.frag.HomeFrag
import com.jyt.video.main.entity.HomeDialogResult
import com.jyt.video.person.frag.PersonalFrag
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import com.jyt.video.web.WebFrag
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

@Route(path = "/main/index")
class MainActivity : BaseAct(), View.OnClickListener {

    companion object{
        var pid:String=""

    }
    val imagesNor = arrayOf(R.mipmap.home_nor,R.mipmap.proxy_nor,R.mipmap.publicity_nor,R.mipmap.member_nor,R.mipmap.personal_nor)
    val imagesSel = arrayOf(R.mipmap.home_sel,R.mipmap.proxy_sel,R.mipmap.publicity_sel,R.mipmap.member_sel,R.mipmap.personal_sel)

    var vipUrl:String?=null

    var curTab:LinearLayout? = null
    lateinit var userService: UserService

    var adapter:FragmentViewPagerAdapter? = null

    var vipDateDialog: VipDateDialog? = null

    lateinit var huiyuan:WebFrag

    lateinit var receiver : DownloadReceiver


    var isShowHomeDialog = false

    var versionBean:VersionBean?=null

    var homeDialogResult:HomeDialogResult?=null

    var wakeUpAdapter: AppWakeUpAdapter? = object : AppWakeUpAdapter() {
        override fun onWakeUp(appData: AppData) {
            //获取渠道数据
            val channelCode = appData.getChannel()
            //获取绑定数据
            val bindData = appData.getData()

            try {
                var jsobj =  JSONObject(bindData)
                pid = jsobj.optString("pid")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        override fun onWakeUpFinish(p0: AppData?, p1: Error?) {
            super.onWakeUpFinish(p0, p1)
        }
    }



    override fun onDestroy() {

        wakeUpAdapter = null

        unregisterReceiver(receiver)

        super.onDestroy()

    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter)
    }

    override fun initView() {

        OpenInstall.getInstall(object: AppInstallAdapter() {
            override fun onInstall( appData:AppData) {

            }

            override fun onInstallFinish(appData: AppData?, p1: Error?) {
                super.onInstallFinish(appData, p1)
                //获取渠道数据
                var channelCode = appData?.getChannel()
                //获取自定义数据
                var bindData = appData?.getData()

                try {
                    var jsobj =  JSONObject(bindData)
                    pid = jsobj.optString("pid")


                }catch (e:Exception){
                    e.printStackTrace()
                }finally {

                    if (pid.isNullOrBlank()){
                        pid="0"
                    }

                    checkVersion()
                }

            }
        }

        )

        receiver =  DownloadReceiver();
        var intentFilter =  IntentFilter();
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
        intentFilter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
        registerReceiver(receiver, intentFilter);

        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);

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
//        adapter!!.addFragment(Fragment(),null)

        var daili = WebFrag()
//        var daili = DaiLiFrag()
//        daili.url = Constans.BaseUrl+"/appapi/delegate"
        adapter!!.addFragment(daili,null)
//        adapter!!.addFragment(Fragment(),null)

//        var xuanchuan = WebFrag()
//        var xuanchuan = XuanChuanFrag()
//        xuanchuan.url = Constans.BaseUrl+"/appapi/introduce"
//        adapter!!.addFragment(xuanchuan,null)
        adapter!!.addFragment(Fragment(),null)

        huiyuan = WebFrag()
        huiyuan.url = vipUrl
        adapter!!.addFragment(huiyuan,null)
//        adapter!!.addFragment(Fragment(),null)


        adapter!!.addFragment(PersonalFrag(),null)
//        adapter!!.addFragment(Fragment(),null)


        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = adapter?.fragments?.size?:1

        StatusBarUtil.setStatusBarColor(this,Color.TRANSPARENT)

        getVipData()


        getDaiLiData()
//        getXuanChuanData()

        object :Thread() {
            override fun run() {
                super.run()
                Thread.sleep(2000)
                runOnUiThread{
//                    isShowHomeDialog()
                    getDialogData()

                }

            }
        }

            .start()

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

            if (tab==ll_tab_home){
                adapter?.fragments?.get(0)?.childFragmentManager?.popBackStack()
            }

            return
        }

        if (!UserInfo.isLogin()){
            if(
//                tab==ll_tab_proxy
//                ||
                tab==ll_tab_publicity
                ||
                    tab==ll_tab_personal){
                ARouter.getInstance().build("/login/index").navigation()
                return
            }
        }

        if (tab==ll_tab_publicity){
            val uri = Uri.parse(Constans.BaseUrl+"/appapi/shareUrl/pid/"+UserInfo.getUserId())
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
            return
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

    override fun onResume() {
        super.onResume()

        if (isShowHomeDialog){

            if(homeDialogResult!=null) {
                showHomeDialog(homeDialogResult!!)
            }else{
                if(versionBean!=null){
                    showVersionDialog(versionBean!!)
                }
            }
//            checkVersion()
            isShowHomeDialog = false
        }
    }


    private fun checkVersion(){

        try {
            RxPermissions(this@MainActivity).request(Manifest.permission.READ_PHONE_STATE).subscribe {
                if(it){
                    userService.getVersion(ServiceCallback{
                            code, data ->
                        if (data!=null){
                            var version = BuildConfig.VERSION_NAME
                            if ( data.apk_version != version){
                                var igVersion = UserInfo.get("apkversion","")
                                if (igVersion==data.apk_version){
                                    return@ServiceCallback
                                }
                                if(data?.android.isNullOrBlank()){
                                    return@ServiceCallback
                                }
                                versionBean = data
                            }
                        }
                    })
                }else{
                    DialogHelper.showOpenPermissionDialog(this@MainActivity)
                }
            }

        }catch (e:Exception){
            e.printStackTrace()

            isShowHomeDialog = true
        }

    }


    private fun getVipData(){
        userService.getVipInfo(ServiceCallback{
            code, data ->
            vipUrl = data?.get("vip")

            huiyuan.loadUrl(vipUrl?:"")

        })

    }

    private fun getDialogData(){
        userService.getHomeDialogData(ServiceCallback{
            code, data ->
            if(data!=null){
                homeDialogResult = data
                showHomeDialog(homeDialogResult!!)
            }
        })
    }


    private fun getDaiLiData(){
        userService.getDaiLiInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                var dailiUrl = data["url"]
                (adapter?.fragments?.get(1) as WebFrag).loadUrl(dailiUrl?:"")

            }
        })
    }

    private fun getXuanChuanData(){
        userService.getXuanChuanInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                var xuanchuanUrl = data["url"]
                (adapter?.fragments?.get(2) as WebFrag).loadUrl( xuanchuanUrl?:"")
            }
        })
    }

    override fun onBackPressed() {

        if (DoubleClickExitHelper.getInstance().canExit()){
            super.onBackPressed()
        }
    }

    private fun showVersionDialog(data:VersionBean){
        var dialog = AlertDialog.Builder(this@MainActivity)
            .setTitle("检测到新版本")
            .setMessage(data.apk_update)
            .setPositiveButton("去下载") { dialog, which ->
                dialog.dismiss()

//                            val uri = Uri.parse(data.android)
//                            val intent = Intent(Intent.ACTION_VIEW, uri)
//                            startActivity(intent)
                var dmUtil =  DMUtil(this@MainActivity)
                DMUtil.URL = data.android
                DMUtil.TITLE =  data.android?.substring((data.android?.lastIndexOf("/")?:0)+1,data.android?.length?:0)
                DMUtil.DESC = resources.getString(R.string.app_name)
                if (dmUtil.checkDownloadManagerEnable()) {
                    if (App.id != 0L) {
                        dmUtil.clearCurrentTask(App.id);
                    }
                    App.id = dmUtil.download();
                }

            }
            .setNegativeButton("忽略") { dialog, which ->

                UserInfo.add("apkversion",data.apk_version?:"")
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun showHomeDialog(data:HomeDialogResult){
        vipDateDialog = VipDateDialog()
        vipDateDialog?.title = data.title
        vipDateDialog?.content = data.content
        try {
            vipDateDialog?.show(supportFragmentManager,"")


            object :Thread(){
                override fun run() {
                    super.run()
                    while (versionBean==null){
                        Thread.sleep(500)
                    }
                    runOnUiThread {
                        showVersionDialog(versionBean!!)
                    }
                }
            }.start()
            isShowHomeDialog = false

        }catch (e:Exception){
            isShowHomeDialog = true
            e.printStackTrace()
        }
    }


}
