package com.chrissen.eyepetizer.mvp.model

import android.content.Context
import com.chrissen.eyepetizer.mvp.model.bean.FindBean
import com.chrissen.eyepetizer.network.ApiService
import com.chrissen.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/12.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class FindModel {

    fun loadData(context: Context): Observable<MutableList<FindBean>>?{
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindData()
    }
}