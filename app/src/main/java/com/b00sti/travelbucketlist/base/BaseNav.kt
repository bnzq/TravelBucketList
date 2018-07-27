package com.b00sti.travelbucketlist.base

import android.support.annotation.StringRes
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.utils.ScreenRouter

/**
 * Created by b00sti on 08.02.2018
 */

interface EmptyNavigator

interface BaseNav : EmptyNavigator {

    fun showToast(@StringRes resMsg: Int)
    fun showToast(message: String)
    fun onStartLoading(): Unit?
    fun onFinishLoading(): Unit?
    fun showErrorDialog(@StringRes resMsg: Int, @StringRes resTitle: Int = R.string.default_error)
    fun showErrorDialog(msg: String?, title: String? = null)
    fun showErrorDialog(msg: String?, title: String? = null, listener: (View) -> Unit = ScreenRouter.EMPTY_METHOD)
    fun onError(throwable: Throwable)

}