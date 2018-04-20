package com.b00sti.travelbucketlist.utils.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.b00sti.travelbucketlist.base.AdapterNavigator
import com.b00sti.travelbucketlist.base.AdapterViewModel
import com.b00sti.travelbucketlist.base.BaseAdapter
import com.b00sti.travelbucketlist.base.BaseViewHolder
import com.b00sti.travelbucketlist.databinding.ItemBucketBinding

/**
 * Created by b00sti on 20.04.2018
 */
data class BucketItem(val name: String, val visitedCount: Int, val allCount: Int, val photoUri: String)

private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<BucketItem>() {
    override fun areItemsTheSame(oldItem: BucketItem, newItem: BucketItem): Boolean = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: BucketItem, newItem: BucketItem): Boolean = oldItem == newItem
}

class BucketsAdapter(val callback: AdapterNavigator<BucketItem>) : BaseAdapter<BucketItem, BucketsAdapter.BucketHolder>(ITEM_CALLBACK) {
    override fun getViewHolder(parent: ViewGroup): BucketHolder = BucketHolder(ItemBucketBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class BucketHolder(binding: ItemBucketBinding) : BaseViewHolder<ItemBucketBinding, AdapterViewModel<BucketItem>>(binding), AdapterNavigator<BucketItem> {

        override fun onItemClicked(item: BucketItem) = callback.onItemClicked(item)

        override fun getViewModel(position: Int): AdapterViewModel<BucketItem> {
            val viewModel = AdapterViewModel(getItem(adapterPosition))
            viewModel.setNavigator(this)
            return viewModel
        }

        override fun getBindingVariable(): Int = BR.vm
    }
}