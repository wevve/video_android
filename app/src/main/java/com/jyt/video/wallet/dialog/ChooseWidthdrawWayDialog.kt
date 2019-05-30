package com.jyt.video.wallet.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.setting.entity.WithdrawAccount
import com.jyt.video.wallet.adapter.WidthdrawAccountAdapter
import kotlinx.android.synthetic.main.dialog_choose_widthdraw_way.*

class ChooseWidthdrawWayDialog : DialogFragment() ,BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<Any>>{


    var  account: ArrayList<WithdrawAccount>? = null


    var itemClickListener: ((data:Any)->Unit)? = null
    internal lateinit var adapter: WidthdrawAccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.dialog_choose_widthdraw_way, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        adapter.data.clear()

        if (account!=null){
            adapter.data.addAll(account!!)
            adapter.notifyDataSetChanged()
        }

    }

    override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
       var data  = holder.data

        itemClickListener?.invoke(data!!)
        dismiss()
    }
    private fun initView(){
        adapter = WidthdrawAccountAdapter()
        adapter.setOnTriggerListener(this)
        recycler_view.adapter = adapter;
        recycler_view.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.window.setGravity(Gravity.BOTTOM)
    }



}
