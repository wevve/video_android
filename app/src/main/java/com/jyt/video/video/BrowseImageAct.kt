package com.jyt.video.video

import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import com.jyt.video.R
import com.jyt.video.common.base.BaseAct
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ortiz.touchview.TouchImageView
import android.support.v4.view.PagerAdapter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.act_browse_image.*

@Route(path = "/browse/image")
class BrowseImageAct : BaseAct() {

    var images:ArrayList<String>? = null

    var startIndex = 0

     var adapter = TouchImageAdapter()

    override fun initView() {

        images = intent.getStringArrayListExtra("image")
        startIndex = intent.getIntExtra("index",0)

        view_pager.adapter = adapter

        adapter.notifyDataSetChanged()
        view_pager.currentItem = startIndex

    }

    override fun getLayoutId(): Int {
        return R.layout.act_browse_image
    }

    inner class TouchImageAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return images?.size?:0
        }

        @NonNull
        override fun instantiateItem(@NonNull container: ViewGroup, position: Int): View {
            val img = TouchImageView(container.context)

            Glide.with(container.context).load(images?.get(position)).into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                    img.setImageDrawable(resource)
                }
            })
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            return img
        }

        override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
            return view === `object`
        }


    }
}