package com.tt.lvruheng.eyepetizer.utils

import android.graphics.Typeface
import com.tt.lvruheng.eyepetizer.ui.activity.BaseApplication

/**
 * Created by catherine on 25/08/2017.
 */
object TextViewU {
    fun getTypeFace(typeName: String): Typeface = Typeface.createFromAsset(BaseApplication.context().assets, typeName)

}