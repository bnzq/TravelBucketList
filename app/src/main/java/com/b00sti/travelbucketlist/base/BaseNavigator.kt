package com.b00sti.travelbucketlist.base

import android.support.annotation.StringRes

/**
 * Created by b00sti on 08.02.2018
 */
interface BaseNavigator {

    fun showToast(@StringRes resMsg: Int)
    fun showToast(message: String)
    fun onLoading(loading: Boolean): Unit?

}