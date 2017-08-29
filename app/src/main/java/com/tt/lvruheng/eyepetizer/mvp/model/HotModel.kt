package com.tt.lvruheng.eyepetizer.mvp.model

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import com.tt.lvruheng.eyepetizer.network.ApiService
import com.tt.lvruheng.eyepetizer.network.RetrofitClient
import com.tt.lvruheng.eyepetizer.utils.Config
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/7.
 */
class HotModel{
    fun loadData(context: Context, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService  = retrofitClient.create(ApiService::class.java)
          return apiService?.getHotData(10, strategy!!,Config.uuid,83)

        }
    }