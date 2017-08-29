package com.tt.lvruheng.eyepetizer.mvp.model

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.network.ApiService
import com.tt.lvruheng.eyepetizer.network.RetrofitClient
import com.tt.lvruheng.eyepetizer.utils.Config
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/6.
 */
class FindModel() {
    fun loadData(context: Context): Observable<MutableList<FindBean>>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindData(Config.uuid, 83)
    }
}