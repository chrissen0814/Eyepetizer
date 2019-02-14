package com.chrissen.eyepetizer.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.model.bean.HotBean
import com.chrissen.eyepetizer.mvp.model.bean.VideoBean
import com.chrissen.eyepetizer.ui.VideoDetailActivity
import com.chrissen.eyepetizer.utils.ImageLoadUtils
import com.chrissen.eyepetizer.utils.ObjectSaveUtils
import com.chrissen.eyepetizer.utils.SPUtils

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class RankAdapter(context: Context, list : ArrayList<HotBean.ItemListBean.DataBean>) : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {


    var mContext : Context? = null
    var mList : ArrayList<HotBean.ItemListBean.DataBean>? = null
    var mInflater : LayoutInflater? = null

    init {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RankViewHolder {
        return RankViewHolder(mContext!!, mInflater?.inflate(R.layout.item_rank, parent!!, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RankViewHolder?, position: Int) {
        var photoUrl = mList?.get(position)?.cover?.feed
        photoUrl?.let { ImageLoadUtils.display(mContext!!, holder?.ivPhoto, photoUrl) }
        var title: String? = mList?.get(position)?.title
        holder?.tv_title?.text = title
        var category = mList?.get(position)?.category
        var duraruion = mList?.get(position)?.duration
        var minute = duraruion?.div(60)
        var second = duraruion?.minus(minute?.times(60) as Long)
        var realMinute : String
        var realSecond : String
        if(minute!! < 10){
            realMinute = "0" + minute
        }else{
            realMinute = minute.toString()
        }

        if(second!! < 10){
            realSecond = "0" + second
        }else{
            realSecond = second.toString()
        }
        holder?.tv_time?.text = "$category / $realMinute'$realSecond''"
        holder?.itemView?.setOnClickListener {
            //跳转视频详情页
            var intent : Intent = Intent(mContext, VideoDetailActivity::class.java)
            var desc = mList?.get(position)?.description
            var playUrl = mList?.get(position)?.playUrl
            var blurred = mList?.get(position)?.cover?.blurred
            var collect = mList?.get(position)?.consumption?.collectionCount
            var share = mList?.get(position)?.consumption?.shareCount
            var reply = mList?.get(position)?.consumption?.replyCount
            var time = System.currentTimeMillis()
            var videoBean  = VideoBean(photoUrl,title,desc,duraruion,playUrl,category,blurred,collect ,share ,reply,time)
            var url = SPUtils.getInstance(mContext!!,"beans").getString(playUrl!!)
            if(url.equals("")){
                var count = SPUtils.getInstance(mContext!!,"beans").getInt("count")
                if(count!=-1){
                    count = count.inc()
                }else{
                    count = 1
                }
                SPUtils.getInstance(mContext!!,"beans").put("count",count)
                SPUtils.getInstance(mContext!!,"beans").put(playUrl!!,playUrl)
                ObjectSaveUtils.saveObject(mContext!!,"bean$count",videoBean)
            }
            intent.putExtra("data",videoBean as Parcelable)
            mContext?.let { context -> context.startActivity(intent) }
        }
    }


    class RankViewHolder(context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView){
        var ivPhoto : ImageView? = null
        var tv_title : TextView? = null
        var tv_time: TextView? = null

        init {
            ivPhoto = itemView?.findViewById(R.id.iv_photo)
            tv_title = itemView?.findViewById(R.id.tv_title)
            tv_time = itemView?.findViewById(R.id.tv_time)
            tv_title?.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }
    }

}