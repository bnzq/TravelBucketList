package com.b00sti.travelbucketlist.utils

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.b00sti.travelbucketlist.R

/**
 * Created by b00sti on 07.02.2018
 */

fun AppCompatActivity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
}

fun Fragment.finish() {
    activity?.finish()
}


fun AppCompatActivity.showProgressDialog(): DialogFragment? {
    val dialog = ProgressBarDialog.getInstance()
    dialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppDialog)
    dialog.show(this.supportFragmentManager, dialog.tag)
    return dialog
}

fun AppCompatActivity.hideProgressDialog(dialog: DialogFragment?) {
    this.runOnUiThread({ dialog?.dismiss() })
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isHide(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.showIf(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.goneIf(show: Boolean) {
    visibility = if (show) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun Animation.addListener(
        onEnd: (Animation) -> Unit = { },
        onStart: (Animation) -> Unit = { },
        onRepeat: (Animation) -> Unit = { }
) {

    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(anim: Animation) = onEnd(anim)

        override fun onAnimationStart(anim: Animation) = onStart(anim)

        override fun onAnimationRepeat(anim: Animation) = onRepeat(anim)

    })
}