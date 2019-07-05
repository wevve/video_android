package com.jyt.video.common.dialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jyt.video.R
import com.jyt.video.common.util.DensityUtil
import kotlinx.android.synthetic.main.dialog_action_sheet.*

class ActionSheetDialog:DialogFragment(){


    var items:Array<Item>? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME,R.style.customDialog)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_action_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addItem()
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setGravity(Gravity.BOTTOM)
        dialog.window.decorView.setPadding(0,0,0,0)
//        dialog.window.decorView.background = null
    }

    private fun  addItem(){

       var clickListener =  object :View.OnClickListener{
           override fun onClick(v: View?) {
               onItemClickListener?.onClick(this@ActionSheetDialog,v!!.tag as Item)
           }

       }
        if(ll_content.childCount!=1) {
            ll_content.removeViews(1, ll_content.childCount - 1)
        }
        items?.forEach {
           var item =  createSheetItem(it)

            item.setOnClickListener(clickListener)

            ll_content.addView(item)
        }
    }

    private fun createSheetItem( item: Item):View{
        var ll = LinearLayout(context)
        ll.tag = item
        ll.gravity = Gravity.CENTER
        var llParams =LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dpToPx(context,65))
        ll.layoutParams = llParams


        item.imageId?.let {
            var image = ImageView(context)
            var imageParams = LinearLayout.LayoutParams(DensityUtil.dpToPx(context,60),LinearLayout.LayoutParams.WRAP_CONTENT)
            imageParams.rightMargin = DensityUtil.dpToPx(context,18)
            image.layoutParams = imageParams
            image.adjustViewBounds = true
            Glide.with(context).load(it).into(image)
//            image.setImageDrawable(resources.getDrawable(it))
            ll.addView(image)

        }

        item.title?.let {
            var title = TextView(context)
            title.text = it
            title.setTextColor(Color.parseColor("#333333"))
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            ll.addView(title)

        }

        return ll
    }

    class Item{
        var title:String? = null
        var imageId:Any? = null
        var extra:Any? = null

        constructor(title: String?, imageId: Any?) {
            this.title = title
            this.imageId = imageId
        }
    }

    public interface OnItemClickListener{
        fun onClick(dialogFragment: DialogFragment,item:Item)
    }
}