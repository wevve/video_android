package com.jyt.video.person.frag

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.api.entity.PersonHomeResult
import com.jyt.video.common.base.BaseFrag
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.frag_personal.*
import java.text.SimpleDateFormat
import java.util.*

class PersonalFrag:BaseFrag(), View.OnClickListener {

    lateinit var userService:UserService

    override fun onClick(v: View?) {

        when(v){
            btn_to_login->{
                ARouter.getInstance().build("/login/index").navigation()
                return
            }
            btn_to_register->{
                ARouter.getInstance().build("/register/index").navigation()
                return
            }
        }
        //登录注册不需要登录   其余要登录才能点

        if (!UserInfo.isLogin()){

            ARouter.getInstance().build("/login/index").navigation()

            return
        }

        when(v){

            img_setting->{
                ARouter.getInstance().build("/setting/index").navigation()
            }
            ll_cache_video->{
                ARouter.getInstance().build("/video/cache").navigation()
            }
            ll_collection_video->{
                ARouter.getInstance().build("/video/collection").navigation()
            }
            ll_deal_record->{
                ARouter.getInstance().build("/deal/record").navigation()
            }
            ll_my_wallet->{
                ARouter.getInstance().build("/wallet/index").navigation()
            }
            ll_promotion->{
                ARouter.getInstance().build("/web/index")
                    .withString("title","推广")
                    .withString("url","https://www.baidu.com").navigation()
            }
            ll_promotion_record->{
                ARouter.getInstance().build("/promotion/record").navigation()
            }
            ll_recharge_member->{
                ARouter.getInstance().build("/recharge/member").navigation()
            }
            ll_recharge->{
                ARouter.getInstance().build("/recharge/card").navigation()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_personal
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()

        if (UserInfo.isLogin()){
            group_no_login.visibility = View.GONE
            group_login.visibility =  View.VISIBLE

            getUserInfo()
        }else{
            group_no_login.visibility = View.VISIBLE
            group_login.visibility =  View.GONE
        }




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userService = UserServiceImpl()

        btn_to_login.setOnClickListener(this)
        btn_to_register.setOnClickListener(this)
        img_setting.setOnClickListener(this)


        ll_cache_video.setOnClickListener(this)
        ll_collection_video.setOnClickListener(this)
        ll_deal_record.setOnClickListener(this)
        ll_my_wallet.setOnClickListener(this)
        ll_promotion.setOnClickListener(this)
        ll_promotion_record.setOnClickListener(this)

        ll_recharge_member.setOnClickListener(this)
        ll_recharge.setOnClickListener(this)



    }

    private fun getUserInfo(){
        userService.getUserHomeInfo(ServiceCallback{
            code, data ->
            if (data!=null){
                group_login.visibility = View.VISIBLE
                group_no_login.visibility = View.GONE
                setupView(data)
            }
        })
    }

    private fun setupView(data: PersonHomeResult){
        if (data.isVip){
            tv_is_vip.visibility = View.VISIBLE
            tv_vip_end_date.visibility = View.VISIBLE
            var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm-ss")
            tv_vip_end_date.text = "到期时间 ${dateFormat.format(Date((data.vipEndDate?:0)*1000))}"
        }else{
            tv_is_vip.visibility = View.GONE
            tv_vip_end_date.visibility = View.GONE
        }
1
        Glide.with(this).load(data.avatar).apply(GlideHelper.avatar()).into(img_avatar)

        tv_name.text = data.nickname

        tv_money.text = "余额：${data.money.toString()}"

        tv_coin.text = "金币：${data.corn.toString()}"


        UserInfo.setUserHomeInfo(data)

    }
}