package com.chrissen.eyepetizer.network

import com.chrissen.eyepetizer.mvp.model.bean.FindBean
import com.chrissen.eyepetizer.mvp.model.bean.HomeBean
import com.chrissen.eyepetizer.mvp.model.bean.HotBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

interface ApiService {

    companion object {
        val BASE_URL : String
        get() = "http://baobab.kaiyanapp.com/api/"
    }

    /**
     * 获取首页第一页数据
     */
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData() : Observable<HomeBean>

    /**
     * 获取首页第一页之后的数据
     */
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num : String) : Observable<HomeBean>

    /**
     * 获取发现数据
     */
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData() : Observable<MutableList<FindBean>>

    @GET("v3/ranklist")
    fun getHotData(@Query("num") num : Int, @Query("strategy") strategy: String,
                   @Query("udid") udid: String, @Query("vc") vc : Int) : Observable<HotBean>

}