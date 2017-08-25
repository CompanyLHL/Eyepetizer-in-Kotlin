package com.tt.lvruheng.eyepetizer.ui.activity

import com.tt.lvruheng.eyepetizer.utils.*
import kotlinx.android.synthetic.main.activity_video_detail.*


/**
 * Created by lvruheng on 2017/7/7.
 */
class VideoDetailActivity : com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity() {
    override fun needFullScreen(): Boolean = false

    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_video_detail

    override fun setListener() {
    }

    companion object {
        var MSG_IMAGE_LOADED = 101
    }

    var mContext: android.content.Context = this
    lateinit var imageView: android.widget.ImageView
    lateinit var bean: com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
    var isPlay: Boolean = false
    var isPause: Boolean = false
    lateinit var orientationUtils: com.shuyu.gsyvideoplayer.utils.OrientationUtils
    var mHandler: android.os.Handler = object : android.os.Handler() {
        override fun handleMessage(msg: android.os.Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                com.tt.lvruheng.eyepetizer.ui.activity.VideoDetailActivity.Companion.MSG_IMAGE_LOADED -> {
                    android.util.Log.e("video", "setImage")
                    gsy_player.setThumbImageView(imageView)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        bean = intent.getParcelableExtra<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>("data")
        var bgUrl = bean.blurred
        bgUrl?.let { com.tt.lvruheng.eyepetizer.utils.ImageLoadUtils.Companion.displayHigh(this, iv_bottom_bg, bgUrl) }
        tv_video_desc.text = bean.description
        tv_video_desc.typeface = com.tt.lvruheng.eyepetizer.utils.TextViewU.getTypeFace("fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_video_title.text = bean.title
        tv_video_title.typeface = com.tt.lvruheng.eyepetizer.utils.TextViewU.getTypeFace("fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        var category = bean.category
        var duration = bean.duration
        var minute = duration?.div(60)
        var second = duration?.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }
        tv_video_time.text = "$category / $realMinute'$realSecond''"
        tv_video_favor.text = bean.collect.toString()
        tv_video_share.text = bean.share.toString()
        tv_video_reply.text = bean.share.toString()
        tv_video_download.setOnClickListener {
            //点击下载
            var url = bean.playUrl?.let { it1 -> com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(this, "downloads").getString(it1) }
            if (url.equals("")) {
                var count = com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(this, "downloads").getInt("count")
                if (count != -1) {
                    count = count.inc()
                } else {
                    count = 1
                }
                com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(this, "downloads").put("count", count)
                com.tt.lvruheng.eyepetizer.utils.ObjectSaveUtils.saveObject(this, "download$count", bean)
                addMission(bean.playUrl, count)
            } else {
                showToast("该视频已经缓存过了")
            }
        }
        prepareVideo()

    }

    private fun addMission(playUrl: String?, count: Int) {
        zlc.season.rxdownload2.RxDownload.getInstance(this).serviceDownload(playUrl, "download$count").subscribe({
            showToast("开始下载")
            com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(this, "downloads").put(bean.playUrl.toString(), bean.playUrl.toString())
            com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(this, "download_state").put(playUrl.toString(), true)
        }, {
            showToast("添加任务失败")
        })
    }

    private fun prepareVideo() {
        var uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            android.util.Log.e("uri", uri)
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(bean.playUrl, false, null, null)
        }
        //增加封面
        imageView = android.widget.ImageView(this)
        imageView.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP
        com.tt.lvruheng.eyepetizer.ui.activity.VideoDetailActivity.ImageViewAsyncTask(mHandler, this, imageView).execute(bean.feed)
        gsy_player.titleTextView.visibility = android.view.View.GONE
        gsy_player.backButton.visibility = android.view.View.VISIBLE
        orientationUtils = com.shuyu.gsyvideoplayer.utils.OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true);
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false;
        gsy_player.isLockLand = false;
        gsy_player.isShowFullAnimation = false;
        gsy_player.isNeedLockFull = true;
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick();
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(mContext, true, true);
        }
        gsy_player.setStandardVideoAllCallBack(object : com.tt.lvruheng.eyepetizer.utils.VideoListener() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true;
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
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener(android.view.View.OnClickListener {
            onBackPressed()
        })

    }

    private class ImageViewAsyncTask(handler: android.os.Handler, activity: com.tt.lvruheng.eyepetizer.ui.activity.VideoDetailActivity, private val mImageView: android.widget.ImageView) : android.os.AsyncTask<String, Void, String>() {
        private var handler = handler
        private var mPath: String? = null
        private var mIs: java.io.FileInputStream? = null
        private var mActivity: com.tt.lvruheng.eyepetizer.ui.activity.VideoDetailActivity = activity
        override fun doInBackground(vararg params: String): String? {
            val future = com.bumptech.glide.Glide.with(mActivity)
                    .load(params[0])
                    .downloadOnly(100, 100)
            try {
                val cacheFile = future.get()
                mPath = cacheFile.absolutePath
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: java.util.concurrent.ExecutionException) {
                e.printStackTrace()
            }

            return mPath
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            try {
                mIs = java.io.FileInputStream(s)
            } catch (e: java.io.FileNotFoundException) {
                e.printStackTrace()
            }
            val bitmap = android.graphics.BitmapFactory.decodeStream(mIs)
            mImageView.setImageBitmap(bitmap)
            var message = handler.obtainMessage()
            message.what = com.tt.lvruheng.eyepetizer.ui.activity.VideoDetailActivity.Companion.MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.let {
            orientationUtils.backToProtVideo()
        }
        if (com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer.backFromWindowFull(this)) {
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
        com.shuyu.gsyvideoplayer.GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.let {
            orientationUtils.releaseListener()
        }
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig?.orientation == android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsy_player.isIfCurrentIsFullscreen) {
                    gsy_player.startWindowFullscreen(mContext, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (gsy_player.isIfCurrentIsFullscreen) {
                    com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                orientationUtils?.let { orientationUtils.isEnable = true }
            }
        }
    }
}
