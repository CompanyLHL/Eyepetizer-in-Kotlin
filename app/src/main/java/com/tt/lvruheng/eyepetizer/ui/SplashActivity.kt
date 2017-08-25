package com.tt.lvruheng.eyepetizer.ui

import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.ui.activity.BaseActivity
import com.tt.lvruheng.eyepetizer.utils.AnimationU
import com.tt.lvruheng.eyepetizer.utils.TextViewU
import com.tt.lvruheng.eyepetizer.utils.newIntent
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by lvruheng on 2017/7/2.
 */
class SplashActivity : BaseActivity() {
    override fun needFullScreen(): Boolean = true

    override fun setView(): Int = R.layout.activity_splash

    override fun getArgs(bundle: Bundle?) {
    }

    override fun initView() {
        tv_name_english.typeface = TextViewU.getTypeFace("fonts/Lobster-1.4.otf")
        tv_english_intro.typeface = TextViewU.getTypeFace("fonts/Lobster-1.4.otf")
        AnimationU.setSplashAnimation(iv_icon_splash, object : Animation.AnimationListener {
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
