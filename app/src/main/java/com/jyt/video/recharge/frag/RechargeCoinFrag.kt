package com.jyt.video.recharge.frag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alipay.sdk.app.PayTask
import com.jyt.video.App
import com.jyt.video.api.Constans
import com.jyt.video.common.dialog.ActionSheetDialog
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.recharge.entity.PayResult
import com.jyt.video.recharge.entity.PayWay
import com.jyt.video.recharge.entity.RechargeItem
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.WalletService
import com.jyt.video.wxapi.WeChartHelper

class RechargeCoinFrag:BaseRechargeFrag(){
    var items:ArrayList<RechargeItem>? = null

    var coinMoneyRate:Double? = 1.0

    lateinit var walletService:WalletService
    lateinit var userService: UserService

    lateinit var wxHelper: WeChartHelper

    var orderInfo = ""

    override fun pay(item:  ActionSheetDialog.Item,price:Double,rechargeItem: RechargeItem){
        var payWay = item.extra as PayWay
        walletService?.createOrder(payWay.payCode,price,1,null,rechargeItem.corn?.toDouble(), ServiceCallback{
                code, data ->
            if (data!=null){
                if (payWay.payCode == "nativePay|wxAppPay") {
                    walletService.wxPay(data.order_sn, ServiceCallback { code, data ->

                        if (data != null) {
                            wxHelper = WeChartHelper()
                            App.wxpayAppid = data.appid
                            wxHelper.init(context, App.wxpayAppid)
                            wxHelper.registerToWx()
                            wxHelper.setReceivePayResultListener { payResult, ext ->
                                if (ext == "coin") {
                                    if (payResult) {
                                        ToastUtil.showShort(context, "交易成功")
                                        activity?.finish()
                                    } else {
                                        ToastUtil.showShort(context, "交易失败")

                                    }
                                }
                            }
                            wxHelper.pay(
                                data.partnerid,
                                data.prepayid,
                                data.timestamp,
                                data.noncestr,
                                data.sign,
                                "coin"
                            )
                        }
                    })
                } else if (payWay.payCode == "nativePay|aliAppPay") {

                    walletService.aliPay(data.order_sn, ServiceCallback { code, data ->
                        if (data != null) {
                            orderInfo = data
                            zhiFuBaoPay()
                        }
                    })
                } else {
                    val uri = Uri.parse(Constans.BaseUrl + "/appapi/pay/orderSn/${data.order_sn}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

            }
        })

//        alertDialog?.onClickListener={
//            dialogFragment, s ->
//            dialogFragment.dismiss()
//        }
//        alertDialog?.show(childFragmentManager,"")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::wxHelper.isInitialized) {
            wxHelper?.unInit()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }


    private fun getData(){

        adapter?.data.clear()
        if (items?.isNotEmpty()==true){
            adapter?.data.addAll(items!!)

            var input = RechargeItem()
            input.coinRate = coinMoneyRate?:1.0
            input.canInput = true
            adapter?.data.add(input)
        }
        adapter?.notifyDataSetChanged()


    }

    private fun zhiFuBaoPay() {

        val payRunnable = Runnable {
            val alipay = PayTask(activity)
            val result = alipay.payV2(orderInfo, true)
            Log.i("msp", result.toString())

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            handler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val SDK_PAY_FLAG = 1

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.getResult()// 同步返回需要验证的信息
                    val resultStatus = payResult.getResultStatus()
                    val intent = Intent()

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showShort(context, "支付成功")
                        activity?.finish()

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showShort(context, "支付失败")
                    }

                }

                else -> {
                }
            }
        }
    }


}