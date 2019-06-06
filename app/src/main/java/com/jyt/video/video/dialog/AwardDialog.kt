package com.jyt.video.video.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.video.PlayVideoAct
import com.jyt.video.video.adapter.AwardDialogAdapter
import com.jyt.video.video.entity.Gift
import kotlinx.android.synthetic.main.dialog_award.*

//打赏
class AwardDialog :DialogFragment(), View.OnClickListener {



    var adapter:AwardDialogAdapter? = null

    var onGiftClick:((gift:Gift)->Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.dialog_award,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AwardDialogAdapter()
        adapter?.onTriggerListener = object :BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>{
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                when(event){
                    "itemClick"->{
                        onGiftClick?.invoke(holder.data as Gift)
                    }
                }
            }

        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(context,4)

        adapter?.data?.clear()
        if (PlayVideoAct.giftList?.isNotEmpty()==true) {
            adapter?.data?.addAll(PlayVideoAct.giftList!!)
        }
        adapter?.notifyDataSetChanged()


        btn_cancel.setOnClickListener(this)
        btn_done.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btn_cancel,btn_done->{
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }





}