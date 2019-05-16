package com.jyt.video.video.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.jyt.video.R
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.entity.CollectionVideo
import kotlinx.android.synthetic.main.vh_collection_video.*
import kotlinx.android.synthetic.main.vh_collection_video.cb_sel

class VideoCollectionItemVH(parent: View) : BaseVH<Any>(
    LayoutInflater.from(parent.context).inflate(
        R.layout.vh_collection_video,
        parent as ViewGroup,
        false
    )
), View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private var showCheckBox=false

    init {
        itemView.setOnClickListener(this)
        cb_sel.setOnCheckedChangeListener(this)
    }
    override fun onClick(v: View?) {
        when(v){
            itemView->{
                if (showCheckBox){

                }else {
                    onTriggerListener?.onTrigger(this, "click")
                }
            }
        }
    }
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView){
            cb_sel->{
                if (isChecked){
                    onTriggerListener?.onTrigger(this,"sel")
                }else{
                    onTriggerListener?.onTrigger(this,"disSel")

                }
            }
        }
    }

    override fun bindData(data: Any?) {
        super.bindData(data)
        var data = data as CollectionVideo
        cb_sel.isChecked = data.isSel
    }

    public fun showCheckBox(show:Boolean){
        this.showCheckBox = show
        if (show){
            cb_sel.visibility = View.VISIBLE
            img_next.visibility = View.GONE
        }else{
            cb_sel.visibility = View.GONE
            img_next.visibility = View.VISIBLE

        }
    }

}
