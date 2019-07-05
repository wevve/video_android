package com.jyt.video.common.util

import android.content.Context
import android.graphics.Bitmap
import com.alibaba.android.arouter.launcher.ARouter
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


import java.util.Hashtable


object QrCodeUtil {
    fun createQRcodeImage(url: String, w: Int, h: Int): Bitmap? {
        try {
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            //图像数据转换，使用了矩阵转换
            val bitMatrix = QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints)
            val pixels = IntArray(w * h)
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (y in 0 until h) {
                for (x in 0 until w) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = -0x1000000
                    } else {
                        pixels[y * w + x] = -0x1
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            //显示到我们的ImageView上面
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return null
    }


}
