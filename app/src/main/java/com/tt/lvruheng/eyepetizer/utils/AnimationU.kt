package com.tt.lvruheng.eyepetizer.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation

/**
 * Created by catherine on 24/08/2017.
 */

object AnimationU {
    fun setSplashAnimation(view: View, listener: Animation.AnimationListener) {
        val alphaAnimation = AlphaAnimation(0.1f, 1.0f)
        val scaleAnimation = ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration = 1000
        view.startAnimation(animationSet)
        animationSet.setAnimationListener(listener)
    }
}