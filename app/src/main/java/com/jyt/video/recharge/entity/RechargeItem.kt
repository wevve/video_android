package com.jyt.video.recharge.entity

class RechargeItem{
    var sel:Boolean = false
    var canInput:Boolean = false
    var price:Double? = 0.0
    var inputCoin:Double? = 0.0
    var coinRate:Double = 1.0
    // 自定义初始金额
    var customs:Long? = 0

    var cornId:Long? = null
    var title:String? = null
    var money:String? = null
    var corn:String? = null
    var tips:String? = null

    var time:String? = null
    var vipId:Long? = null
}