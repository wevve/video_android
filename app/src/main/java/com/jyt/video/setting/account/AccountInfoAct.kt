package com.jyt.video.setting.account

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import kotlinx.android.synthetic.main.act_account_info.*
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import android.net.Uri
import com.binbook.binbook.common.util.GlideHelper
import com.bumptech.glide.Glide
import com.jyt.video.common.helper.Glide4Engine
import com.zhihu.matisse.filter.Filter.K
import com.jyt.video.service.ServiceCallback
import com.jyt.video.service.UserService
import com.jyt.video.service.impl.UserServiceImpl
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.utils.PathUtils
import java.io.File
import java.util.jar.Manifest
import android.widget.Toast
import com.jyt.video.main.MainActivity
import android.content.DialogInterface
import android.widget.EditText
import android.support.v7.app.AlertDialog
import com.jyt.video.common.entity.BaseJson
import com.jyt.video.common.helper.UserInfo
import com.jyt.video.common.util.ToastUtil


@Route(path = "/setting/account")
class AccountInfoAct :BaseAct(), View.OnClickListener {

    lateinit var userService:UserService

    override fun onClick(v: View?) {
        when(v){
            fl_edit_avatar->{
                RxPermissions(this).request(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ).subscribe {
                    Matisse.from(this@AccountInfoAct)
                        .choose(MimeType.of(MimeType.PNG,MimeType.JPEG, MimeType.PNG,MimeType.BMP, MimeType.WEBP))
                        .countable(false)
                        .maxSelectable(1)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .imageEngine(Glide4Engine())
                        .forResult(1)
                }

            }
            fl_edit_name->{
                val editText = EditText(this)
                var builder = AlertDialog.Builder(this).setTitle("").setView(editText)
                    .setTitle("修改昵称")
                    .setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
                       dialogInterface.dismiss()
                    })
                    .setPositiveButton("确定",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            var input = editText.text.toString()
                            if (input.isNullOrEmpty()){
                                ToastUtil.showShort(this,"请输入昵称")
                                return@OnClickListener
                            }
                            userService.modifyNickName(editText.text.toString(),ServiceCallback{
                                code, data ->
                                if (code==BaseJson.CODE_SUCCESS){
                                    dialogInterface.dismiss()

                                    tv_name.text = input
                                }
                            })

                        })
                builder.create().show()
            }
            fl_to_cs_account->{
                ARouter.getInstance().build("/setting/account/cs").navigation()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.act_account_info
    }

    override fun initView() {

        userService = UserServiceImpl()

        fl_edit_avatar.setOnClickListener(this)
        fl_edit_name.setOnClickListener(this)
        fl_to_cs_account.setOnClickListener(this)


        tv_name.text = UserInfo.getUserHomeInfo()?.nickname

        Glide.with(this).load(UserInfo.getUserHomeInfo()?.avatar).apply(GlideHelper.avatar()).into(img_avatar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1 && resultCode == RESULT_OK) {
                 var mSelected:List<Uri> = Matisse.obtainResult(data);

                Glide.with(this).load(mSelected[0]).apply(GlideHelper.avatar()).into(img_avatar)

                var file = File(PathUtils.getPath(this,mSelected[0]))
                userService.modifyAvatar(file, ServiceCallback{
                    code, data ->

                })
            }
    }


}