package com.tt.lvruheng.eyepetizer.ui.activity

import android.app.Application
import android.content.Context

/**
 * Created by catherine on 25/08/2017.
 */
class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
        fun context(): Context = instance!!.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}

