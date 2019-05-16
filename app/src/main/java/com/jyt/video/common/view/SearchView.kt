package com.jyt.video.common.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jyt.video.R
import com.jyt.video.common.util.DensityUtil

class SearchView : LinearLayout{

    var input:TextView?=null


    var canInput:Boolean = false

    constructor(context: Context):this(context,null){

    }

    constructor(context: Context, attrs: AttributeSet? = null):super(context, attrs){
        background = resources.getDrawable(R.drawable.shape_search_view)
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL


        var typeArray = context?.obtainStyledAttributes(attrs,R.styleable.SearchView)
        canInput = typeArray.getBoolean(R.styleable.SearchView_canInput,false)
        typeArray.recycle()
        createContent()
    }


    private fun createContent() {
        var image = ImageView(context)
        image.setImageDrawable(resources.getDrawable(R.mipmap.sousuo))
        var imageParams = LinearLayout.LayoutParams(DensityUtil.dpToPx(context,14),DensityUtil.dpToPx(context,14))
        imageParams.leftMargin = DensityUtil.dpToPx(context,10)
        imageParams.rightMargin = DensityUtil.dpToPx(context,6)
        image.layoutParams = imageParams

        addView(image)


        input = if (canInput) {
            EditText(context)
        }else{
            TextView(context)
        }
        input?.setSingleLine(true)
        input?.setPadding(0,0,0,0)
//        input?.setBackgroundColor(Color.RED)
        input?.background = null
        input?.hint = "搜索"
        input?.setHintTextColor(Color.WHITE)
        input?.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)
        input?.gravity = Gravity.CENTER_VERTICAL
        addView(input)

//        var inputParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        var inputParams = input?.layoutParams as LayoutParams
        inputParams.weight = 1f
        inputParams.rightMargin = DensityUtil.dpToPx(context,10)
        input?.layoutParams = inputParams
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }
}
