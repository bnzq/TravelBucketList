package com.b00sti.travelbucketlist.utils

/**
 * Created by b00sti on 26.07.2018
 */
fun <T> Array<out Any>.toArrayList(): ArrayList<T> {
    val outItems = arrayListOf<T>()
    this.forEach { any -> outItems.add(any as T) }
    return outItems
}

fun <T> MutableList<T>.copyAndClear(): MutableList<T> {
    val outList: MutableList<T> = mutableListOf()
    outList.addAll(this)
    this.clear()
    return outList
}