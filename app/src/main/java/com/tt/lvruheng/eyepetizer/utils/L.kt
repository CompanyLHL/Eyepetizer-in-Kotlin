package com.tt.lvruheng.eyepetizer.utils

import android.util.Log


/**
 * Created by catherine on 25/08/2017.
 */
object L {
    val TAG = "Kotlins"

    fun line() {
        line("")
    }

    fun line(log: String) {
        Log.d(TAG, createLog("--------------" + log))


    }

    fun createLog(log: String): String {
        return "[ " + Thread.currentThread().stackTrace.toString() + " ] " + log
    }
}