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
class FindDetailModel {
    fun loadData(context: Context, categoryName: String, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindDetailData(categoryName, strategy!!, Config.uuid, 83)
    }
    fun loadMoreData(context: Context,start : Int, categoryName: String, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindDetailMoreData(start,10,categoryName, strategy!!)
    }
}