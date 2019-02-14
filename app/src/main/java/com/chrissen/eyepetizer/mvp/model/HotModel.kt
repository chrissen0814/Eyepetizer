package com.chrissen.eyepetizer.mvp.model

import android.content.Context
import com.chrissen.eyepetizer.mvp.model.bean.HotBean
import com.chrissen.eyepetizer.network.ApiService
import com.chrissen.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class HotModel {

    fun loadData(context: Context, strategy: String?) : Observable<HotBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHotData(10, strategy!!, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }

}