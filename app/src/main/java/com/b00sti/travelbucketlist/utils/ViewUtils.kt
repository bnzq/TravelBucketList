package com.b00sti.travelbucketlist.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.b00sti.travelbucketlist.App

/**
 * Created by b00sti on 07.02.2018
 */
object ViewUtils {

    fun fromHtml(string: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(string)
        }
    }

    fun getPx(value: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, App.appCtx().resources.displayMetrics)
    }

    fun hideSoftInput(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSoftInput(edit: EditText, context: Context) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}