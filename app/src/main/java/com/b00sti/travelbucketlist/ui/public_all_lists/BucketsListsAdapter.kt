package com.b00sti.travelbucketlist.ui.public_all_lists

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.b00sti.travelbucketlist.base.AdapterNavigator
import com.b00sti.travelbucketlist.base.BaseAdapter
import com.b00sti.travelbucketlist.base.BaseVH
import com.b00sti.travelbucketlist.base.BaseVM
import com.b00sti.travelbucketlist.databinding.ItemBucketsListsBinding
import com.b00sti.travelbucketlist.model.Bucket


/**
 * Created by b00sti on 27.07.2018
 */
private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<Bucket.List>() {
    override fun areItemsTheSame(oldItem: Bucket.List, newItem: Bucket.List): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Bucket.List, newItem: Bucket.List): Boolean = oldItem == newItem
}

interface BucketsListsNavigator : AdapterNavigator<Bucket.List> {

}

class BucketsListsAdapterVM(val item: Bucket.List) : BaseVM<BucketsListsNavigator>() {
    fun onItemClicked() = getNavigator().onItemClicked(item)
}

class BucketsListsAdapter(val callback: BucketsListsNavigator) : BaseAdapter<Bucket.List, BucketsListsAdapter.VH>(ITEM_CALLBACK) {
    override fun getViewHolder(parent: ViewGroup): VH = VH(ItemBucketsListsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class VH(binding: ItemBucketsListsBinding) : BaseVH<ItemBucketsListsBinding, BucketsListsAdapterVM>(binding), BucketsListsNavigator {

        override fun onItemClicked(item: Bucket.List) = callback.onItemClicked(item)

        override fun getViewModel(position: Int): BucketsListsAdapterVM {
            val viewModel = BucketsListsAdapterVM(getItem(adapterPosition))
            viewModel.setNavigator(this)
            return viewModel
        }

        override fun getBindingVariable(): Int = BR.vm
    }
}