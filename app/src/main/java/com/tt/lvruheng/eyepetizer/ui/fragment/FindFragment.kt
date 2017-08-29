package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Intent
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.FindAdapter
import com.tt.lvruheng.eyepetizer.mvp.contract.FindContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.FindPresenter
import com.tt.lvruheng.eyepetizer.ui.activity.FindDetailActivity
import kotlinx.android.synthetic.main.find_fragment.*

/**
 * Created by lvruheng on 2017/7/4.
 */
class FindFragment : BaseFragment(), FindContract.View {
    var mPresenter: FindPresenter? = null
    var mAdapter: FindAdapter? = null
    var mList: MutableList<FindBean>? = null
    override fun setData(beans: MutableList<FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        mAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int = R.layout.find_fragment

    override fun initView() {
        mPresenter = FindPresenter(context, this)
        mPresenter?.start()
        mAdapter = FindAdapter(context, mList)
        gv_find.adapter = mAdapter

    }

    override fun setListener() {
        gv_find.setOnItemClickListener { parent, view, position, id ->
            var bean = mList?.get(position)
            var name = bean?.name
            var intent: Intent = Intent(context, FindDetailActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)

        }
    }

}