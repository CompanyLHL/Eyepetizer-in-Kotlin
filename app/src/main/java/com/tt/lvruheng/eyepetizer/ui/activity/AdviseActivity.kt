package com.tt.lvruheng.eyepetizer.ui.activity

import kotlinx.android.synthetic.main.activity_advise.*

/**
 * Created by lvruheng on 2017/7/11.
 */
class AdviseActivity : BaseActivity() {
    override fun needFullScreen(): Boolean = false

    override fun initView() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        bar?.title = "意见反馈"
        bar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun setListener() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_advise
}