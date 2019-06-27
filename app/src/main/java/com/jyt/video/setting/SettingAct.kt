package com.jyt.video.setting

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.BuildConfig
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import kotlinx.android.synthetic.main.act_setting.*

@Route(path = "/setting/index")
class SettingAct : BaseAct(), View.OnClickListener {
    lateinit var userService: UserService
    override fun onClick(v: View?) {
        when(v){
            fl_to_about->{

            }
            fl_to_account_detail->{
               ARouter.getInstance().build("/setting/account").navigation()
            }
            tv_logout->{
                UserInfo.logout()
                finish()
//                ARouter.getInstance().build("/setting/account/cs").navigation()
            }
            fl_check_version->{
                userService.getVersion(ServiceCallback{
                    code, data ->
                    if (data!=null){
                        var version = BuildConfig.VERSION_NAME
                        if ( data.apk_version != version){
                            if(data?.android.isNullOrBlank()){
                                return@ServiceCallback
                            }
                            AlertDialog.Builder(this)
                                .setTitle("检测到新版本")
                                .setMessage(data.apk_update)
                                .setPositiveButton("去下载") { dialog, which ->
                                    dialog.dismiss()

                                    val uri = Uri.parse(data.android)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(intent)
                                }
                                .setNegativeButton("取消") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .create().show()
                        }else{
                            ToastUtil.showShort(this,"已经是最新版本了")
                        }
                    }
                })
            }
        }
    }

    override fun initView() {
        userService = UserServiceImpl()
        fl_to_about.setOnClickListener(this)
        fl_to_account_detail.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
        fl_check_version.setOnClickListener(this)


        tv_cur_version.text = BuildConfig.VERSION_NAME
    }

    override fun getLayoutId(): Int {
        return R.layout.act_setting
    }
}
