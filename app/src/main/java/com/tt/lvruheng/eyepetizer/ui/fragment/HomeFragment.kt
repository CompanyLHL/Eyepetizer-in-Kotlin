package com.tt.lvruheng.eyepetizer.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HomeAdatper
import com.tt.lvruheng.eyepetizer.mvp.contract.HomeContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean.IssueListBean.ItemListBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.HomePresenter
import com.tt.lvruheng.eyepetizer.utils.RegexU
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import java.util.regex.Pattern

/**
 * Created by lvruheng on 2017/7/4.
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    var mIsRefresh: Boolean = false
    var mPresenter: HomePresenter? = null
    var mList = ArrayList<ItemListBean>()
    var mAdapter: HomeAdatper? = null
    var data: String? = null
    override fun setData(bean: HomeBean) {
        val p = Pattern.compile(RegexU.numberRegex)
        val m = p.matcher(bean?.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }
        mAdapter?.notifyDataSetChanged()
    }


    override fun getLayoutResources(): Int = R.layout.home_fragment

    override fun initView() {
        mPresenter = HomePresenter(context, this)
        mPresenter?.start()
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeAdatper(context, mList)
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.moreData(data)
                    }

                }
            }
        })

    }

    override fun setListener() {
        refreshLayout.setOnRefreshListener {
            SwipeRefreshLayout.OnRefreshListener {
                if (!mIsRefresh) {
                    mIsRefresh = true
                    mPresenter?.start()
                }
            }
        }
    }
}