package com.chrissen.eyepetizer.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.model.bean.VideoBean
import com.chrissen.eyepetizer.ui.VideoDetailActivity
import com.chrissen.eyepetizer.utils.ImageLoadUtils
import com.chrissen.eyepetizer.utils.SPUtils
import io.reactivex.disposables.Disposable
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadFlag

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/15.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class DownloadAdapter(context: Context, list : ArrayList<VideoBean>) : RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    var mContext : Context? = null
    var mList : ArrayList<VideoBean>? = null
    var mInflater : LayoutInflater? = null

    var mIsDownload : Boolean = false
    var hasLoaded = false
    lateinit var disposable : Disposable
    lateinit var mOnLongClickListener : OnLongClickListener

    init {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DownloadViewHolder {
        return DownloadViewHolder(mContext!!, mInflater?.inflate(R.layout.item_download, parent, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DownloadViewHolder?, position: Int) {
        var photoUrl : String? = mList?.get(position)?.feed
        photoUrl?.let { ImageLoadUtils.display(mContext!!, holder?.iv_photo, it) }
        var title: String? = mList?.get(position)?.title
        holder?.tv_title?.text = title
        var category = mList?.get(position)?.category
        var duration = mList?.get(position)?.duration
        mIsDownload = SPUtils.getInstance(mContext!!, "download_state").getBoolean(mList?.get(position)?.playUrl!!)
        getDownloadState(mList?.get(position)?.playUrl, holder)
        if(mIsDownload){
            holder?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
        }else{
            holder?.iv_download_state?.setImageResource(R.drawable.icon_download_start)
        }
        holder?.iv_download_state?.setOnClickListener {
            if(mIsDownload){
                mIsDownload = false
                SPUtils.getInstance(mContext!!, "download_state").put(mList?.get(position)?.playUrl!!, false)
                holder?.iv_download_state?.setImageResource(R.drawable.icon_download_start)
                RxDownload.getInstance(mContext).pauseServiceDownload(mList?.get(position)?.playUrl).subscribe()
            }else{
                mIsDownload = true
                SPUtils.getInstance(mContext!!, "download_state").put(mList?.get(position)?.playUrl!!, true)
                holder?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                addMission(mList?.get(position)?.playUrl, position+1)
            }
        }
        holder?.itemView?.setOnClickListener {
            //跳转视频详情页
            var intent: Intent = Intent(mContext, VideoDetailActivity::class.java)
            var desc = mList?.get(position)?.description
            var playUrl = mList?.get(position)?.playUrl
            var blurred = mList?.get(position)?.blurred
            var collect = mList?.get(position)?.collect
            var share = mList?.get(position)?.share
            var reply = mList?.get(position)?.reply
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            var url = SPUtils.getInstance(mContext!!, "beans").getString(playUrl!!)
            intent.putExtra("data", videoBean as Parcelable)
            if(hasLoaded){
                var files = RxDownload.getInstance(mContext).getRealFiles(playUrl)
                var uri = Uri.fromFile(files!![0])
                intent.putExtra("loaclFile",uri.toString())
            }

            mContext?.let { context -> context.startActivity(intent) }
        }
    }

    private fun getDownloadState(playUrl: String?, holder: DownloadViewHolder?){
        disposable = RxDownload.getInstance(mContext).receiveDownloadStatus(playUrl)
                .subscribe { event ->
                    if(event.flag == DownloadFlag.FAILED){
                        val throwable = event.error
                    }
                    var downloadStatus = event.downloadStatus
                    var percent = downloadStatus.percentNumber
                    if(percent == 100L){
                        if(!disposable.isDisposed && disposable != null){
                            disposable.dispose()
                        }
                        hasLoaded = true
                        holder?.iv_download_state?.visibility = View.GONE
                        holder?.tv_detail?.text = "已缓存"
                        mIsDownload = false
                        SPUtils.getInstance(mContext!!, "downlaod_state").put(playUrl.toString(), false)
                    }else{
                        if(holder?.iv_download_state?.visibility != View.VISIBLE){
                            holder?.iv_download_state?.visibility = View.VISIBLE
                        }
                        if(mIsDownload){
                            holder?.tv_detail?.text = "缓存中/$percent%"
                        }else{
                            holder?.tv_detail?.text = "已缓存/$percent%"
                        }
                    }
                }
    }


    private fun addMission(playUrl: String?, count: Int){
        RxDownload.getInstance(mContext).serviceDownload(playUrl, "download$count").subscribe({
            Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(mContext, "添加任务失败", Toast.LENGTH_SHORT).show()
        })
    }


    class DownloadViewHolder(context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView){
        var iv_photo : ImageView = itemView?.findViewById(R.id.iv_photo) as ImageView
        var tv_title : TextView = itemView?.findViewById(R.id.tv_title) as TextView
        var tv_detail : TextView = itemView?.findViewById(R.id.tv_detail) as TextView
        var iv_download_state : ImageView = itemView?.findViewById(R.id.iv_download_state) as ImageView

        init {
            tv_title.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }
    }

    public interface OnLongClickListener{
        fun onLongClick(position: Int)
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener){
        mOnLongClickListener = onLongClickListener
    }

}