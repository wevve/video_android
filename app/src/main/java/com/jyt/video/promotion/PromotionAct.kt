package com.jyt.video.promotion

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.promotion.entity.PromotionBean
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import kotlinx.android.synthetic.main.act_promotion.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.binbook.binbook.common.util.GlideHelper
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.impl.UserServiceImpl


@Route(path = "/promotion/index")
class PromotionAct: BaseAct(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_copy->{
                if(promotionBean==null){
                    return
                }
                var myClipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip: ClipData
                val text = promotionBean?.td_code
                myClip = ClipData.newPlainText("text", text)
                myClipboard.primaryClip = myClip

                ToastUtil.showShort(this,"已复制到黏贴版")
            }
            tv_right_function->{
                ARouter.getInstance().build("/promotion/record").navigation()
            }

        }
    }

    lateinit var userService: UserService

    var promotionBean:PromotionBean? = null
    override fun initView() {
        userService = UserServiceImpl()

        tv_right_function.text = "宣传记录"
        tv_right_function.visibility = View.VISIBLE
        tv_right_function.setOnClickListener(this)
        btn_copy.setOnClickListener(this)
        getData()
    }

    override fun getLayoutId(): Int {
        return R.layout.act_promotion
    }


    private fun getData(){
        userService.getPromotionInfo(ServiceCallback{
            code, data ->
            if (data!=null){
                promotionBean = data

                tv_content.text = promotionBean?.td_text

                Glide.with(this).load(promotionBean?.td_code).into(img_qrcode)


                Glide.with(this).load(promotionBean?.td_bgimg).apply(GlideHelper.tuiguangBanner()).into(img_top)
            }
        })
    }
}