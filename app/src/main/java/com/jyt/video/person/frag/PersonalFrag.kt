package com.jyt.video.person.frag

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseFrag
import kotlinx.android.synthetic.main.frag_personal.*

class PersonalFrag:BaseFrag(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_to_login->{
                ARouter.getInstance().build("/login/index").navigation()
//                group_no_login.visibility = View.GONE
//                group_login.visibility = View.VISIBLE
            }
            btn_to_register->{
                ARouter.getInstance().build("/register/index").navigation()
            }
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

    override fun onResume() {
        super.onResume()

        group_no_login.visibility = View.VISIBLE
        group_login.visibility =  View.GONE
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}