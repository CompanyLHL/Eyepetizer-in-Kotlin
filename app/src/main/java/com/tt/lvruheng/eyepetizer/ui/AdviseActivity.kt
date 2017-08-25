package com.tt.lvruheng.eyepetizer.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity
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

    override fun getArgs(bundle: Bundle?) {
    }

    override fun setView(): Int = R.layout.activity_advise
}