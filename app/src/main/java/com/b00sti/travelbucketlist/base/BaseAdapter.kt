package com.b00sti.travelbucketlist.base

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup

/**
 * Created by b00sti on 20.04.2018
 */
interface AdapterNavigator<in T : Any> : EmptyNavigator {
    fun onItemClicked(item: T)
}

class AdapterViewModel<V : Any>(val item: V) : BaseViewModel<AdapterNavigator<V>>() {
    fun onItemClicked() = getNavigator().onItemClicked(item)
}

abstract class BaseAdapter<T : Any, R : BaseViewHolder<*, *>>(ITEM_CALLBACK: DiffUtil.ItemCallback<T>) : ListAdapter<T, R>(ITEM_CALLBACK) {

    abstract fun getViewHolder(parent: ViewGroup): R
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): R = getViewHolder(parent)
    override fun onBindViewHolder(holder: R, position: Int) = holder.onBind(position)

}