package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.ui.activity.AdviseActivity
import com.tt.lvruheng.eyepetizer.ui.activity.CacheActivity
import com.tt.lvruheng.eyepetizer.ui.activity.WatchActivity
import kotlinx.android.synthetic.main.mine_fragment.*

/**
 * Created by lvruheng on 2017/7/4.
 */
class MineFragment : BaseFragment() {

    override fun getLayoutResources(): Int = R.layout.mine_fragment

    override fun initView() {
        tv_advise.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_watch.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_save.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun setListener() {
        tv_watch.setOnClickListener {
            var intent = Intent(activity, WatchActivity::class.java)
            startActivity(intent)
        }
        tv_advise.setOnClickListener {
            var intent = Intent(activity, AdviseActivity::class.java)
            startActivity(intent)
            tv_save.setOnClickListener {
                var intent = Intent(activity, CacheActivity::class.java)
                startActivity(intent)
            }
        }

    }
}