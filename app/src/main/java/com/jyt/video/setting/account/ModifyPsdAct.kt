package com.jyt.video.setting.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.dialog.AlertDialog
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.SearchHistoryService
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_modify_psd.*

@Route(path = "/modify/psd")
class ModifyPsdAct : BaseAct(), View.OnClickListener {

    lateinit var userService: UserService
    override fun onClick(v: View?) {
        when(v){
            btn_done->{
                var oldPsd = input_old_psd.text.toString()
                var newPsd1 = input_new_psd1.text.toString()
                var newPsd2 = input_new_psd2.text.toString()
                if (oldPsd.isNullOrEmpty()){
                    ToastUtil.showShort(this,"请输入旧密码")
                    return
                }
                if(newPsd1.isNullOrEmpty()){
                    ToastUtil.showShort(this,"请输入新密码")
                    return
                }
                if(newPsd2.isNullOrEmpty()){
                    ToastUtil.showShort(this,"请再次输入新密码")
                    return
                }
                if (newPsd1!=newPsd2){
                    ToastUtil.showShort(this,"两次密码不一致")
                    return
                }
                userService.modifyPsd(oldPsd,newPsd1, ServiceCallback{
                    code, data ->
                    if (code==BaseJson.CODE_SUCCESS){

                        var dialog = AlertDialog()
                        dialog.message = "修改成功"
                        dialog.leftBtnText="确定"
                        dialog.onClickListener = {
                            dialogFragment, s ->
                            dialogFragment.dismissAllowingStateLoss()
                            finish()
                        }
                        dialog.show(supportFragmentManager,"")
                    }

                })
            }
        }
    }

    override fun initView() {
        userService = UserServiceImpl()
        btn_done.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.act_modify_psd
    }



}