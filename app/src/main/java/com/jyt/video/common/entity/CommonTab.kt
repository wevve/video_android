package com.jyt.video.common.entity

import com.flyco.tablayout.listener.CustomTabEntity

class CommonTab:CustomTabEntity {
    private var tabName:String? = null

    constructor()
    constructor(tabName: String?) {
        this.tabName = tabName
    }


    override fun getTabUnselectedIcon(): Int {
        return 0
    }

    override fun getTabSelectedIcon(): Int {
        return 0
    }

    override fun getTabTitle(): String {
        return tabName?:""
    }

}