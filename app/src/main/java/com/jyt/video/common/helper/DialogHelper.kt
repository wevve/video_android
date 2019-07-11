package com.jyt.video.common.helper

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog

class DialogHelper {
    companion object{

        fun showOpenPermissionDialog(context: Context):AlertDialog{
            var builder = AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("缺少权限会导致部分功能无法使用")
                .setNegativeButton("取消",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        dialog?.dismiss()
                    }

                })
                .setPositiveButton("去设置",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        val intent = Intent()
                        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = Uri.parse("package:" + context.getPackageName())
                        context.startActivity(intent)
                        dialog?.dismiss()

                    }

                })

            return builder.show()
        }
    }
}