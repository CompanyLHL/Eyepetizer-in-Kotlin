package com.tt.lvruheng.eyepetizer.ui.activity

import kotlinx.android.synthetic.main.activity_find_detail.*

/**
 * Created by lvruheng on 2017/7/8.
 */
class FindDetailActivity : BaseActivity(), com.tt.lvruheng.eyepetizer.mvp.contract.FindDetailContract.View, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_find_detail

    override fun initView() {
        setToolbar()
        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)
        mAdapter = com.tt.lvruheng.eyepetizer.adapter.RankAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        mPresenter = com.tt.lvruheng.eyepetizer.mvp.presenter.FindDetailPresenter(this, this)
        mPresenter.requestData(name, "date")
        recyclerView.addOnScrollListener(object : android.support.v7.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: android.support.v7.widget.RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: android.support.v7.widget.LinearLayoutManager = recyclerView?.layoutManager as android.support.v7.widget.LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.requesMoreData(mstart, name, "date")
                        mstart = mstart.plus(10)
                    }

                }
            }
        })
    }

    override fun setListener() {
    }

    override fun needFullScreen(): Boolean = false

    lateinit var mPresenter: com.tt.lvruheng.eyepetizer.mvp.presenter.FindDetailPresenter
    lateinit var mAdapter: com.tt.lvruheng.eyepetizer.adapter.RankAdapter
    lateinit var data: String
    var mIsRefresh: Boolean = false
    var mList: ArrayList<com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean.ItemListBean.DataBean> = ArrayList()
    var mstart: Int = 10
    override fun setData(bean: com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean) {
        val regEx = "[^0-9]"
        val p = java.util.regex.Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl as CharSequence?)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
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

    lateinit var name: String

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        intent.getStringExtra("name")?.let {
            name = intent.getStringExtra("name")
            bar?.title = name
        }
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            mPresenter.requestData(name, "date")
        }
    }
}