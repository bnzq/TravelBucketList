package com.b00sti.travelbucketlist.utils

import android.view.View
import android.view.animation.Animation

/**
 * Created by b00sti on 07.02.2018
 */

fun View.showNow() {
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

fun View.isShow(): Boolean {
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