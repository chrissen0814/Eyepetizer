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
import com.chrissen.eyepetizer.mvp.model.bean.VideoBean
import com.chrissen.eyepetizer.ui.VideoDetailActivity
import com.chrissen.eyepetizer.utils.ImageLoadUtils
import com.chrissen.eyepetizer.utils.ObjectSaveUtils
import com.chrissen.eyepetizer.utils.SPUtils
import java.text.SimpleDateFormat

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class WatchAdapter(context: Context, list: ArrayList<VideoBean>) : RecyclerView.Adapter<WatchAdapter.WatchViewHolder>() {

    var mContext: Context? = null
    var mList : ArrayList<VideoBean>? = null
    var mInflater : LayoutInflater? = null

    init {
        this.mContext = context
        this.mList = list
        this.mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WatchViewHolder {
        return WatchViewHolder(mContext!!, mInflater?.inflate(R.layout.item_feed_result, parent, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: WatchViewHolder?, position: Int) {
        var photoUrl : String? = mList?.get(position)?.feed
        photoUrl?.let { ImageLoadUtils.display(mContext!!,holder?.iv_photo, it) }
        var title : String? = mList?.get(position)?.title
        holder?.tv_title?.text = title
        var category = mList?.get(position)?.category
        var duration = mList?.get(position)?.duration
        var minute =duration?.div(60)
        var second = duration?.minus((minute?.times(60)) as Long )
        var releaseTime = mList?.get(position)?.time
        var smf : SimpleDateFormat = SimpleDateFormat("MM-dd")
        var date = smf.format(releaseTime)
        var realMinute : String
        var realSecond : String
        if(minute!!<10){
            realMinute = "0"+minute
        }else{
            realMinute = minute.toString()
        }
        if(second!!<10){
            realSecond = "0"+second
        }else{
            realSecond = second.toString()
        }
        holder?.tv_time?.text = "$category / $realMinute'$realSecond'' / $date"
        holder?.itemView?.setOnClickListener {
            //跳转视频详情页
            var intent : Intent = Intent(mContext, VideoDetailActivity::class.java)
            var desc = mList?.get(position)?.description
            var playUrl = mList?.get(position)?.playUrl
            var blurred = mList?.get(position)?.blurred
            var collect = mList?.get(position)?.collect
            var share = mList?.get(position)?.share
            var reply = mList?.get(position)?.reply
            var time = System.currentTimeMillis()
            var videoBean  = VideoBean(photoUrl,title,desc,duration,playUrl,category,blurred,collect ,share ,reply,time)
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


    class WatchViewHolder(context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView){
        var iv_photo = itemView?.findViewById(R.id.iv_photo) as ImageView
        var tv_title = itemView?.findViewById(R.id.tv_title) as TextView
        var tv_time : TextView = itemView?.findViewById(R.id.tv_detail) as TextView

        init {
            tv_title?.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }
    }

}