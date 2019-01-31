package com.chrissen.eyepetizer.mvp.model

import android.content.Context
import com.chrissen.eyepetizer.mvp.model.bean.HomeBean
import com.chrissen.eyepetizer.network.ApiService
import com.chrissen.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class HomeModel {

    fun loadData(context: Context, isFirst : Boolean, data : String?) : Observable<HomeBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        when (isFirst){
            true -> return apiService?.getHomeData()

            false -> return apiService?.getHomeMoreData(data.toString(), "2")
        }
    }

}