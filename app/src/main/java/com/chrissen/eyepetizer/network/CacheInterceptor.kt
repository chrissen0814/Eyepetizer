package com.chrissen.eyepetizer.network

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class CacheInterceptor(context: Context) : Interceptor {
    val context = context

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        if(com.chrissen.eyepetizer.utils.NetworkUtils.isNetConnected(context)){
            val response = chain?.proceed(request)
            val maxAge = 60
            val cacheControl = request?.cacheControl().toString()
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")
                    ?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        }else{
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            val maxStale = 60 * 60 * 24 * 3
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
        }
    }
}