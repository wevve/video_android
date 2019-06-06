package com.jyt.video.welcome

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
import kotlinx.android.synthetic.main.act_welcome.*

class WelcomeAct : BaseAct(){

    lateinit var userService: UserService

     lateinit var timeHelper:TimeHelper
    override fun initView() {


        tv_next.setOnClickListener {
            toMainAct()
        }

        hideToolbar()
        userService = UserServiceImpl()

        timeHelper = TimeHelper(null)
        timeHelper.setTimerListener {
            if (it=="end"){
//                toMainAct()
                ARouter.getInstance().build("/main/index").navigation()
                finish()
            }
        }
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
            if(data!=null){
                img_welcome.setOnClickListener {
//                    toMainAct()
//                    ARouter.getInstance().build("/main/index").navigation()
                    timeHelper.stop()
                    ARouter.getInstance().build("/web/index").withString("url",data.app_start_url).navigation()
//                    finish()
                }
                Glide.with(this).load(data.app_start_screen).apply(RequestOptions.centerCropTransform()).into(img_welcome)

                timeHelper.setTime(data.app_start_time.toInt())
                timeHelper.start()
            }else{
                toMainAct()
            }
        })
    }


}
