package com.jyt.video.daili

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.AccountService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_promotion.*

class DaiLiFrag:BaseFrag(){

    var map : Map<String,String>? = null

    lateinit var userService: UserService
    override fun getLayoutId(): Int {
        return R.layout.act_promotion
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userService = UserServiceImpl()

        btn_copy.setOnClickListener{
                it->
            if (map?.isNotEmpty()==true){
                var myClipboard: ClipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip: ClipData
                val text = map?.get("delegate_code")
                myClip = ClipData.newPlainText("text", text)
                myClipboard.primaryClip = myClip

                ToastUtil.showShort(context,"已复制到黏贴版")
            }


        }
    }

    override fun onResume() {
        super.onResume()

        if (UserInfo.isLogin()){
            getData()
        }
    }



    private fun getData(){
        userService.getDaiLiInfo(ServiceCallback{
                code, data ->
            if (data!=null) {
                map = data
                var image = data["delegate_bgimg"]
                var text = data["delegate_text"]
                var qrcode = data["delegate_code"]


                img_top?.let{
                    Glide.with(this).load(image).apply(GlideHelper.tuiguangBanner()).into(img_top)
                }
                img_qrcode_bg?.let {
                    Glide.with(this).load(qrcode).into(img_qrcode_bg)
                }
                tv_content?.let {
                    tv_content.text = text
                }
            }
        })
    }
}