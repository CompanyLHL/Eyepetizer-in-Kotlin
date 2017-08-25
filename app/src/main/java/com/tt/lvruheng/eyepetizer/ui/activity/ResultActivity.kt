package com.tt.lvruheng.eyepetizer.ui.activity

import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import kotlinx.android.synthetic.main.activity_find_detail.*

/**
 * Created by lvruheng on 2017/7/11.
 */
class ResultActivity : com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity(), com.tt.lvruheng.eyepetizer.mvp.contract.ResultContract.View, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    override fun needFullScreen(): Boolean = false

    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_result

    override fun initView() {
        keyWord = intent.getStringExtra("keyWord")
        mPresenter = com.tt.lvruheng.eyepetizer.mvp.presenter.ResultPresenter(this, this)
        mPresenter.requestData(keyWord, start)
        setToolbar()
        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)
        mAdapter = com.tt.lvruheng.eyepetizer.adapter.FeedAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
    }

    override fun setListener() {
        recyclerView.addOnScrollListener(object : android.support.v7.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: android.support.v7.widget.RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: android.support.v7.widget.LinearLayoutManager = recyclerView?.layoutManager as android.support.v7.widget.LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    start = start.plus(10)
                    mPresenter.requestData(keyWord, start)
                }
            }
        })
    }

    lateinit var keyWord: String
    lateinit var mPresenter: com.tt.lvruheng.eyepetizer.mvp.presenter.ResultPresenter
    lateinit var mAdapter: com.tt.lvruheng.eyepetizer.adapter.FeedAdapter
    var mIsRefresh: Boolean = false
    var mList = kotlin.collections.ArrayList<HotBean.ItemListBean.DataBean>()
    var start: Int = 10
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        bar?.title = "'$keyWord' 相关"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(bean: com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean) {
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            start = 10
            mPresenter.requestData(keyWord, start)
        }
    }
}