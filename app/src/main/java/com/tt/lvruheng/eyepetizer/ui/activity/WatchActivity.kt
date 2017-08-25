package com.tt.lvruheng.eyepetizer.ui.activity

import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import kotlinx.android.synthetic.main.activity_watch.*

/**
 * Created by lvruheng on 2017/7/11.
 */
class WatchActivity : com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity() {
    override fun needFullScreen(): Boolean = false

    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_watch

    override fun initView() {
        setToolbar()
        com.tt.lvruheng.eyepetizer.ui.activity.WatchActivity.DataAsyncTask(mHandler, this).execute()
        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)
        mAdapter = com.tt.lvruheng.eyepetizer.adapter.WatchAdapter(this, mList)
        recyclerView.adapter = mAdapter
    }

    override fun setListener() {
    }

    var mList = ArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>()
    lateinit var mAdapter: com.tt.lvruheng.eyepetizer.adapter.WatchAdapter
    var mHandler: android.os.Handler = object : android.os.Handler() {
        override fun handleMessage(msg: android.os.Message?) {
            super.handleMessage(msg)
            var list = msg?.data?.getParcelableArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>("beans")
            if (list?.size?.compareTo(0) == 0) {
                tv_hint.visibility = android.view.View.VISIBLE
            } else {
                tv_hint.visibility = android.view.View.GONE
                if (mList.size > 0) {
                    mList.clear()
                }
                list?.let { mList.addAll(it) }
                mAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        bar?.title = "观看记录"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private class DataAsyncTask(handler: android.os.Handler, activity: com.tt.lvruheng.eyepetizer.ui.activity.WatchActivity) : android.os.AsyncTask<Void, Void, ArrayList<VideoBean>>() {
        var activity: com.tt.lvruheng.eyepetizer.ui.activity.WatchActivity = activity
        var handler = handler
        override fun doInBackground(vararg params: Void?): ArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>? {
            var list = ArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>()
            var count: Int = com.tt.lvruheng.eyepetizer.utils.SPUtils.Companion.getInstance(activity, "beans").getInt("count")
            var i = 1
            while (i.compareTo(count) <= 0) {
                var bean: com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean = com.tt.lvruheng.eyepetizer.utils.ObjectSaveUtils.getValue(activity, "bean$i") as com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
                list.add(bean)
                i++
            }
            return list
        }

        override fun onPostExecute(result: ArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean>?) {
            super.onPostExecute(result)
            var message = handler.obtainMessage()
            var bundle = android.os.Bundle()
            bundle.putParcelableArrayList("beans", result)
            message.data = bundle
            handler.sendMessage(message)
        }

    }
}