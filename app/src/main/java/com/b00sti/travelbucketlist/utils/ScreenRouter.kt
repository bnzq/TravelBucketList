package com.b00sti.travelbucketlist.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.b00sti.travelbucketlist.ui.auth.AuthActivity
import com.b00sti.travelbucketlist.ui.main.MainActivity
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * Created by b00sti on 13.12.2017
 */
object ScreenRouter {

    val EMPTY_METHOD: (View) -> Unit = {}

    fun showSimpleErrorDialog(
            context: Context?, message: String? = "Please try again",
            titleText: String? = "Error", buttonText: String? = "ok",
            listener: (View) -> Unit = EMPTY_METHOD, dismiss: Boolean = false
    ): Dialog? {
        return DialogFactory.showSimpleDialog(context, message, titleText, buttonText, listener, dismiss)
    }

    /*
        fun goToReviewActivity(activity: Activity, placeId: String, userId: String) {
            val bundle = Bundle()
            bundle.putString("user", userId)
            bundle.putString("place", placeId)
            startWithParams(activity, ReviewActivity::class, bundle)
        }
    */
    fun goToAuthActivity(activity: Activity?) {
        start(activity, {
            runSimpleActivityWithClearedTop(activity, AuthActivity::class)
        })
    }

    fun goToMainActivity(activity: Activity?) {
        start(activity, {
            runSimpleActivityWithClearedTop(activity, MainActivity::class)
        })
    }

    private fun startWithoutParams(activity: Activity?, clazz: KClass<*>) {
        checkConditions(activity) {
            runSimpleActivity(activity, clazz)
        }
    }

    private fun startWithParams(activity: Activity?, clazz: KClass<*>, bundle: Bundle = Bundle()) {
        checkConditions(activity) {
            runSimpleActivity(activity, clazz, bundle)
        }
    }

    private inline fun start(activity: Activity?, func: ScreenRouter.() -> Unit) {
        checkConditions(activity) {
            ScreenRouter.func()
        }
    }

    private inline fun checkConditions(activity: Activity?, func: ScreenRouter.() -> Unit) {
        if (SystemUtils.isConnected()) {
            ScreenRouter.func()
        } else {
            activity?.let {
                //showSimpleErrorDialog(it, it.getString(R.string.no_connection))
                Timber.d("No Internet Connection")
            }
        }
    }

    private fun runSimpleActivity(activity: Activity?, clazz: KClass<*>, bundle: Bundle = Bundle()) {
        val intent = Intent(activity, clazz.java)
        intent.putExtras(bundle)
        activity?.startActivityForResult(intent, 1001)
    }

    private fun runSimpleActivityWithClearedTop(activity: Activity?, clazz: KClass<*>) {
        val intent = Intent(activity, clazz.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity?.startActivity(intent)
    }
}