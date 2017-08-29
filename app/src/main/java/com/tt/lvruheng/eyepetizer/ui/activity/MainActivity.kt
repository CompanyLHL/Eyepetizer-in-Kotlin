package com.tt.lvruheng.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.search.SEARCH_TAG
import com.tt.lvruheng.eyepetizer.search.SearchFragment
import com.tt.lvruheng.eyepetizer.ui.fragment.*
import com.tt.lvruheng.eyepetizer.utils.DateU
import com.tt.lvruheng.eyepetizer.utils.showToast
import com.tt.lvruheng.eyepetizer.utils.TextViewU
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), View.OnClickListener {

    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragemnt: HotFragment? = null
    var mineFragment: MineFragment? = null
    var mExitTime: Long = 0
    var toast: Toast? = null
    lateinit var searchFragment: SearchFragment


    override fun needFullScreen(): Boolean = false

    override fun getArgs(bundle: Bundle?) {
        initFragment(bundle)
    }

    override fun setView(): Int = R.layout.activity_main

    override fun initView() {
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        setRadioButton()
        initToolbar()
    }

    override fun setListener() {
        iv_search.setOnClickListener {
            if (rb_mine.isChecked) {
                //todo 点击设置
            } else {
                //todo 点击搜索
                searchFragment = SearchFragment()
                searchFragment.show(fragmentManager, SEARCH_TAG)
            }

        }
    }


    private fun initToolbar() {
        var today = DateU.getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = TextViewU.getTypeFace("fonts/Lobster-1.4.otf")

    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is HotFragment) {
                    hotFragemnt = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            mineFragment = MineFragment()
            hotFragemnt = HotFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content, homeFragment)
            fragmentTrans.add(R.id.fl_content, findFragment)
            fragmentTrans.add(R.id.fl_content, mineFragment)
            fragmentTrans.add(R.id.fl_content, hotFragemnt)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .hide(hotFragemnt)
                .commit()
    }

    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_find -> {
                switchFooterState(rb_find)
                switchFraState(findFragment, homeFragment, hotFragemnt, mineFragment)
                switchTitleState("Discover", View.VISIBLE)
                switchSearchRes(R.drawable.icon_search)
            }
            R.id.rb_home -> {
                switchFooterState(rb_home)
                switchFraState(homeFragment, findFragment, hotFragemnt, mineFragment)
                switchTitleState(DateU.getToday(), View.VISIBLE)
                switchSearchRes(R.drawable.icon_search)
            }
            R.id.rb_hot -> {
                switchFooterState(rb_hot)
                switchFraState(hotFragemnt, findFragment, homeFragment, mineFragment)
                switchTitleState("Ranking", View.VISIBLE)
                switchSearchRes(R.drawable.icon_search)
            }
            R.id.rb_mine -> {
                switchFooterState(rb_mine)
                switchFraState(mineFragment, findFragment, homeFragment, hotFragemnt)
                switchTitleState("", View.INVISIBLE)
                switchSearchRes(R.drawable.icon_setting)
            }
        }

    }

    private fun switchFooterState(v: RadioButton) {
        v.isChecked = true
        v.setTextColor(resources.getColor(R.color.black))

    }

    private fun switchSearchRes(res: Int) {
        iv_search.setImageResource(res)

    }

    private fun switchTitleState(title: String, visible: Int) {
        tv_bar_title.text = title
        tv_bar_title.visibility = visible
    }

    private fun switchFraState(oneFra: Fragment?, twoFra: Fragment?, threeFra: Fragment?, fourFra: Fragment?) {
        supportFragmentManager.beginTransaction().show(oneFra)
                .hide(twoFra)
                .hide(threeFra)
                .hide(fourFra)
                .commit()
    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_hot.setTextColor(resources.getColor(R.color.gray))
        rb_find.setTextColor(resources.getColor(R.color.gray))
    }

    override fun onPause() {
        super.onPause()
        toast?.let { toast!!.cancel() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
