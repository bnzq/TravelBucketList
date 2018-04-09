package com.b00sti.travelbucketlist.utils

import android.databinding.ObservableField

/**
 * Created by b00sti on 09.04.2018
 */
fun ObservableField<String>.getOrEmpty(): String {
    return this.get() ?: ""
}