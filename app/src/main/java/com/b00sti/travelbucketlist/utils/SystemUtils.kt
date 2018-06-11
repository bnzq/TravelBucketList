package com.b00sti.travelbucketlist.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.provider.Settings
import com.b00sti.travelbucketlist.App

/**
 * Created by b00sti on 13.12.2017
 */
object SystemUtils {

    fun isConnected(): Boolean {
        val connectivityManager = App.appCtx().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun isGpsEnabled(): Boolean {
        val locationManger = App.appCtx().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun enableGpsIntent() {
        App.appCtx().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

}