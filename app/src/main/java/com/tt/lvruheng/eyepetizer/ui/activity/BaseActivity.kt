package com.tt.lvruheng.eyepetizer.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Created by catherine on 24/08/2017.
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setWindowsFullScreen()
        setContentView(setContentView())
        getArgs(savedInstanceState)
        initView()
        setListener()
    }

    fun setWindowsFullScreen() {        //设置全屏
        if (needFullScreen())
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    abstract fun needFullScreen(): Boolean
    abstract fun initView()
    abstract fun setListener()
    abstract fun getArgs(bundle: Bundle?)
    abstract fun setContentView(): Int

}