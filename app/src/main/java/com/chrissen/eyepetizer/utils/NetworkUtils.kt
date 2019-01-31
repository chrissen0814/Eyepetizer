package com.chrissen.eyepetizer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

object NetworkUtils {

    fun isNetConnected(context: Context) : Boolean{
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = connectManager.activeNetworkInfo
        if (networkInfo == null) {
            return false
        }else{
            return networkInfo.isAvailable && networkInfo.isConnected
        }
    }

    fun isNetworkConnected(context: Context,typeMoblie : Int): Boolean{
        if(!isNetConnected(context)){
            return false
        }
        val connectManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo = connectManager.getNetworkInfo(typeMoblie)
        if(networkInfo==null){
            return false;
        }else{
            return  networkInfo.isConnected && networkInfo.isAvailable
        }
    }

    fun isPhoneNetConnected(context: Context) : Boolean{
        val typeMobile = ConnectivityManager.TYPE_MOBILE
        return isNetworkConnected(context, typeMobile)
    }

    fun isWifiNetConnected(context: Context) : Boolean{
        val typeMobile = ConnectivityManager.TYPE_WIFI
        return isNetworkConnected(context, typeMobile)
    }

}