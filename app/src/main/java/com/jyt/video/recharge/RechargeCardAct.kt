package com.jyt.video.recharge

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.recharge.util.IdentifyingCode
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.WalletService
import com.jyt.video.service.impl.WalletServiceImpl
import kotlinx.android.synthetic.main.act_recharge_card.*
import java.text.SimpleDateFormat
import java.util.*

@Route(path="/recharge/card")

class RechargeCardAct :BaseAct(), View.OnClickListener {

    lateinit var walletService: WalletService

    var result:String? = null

    var buyCardUrl:String? = null

    override fun onClick(v: View?) {
        when(v){
            btn_search->{
                var carNum = input_card_num.text.toString()
                var captcha = input_captcha.text.toString()

                if (captcha.toLowerCase()!=result?.toLowerCase()){
                    ToastUtil.showShort(this,"验证码不正确")
                    return
                }
                walletService.checkCardInfo(carNum, ServiceCallback{
                    code, data ->
                    if (code!=BaseJson.CODE_SUCCESS){
                        refreshCaptcha()
                    }
                    if(data!=null){
                        tv_card_num.text = data.cardType
                        tv_card_time.text = if (data.vipTime==null || data.vipTime==0L){
                             "未知"
                        }else{
                            "${data.vipTime}天"
                        }
                        tv_card_value.text = data.chargeCorn.toString()

                        tv_card_end_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date( data.endTime*1000))

                        tv_card_sate.text = data.cardStatus
                    }
                })
            }
            btn_recharge->{
                var carNum = input_card_num.text.toString()
                var captcha = input_captcha.text.toString()
                if (captcha.toLowerCase()!=result?.toLowerCase()){
                    ToastUtil.showShort(this,"验证码不正确")
                    return
                }
                walletService.rechargeCard(carNum,ServiceCallback{
                    code, data ->
                    refreshCaptcha()
                    if (code==BaseJson.CODE_SUCCESS){
                        ToastUtil.showShort(this,"充值成功")
                        finish()
                    }
                })

            }
            img_captcha->{
                refreshCaptcha()

            }
            tv_buy_card->{
                if (buyCardUrl.isNullOrEmpty()){
                    ToastUtil.showShort(this,"暂无数据")
                    return
                }
                ARouter.getInstance().build("/web/index").withString("url",buyCardUrl).navigation()
            }
        }
    }

    override fun initView() {
        walletService = WalletServiceImpl()
        setListener()


        refreshCaptcha()
        getBuyCardUrl()
    }

    private fun setListener(){
        btn_search.setOnClickListener(this)
        btn_recharge.setOnClickListener(this)
        img_captcha.setOnClickListener(this)
        tv_buy_card.setOnClickListener(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.act_recharge_card
    }

    private fun refreshCaptcha(){
        img_captcha.setImageBitmap(IdentifyingCode.getInstance().createBitmap())
        result = IdentifyingCode.getInstance().code
    }


    private fun getBuyCardUrl(){
        walletService.getBuyCardUrl(ServiceCallback{
            code, data ->
            buyCardUrl = data
        })
    }

}