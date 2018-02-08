package com.b00sti.travelbucketlist.base

import android.support.annotation.StringRes
import android.view.View
import com.b00sti.travelbucketlist.R
import com.b00sti.travelbucketlist.utils.ScreenRouter

/**
 * Created by b00sti on 08.02.2018
 */
interface BaseNavigator {

    fun showError(@StringRes resMsg: Int, @StringRes resTitle: Int = R.string.default_error)
    fun showError(msg: String?, title: String? = null)
    fun onLoading(show: Boolean)
    fun showError(msg: String?, title: String? = null, listener: (View) -> Unit = ScreenRouter.EMPTY_METHOD)

}