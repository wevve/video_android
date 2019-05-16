package com.jyt.video.home.entity

class TabEntity{
    var tabId:Long?=null
    var tabName:String? = null
    var sel:Boolean? = null


    constructor()
    constructor(tabName: String?) {
        this.tabName = tabName
    }

    constructor(tabId: Long?, tabName: String?, sel: Boolean?) {
        this.tabId = tabId
        this.tabName = tabName
        this.sel = sel
    }


}