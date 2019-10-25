package com.jyt.video.video.entity

import java.io.Serializable

class VideoDetail : Serializable {

    /**
     * videoCut : ["http://v.msvodx.com/XResource/20180522/5jt8TDMpezTrRZRWBn4QFbQJSYD387Zm.jpg","http://v.msvodx.com/XResource/20180522/C4JnYXAQk7AQT2Kt5GbxkDtpiZjmehPm.jpg","http://v.msvodx.com/XResource/20180522/t5rj7MXcsQB72rDBPCsaYbyPJSnaGjwW.jpg","http://v.msvodx.com/XResource/20180522/Apx3Xfp2RnzNSKEEywWF3DFTmYzTmWbk.jpg","http://v.msvodx.com/XResource/20180522/ixn8e3cDfp7szQsyHtPBjiiiFrES4TKh.jpg"]
     * isVip : true
     * ad : {"before":{"img":"http://v.msvodx.com/XResource/20180525/JzRWwtacxRWxyzkZw8jzkpxC3GchKT7H.jpg","url":"https://www.msvod.cc/"},"pause":{"img":"http://v.msvodx.com/XResource/20180525/JzRWwtacxRWxyzkZw8jzkpxC3GchKT7H.jpg","url":"https://www.msvod.cc/"}}
     * adTime : 5
     * feeLook : 30
     * videoInfo : {"title":"Plastic - WonderlandPlastic - Wonderland","url":"http://v.msvodx.com/mp4/65100154FF586807762E1F417B6905FA.mp4","thumbnail":"http://v.msvodx.com/static/pic2018/054205085645E55E6A0A4D0475B01B0D.jpg","info":"Msvod魅思Cms V10 相对以前版本功能更加强大、稳定同时提升了用户使用体验，全新的模板+全新开发的系统内核，本次我司采用最新 ThinkPHP框架版本 5.0.11 进行开发Msvod V10 版本 也是魅思视频产品终极版更新换代，以后升级都会在V10基础上升级，不会再更新换代，为什么由V5到V9版本我们都一直在换代换程序内核，原因是每次开发出来的版本都会被外面盗版倒卖，严重损失我司权益，本身换代我们采取防止盗版倒卖行为，也就是说MSVOD V10 所有站长的管理后台我们采取软件统一管理，购买MSVOD V10的站长只需要凭用户账号密码登陆软件就可以管理后台，没有域名限制，前台代码开源可二次开发 自由更换模板等。更多详细请到我们演示站体验吧","short_info":"","gold":10,"click":2666,"class":"27"}
     * isShowComments : 1
     * isLike : 1
     * isCollection : 1
     * guess : [{"id":229,"title":"洪真英 - 爱不爱洪真英 - 爱不爱","thumbnail":"http://v.msvodx.com/static/pic2018/054208085889F10F6F0A2F5B68CA73F9.jpg","good":0,"play_time":"00:03:23","click":2336,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":228,"title":"NCT DREAM - 最后的初恋 韩文版NCT DREAM - 最后的初恋 韩文版","thumbnail":"http://v.msvodx.com/static/pic2018/0542080857A835016A0A49045116E024.jpg","good":0,"play_time":"00:03:43","click":2625,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":227,"title":"MC Hansai - AlcoholicMC Hansai - Alcoholic","thumbnail":"http://v.msvodx.com/static/pic2018/0542080855FF59DC6A0A456D6031F74D.jpg","good":0,"play_time":"00:03:56","click":4144,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":224,"title":"MC Hansai - 宇宙出面MC Hansai - 宇宙出面","thumbnail":"http://v.msvodx.com/static/pic2018/0542080851A3CE996A0A400E09D0C2D2.jpg","good":0,"play_time":"00:02:45","click":3467,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":223,"title":"Circus100、RAMJA - 深夜青春俱乐部Circus100、RAMJA - 深夜青春俱乐部","thumbnail":"http://v.msvodx.com/static/pic2018/0542070858B962C9000001080B0F1212.jpg","good":0,"play_time":"00:03:11","click":2495,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":226,"title":"STi - We ClickSTi - We Click","thumbnail":"http://v.msvodx.com/static/pic2018/0542080853883CAD6A0A4404643FF4E1.jpg","good":0,"play_time":"00:04:18","click":794,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":225,"title":"Jager 视频 Lucas、李艺琳 - Shake ThatJager 视频 Lucas、李艺琳 - Shake That","thumbnail":"http://v.msvodx.com/static/pic2018/0542080851A7033C6A0A4179795B0DE0.jpg","good":0,"play_time":"00:03:17","click":3252,"add_time":1516329866,"gold":10,"update_time":1516329877},{"id":222,"title":"SF9 - Roar 视频版SF9 - Roar 视频版","thumbnail":"http://v.msvodx.com/static/pic2018/0542070856C6862C6A0A480461FC88CD.jpg","good":0,"play_time":"00:04:50","click":1976,"add_time":1516329866,"gold":10,"update_time":1516329877}]
     * alreadyBuy : 1
     */

     var isVip: Boolean = false
     var ad: AdBean? = null
     var adTime: String? = null
     var feeLook: String? = null
     var videoInfo: VideoInfoBean? = null
     var price:String?=null
     var isShowComments: String? = null
     var isLike: Int = 0
     var isCollection: Int = 0
     var alreadyBuy: Int = 0
     var videoCut: List<String>? = null
     var guess: List<ThumbVideo>? = null

    var videoId: Long? = null

 var buyTimeExists: String? = null

}
