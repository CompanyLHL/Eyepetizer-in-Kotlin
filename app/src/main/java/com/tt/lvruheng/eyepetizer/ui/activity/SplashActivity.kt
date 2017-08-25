package com.tt.lvruheng.eyepetizer.ui.activity

import android.view.animation.Animation
import com.tt.lvruheng.eyepetizer.utils.newIntent
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by lvruheng on 2017/7/2.
 */
class SplashActivity : com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity() {
    override fun needFullScreen(): Boolean = true

    override fun setView(): Int = com.tt.lvruheng.eyepetizer.R.layout.activity_splash

    override fun getArgs(bundle: android.os.Bundle?) {
    }

    override fun initView() {
        tv_name_english.typeface = com.tt.lvruheng.eyepetizer.utils.TextViewU.getTypeFace("fonts/Lobster-1.4.otf")
        tv_english_intro.typeface = com.tt.lvruheng.eyepetizer.utils.TextViewU.getTypeFace("fonts/Lobster-1.4.otf")
        com.tt.lvruheng.eyepetizer.utils.AnimationU.setSplashAnimation(iv_icon_splash, object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                newIntent<MainActivity>()
                finish()
            }

            override fun onAnimationStart(p0: Animation?) {

            }
        })

    }

    override fun setListener() {
    }

}
