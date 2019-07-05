package com.jyt.video.video.dialog

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jyt.video.R
import com.jyt.video.common.util.DensityUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.dialog_big_image.*

class BigImageDialog : DialogFragment(){


    lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_big_image,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_bg.setOnClickListener { dismissAllowingStateLoss() }

           var options = RequestOptions.bitmapTransform(RoundedCornersTransformation(DensityUtil.dpToPx(context,5),0))
//        Glide.with(context).asBitmap().load(url).apply(options).into(img_cover)
        Glide.with(context).asBitmap().load(url).apply(options).into(object :SimpleTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {

                if (resource!=null) {
                    var imgw = DensityUtil.dpToPx(context, 330)
                    var lp = FrameLayout.LayoutParams(imgw, (resource.height*1f / resource.width * imgw).toInt())
                    lp.gravity = Gravity.CENTER
                    img_cover.layoutParams = lp

                    img_cover.setImageBitmap(resource)

                }
            }


        })
    }

    override fun onStart() {
        super.onStart()

//        dialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}