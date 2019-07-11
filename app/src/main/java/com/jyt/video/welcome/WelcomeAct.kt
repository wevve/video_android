package com.jyt.video.welcome

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.Window
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.util.TimeHelper
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import com.jyt.video.welcome.entity.WelcomeResult
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.act_welcome.*

class WelcomeAct : BaseAct(){

    lateinit var userService: UserService

     lateinit var timeHelper:TimeHelper

    var clickAD = false

    var welcomeResult: WelcomeResult? = null

    override fun initView() {


        tv_next.setOnClickListener {
            toMainAct()
        }

        hideToolbar()
        userService = UserServiceImpl()

        timeHelper = TimeHelper(tv_next)
        timeHelper.setOriText("跳过")
        timeHelper.setTimerText("跳过(%ss)")
        timeHelper.setTimerListener {
            if (it=="end"){
//                toMainAct()
                finish()
                ARouter.getInstance().build("/main/index").navigation()
                if (clickAD){
                    ARouter.getInstance().build("/web/index").withString("url",welcomeResult?.app_start_url).navigation()
                }

            }
        }
//        timeHelper.setValueListener {
//
//        }
        getWelcome()
    }

    override fun onStop() {
        super.onStop()

        timeHelper.stop()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_welcome
    }




    private fun toMainAct(){
        timeHelper.stop()
        finish()
    }


    private fun getWelcome(){
        userService.getWelcomePhoto(ServiceCallback{
            code, data ->
            welcomeResult = data
            if(data!=null){
                img_welcome.setOnClickListener {
//                    toMainAct()
//                    ARouter.getInstance().build("/main/index").navigation()
                    clickAD = true

                    timeHelper.stop()
//                    finish()
                }

                RxPermissions(this).request(
                    Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe({
                        if (it){
                            var options = RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.welcome_loading)
                            Glide.with(this).load(data.app_start_screen).apply(options).into(img_welcome)
                            timeHelper.setTime(data.app_start_time.toInt())
                            timeHelper.start()
                        }else{

                            var builder = AlertDialog.Builder(this)
                                .setTitle("提示")
                                .setCancelable(false)
                                .setMessage("缺少权限会导致部分功能无法使用")
                                .setNegativeButton("退出",object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {

                                        dialog?.dismiss()
                                        System.exit(0)

                                    }

                                })
                                .setPositiveButton("去设置",object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {

                                        val intent = Intent()
                                        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                        intent.data = Uri.parse("package:" + getPackageName())
                                        startActivity(intent)
                                        dialog?.dismiss()
                                        System.exit(0)

                                    }

                                }).show()
                        }
                    },{
                        it.printStackTrace()
                    })

            }else{
                toMainAct()
            }
        })
    }


}
