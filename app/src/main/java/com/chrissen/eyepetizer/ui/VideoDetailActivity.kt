package com.chrissen.eyepetizer.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.model.bean.VideoBean
import com.chrissen.eyepetizer.utils.*
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_video_detail.*
import zlc.season.rxdownload2.RxDownload
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.ExecutionException

class VideoDetailActivity : AppCompatActivity() {

    companion object {
        var MSG_IMAGE_LOADED = 101
    }

    var mContext: Context = this
    lateinit var imageView: ImageView
    lateinit var bean: VideoBean
    var isPlay: Boolean = false
    var isPause: Boolean = false
    //Kotlin中属性在声明的同时也要求要被初始化
    //lateinit -> late + init 延迟初始化 lateinit只能和var一起使用
    //這可能是因為當你選擇使用lateinit的時候，等於你告訴compiler說你在之後一定會進行初始化
    //by lazy要求属性声明为val，即不可变变量，在java中相当于被final修饰
    lateinit var orientationUtils: OrientationUtils
    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                MSG_IMAGE_LOADED -> {
                    gsy_player.setThumbImageView(imageView)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        bean = intent.getParcelableExtra("data")
        initView()
        prepareVideo()
    }

    private fun prepareVideo() {
        var uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(bean.playUrl, false, null, null)
        }
        imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageViewAsyncTask(mHandler, this, imageView).execute(bean.feed)
        gsy_player.titleTextView.visibility = View.GONE
        gsy_player.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true)

        gsy_player.isRotateViewAuto = false
        gsy_player.isLockLand = false
        gsy_player.isShowFullAnimation = false
        gsy_player.isNeedLockFull = true
        gsy_player.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
            gsy_player.startWindowFullscreen(mContext, true, true)
        }
        gsy_player.setStandardVideoAllCallBack(object : VideoListener(){
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                orientationUtils.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.let { orientationUtils.backToProtVideo() }
            }
        })
        gsy_player.setLockClickListener(object : LockClickListener{
            override fun onClick(view: View?, lock: Boolean) {
                orientationUtils.isEnable = !lock
            }
        })
        gsy_player.backButton.setOnClickListener { onBackPressed() }

    }

    private fun initView() {
        var bgUrl = bean.blurred
        bgUrl?.let { ImageLoadUtils.displayHigh(mContext, iv_bottom_bg, bgUrl) }
        tv_video_desc.text = bean.description
        tv_video_desc.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_video_title.text = bean.title
        tv_video_title.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        var category = bean.category
        var duration = bean.duration
        var minute = duration?.div(60)
        var second = duration?.minus(minute?.times(60) as Long)
        var realMintue: String
        var realSecond: String
        realMintue = if (minute!! < 10) {
            "0" + minute
        } else {
            minute.toString()
        }

        realSecond = if (second!! < 10) {
            "0" + second
        } else {
            second.toString()
        }

        tv_video_time.text = "$category / $realMintue'$realSecond'"
        tv_video_favor.text = bean.collect.toString()
        tv_video_share.text = bean.share.toString()
        tv_video_reply.text = bean.share.toString()

        tv_video_download.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var url = bean.playUrl?.let { itl ->
                    SPUtils.getInstance(mContext, "downloads").getString(itl)
                }
                if (url.equals("")) {
                    var count = SPUtils.getInstance(mContext, "downloads").getInt("count")
                    if (count != -1) {
                        count = count.inc()
                    } else {
                        count = 1
                    }
                    SPUtils.getInstance(mContext, "downloads").put("count", count)
                    ObjectSaveUtils.saveObject(mContext, "downloads$count", bean)
                    addMission(bean.playUrl, count)
                } else {
                    showToast("该视频已经缓存过了")
                }
            }
        })
    }

    private fun addMission(playUrl: String?, count: Int) {
        RxDownload.getInstance(mContext).serviceDownload(playUrl, "downloads$count")
                .subscribe(object : Observer<Any> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onNext(t: Any?) {
                        showToast("开始下载")
                        SPUtils.getInstance(mContext, "downloads").put(bean.playUrl.toString(), bean.playUrl.toString())
                        SPUtils.getInstance(mContext, "download_state").put(playUrl.toString(), true)
                    }

                    override fun onError(e: Throwable?) {
                        showToast("添加任务失败")
                    }
                })
    }

    private class ImageViewAsyncTask(handler: Handler, activity: VideoDetailActivity, private val mImageView: ImageView) : AsyncTask<String, Void, String>() {

        private var handler = handler
        private var mPath: String? = null
        private var mIs: FileInputStream? = null
        private var mActivity: VideoDetailActivity = activity

        override fun doInBackground(vararg params: String?): String? {
            val future = Glide.with(mActivity)
                    .load(params[0])
                    .downloadOnly(100, 100)
            try {
                val cacheFile = future.get()
                mPath = cacheFile.absolutePath
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
            return mPath
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                mIs = FileInputStream(result)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val bitmap = BitmapFactory.decodeStream(mIs)
            mImageView.setImageBitmap(bitmap)
            var message = handler.obtainMessage()
            message.what = MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.let { orientationUtils.backToProtVideo() }
        if(StandardGSYVideoPlayer.backFromWindowFull(this)){
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.let { orientationUtils.releaseListener() }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if(!gsy_player.isIfCurrentIsFullscreen){
                    gsy_player.startWindowFullscreen(mContext, true, true)
                }
            }else{
                if(gsy_player.isIfCurrentIsFullscreen){
                    StandardGSYVideoPlayer.backFromWindowFull(this)
                }
                orientationUtils?.let { orientationUtils.isEnable = true }
            }
        }
    }

}
