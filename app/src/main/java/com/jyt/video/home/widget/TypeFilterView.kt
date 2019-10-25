package com.jyt.video.home.widget

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.gson.Gson
import com.jyt.video.R
import com.jyt.video.common.adapter.BaseRcvAdapter
import com.jyt.video.common.base.BaseVH
import com.jyt.video.home.adapter.GroupTypeAdapter
import com.jyt.video.home.adapter.SubTypeAdapter
import com.jyt.video.home.entity.TabEntity
import com.jyt.video.home.entity.VideoType
import kotlinx.android.synthetic.main.frag_type_video.view.*
import kotlinx.android.synthetic.main.vh_first_video_type.view.*

class TypeFilterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private var typeGroup: VideoType.TypeGroup? = null

    lateinit var groupAdapter: GroupTypeAdapter

    lateinit var subAdapter: SubTypeAdapter

    lateinit var tab:TabEntity

    var firstinit = true

    var type:VideoType.Type?=null
    var subType:VideoType.Type?=null
    var typeClickListener:((typeName:String,type:VideoType.Type?,subType:VideoType.Type?)->Unit?)? =null

    init {

        LayoutInflater.from(context).inflate(R.layout.vh_first_video_type, this, true)

        initRcv()

    }

    private fun initRcv(){
        groupAdapter = GroupTypeAdapter()
        groupAdapter.setOnTriggerListener(object : BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>> {
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                if ("itemClick" == event) {
                    var data = holder.data as VideoType.Type?
                    type = Gson().fromJson(Gson().toJson(data),VideoType.Type::class.java)
                    setupSubType(data!!)

                    triggerListener()
                }
            }
        })

        rcv_type.adapter = groupAdapter

        rcv_type.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        subAdapter = SubTypeAdapter()
        subAdapter.setOnTriggerListener(object :BaseRcvAdapter.OnViewHolderTriggerListener<BaseVH<*>>{
            override fun <T : BaseVH<*>> onTrigger(holder: T, event: String) {
                if("itemClick" == event){
                    var data = holder.data as VideoType.Type?
                    subType = data

                    triggerListener()
                }
            }

        })
        rcv_sub_type.adapter = subAdapter
        rcv_sub_type.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    fun setData(typeGroup: VideoType.TypeGroup) {
        this.typeGroup = typeGroup

        setupView(typeGroup)
    }


    private fun triggerListener(){
        typeClickListener?.invoke(typeGroup?.name?:"",type,subType)

    }

    private fun setupView(typeGroup: VideoType.TypeGroup) {




        tv_group_name.text = typeGroup.name
        groupAdapter.data.addAll(typeGroup.items)
        groupAdapter.curSel = typeGroup.items.get(0)


        groupAdapter.notifyDataSetChanged()


        if (typeGroup.name=="分类" && firstinit){

            typeGroup.items.forEachIndexed { index, type ->
                if (type.id==tab.tabId?.toInt()){
                    groupAdapter.curSel = type
                    this@TypeFilterView.type = type
                    setupSubType(type)

                    rcv_type.scrollToPosition(index)

                    typeClickListener?.invoke(typeGroup?.name?:"",type,subType)
                }
            }
            firstinit = false

        }


        groupAdapter.notifyDataSetChanged()



//        typeClickListener?.invoke(typeGroup?.name?:"",groupAdapter.curSel?.id,subType?.id)

    }


    private fun setupSubType(type: VideoType.Type) {
        if (type.subItem?.isEmpty()==false) {
            subType = type.subItem?.get(0)
            fl_sub_type.visibility = View.VISIBLE

            subAdapter.data.clear()
            subAdapter.data.addAll(type.subItem)
            subAdapter.curSel = type.subItem?.get(0)
            subAdapter.notifyDataSetChanged()
        } else {
            subType = null

            fl_sub_type.visibility = View.GONE
        }
    }


}
