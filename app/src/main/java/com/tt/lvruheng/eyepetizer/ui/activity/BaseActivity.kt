package com.tt.lvruheng.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.gyf.barlibrary.ImmersionBar

/**
 * Created by catherine on 24/08/2017.
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs(this.intent.extras)
        setWindowsFullScreen()
        setContentView(setView())
        initView()
        setListener()
    }

    fun setWindowsFullScreen() {        //设置全屏
        if (needFullScreen())
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        else
            ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()


    }

    abstract fun needFullScreen(): Boolean
    abstract fun getArgs(bundle: Bundle?)
    abstract fun setView(): Int
    abstract fun initView()
    abstract fun setListener()

}