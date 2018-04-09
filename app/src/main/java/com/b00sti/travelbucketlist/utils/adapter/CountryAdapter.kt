package com.b00sti.travelbucketlist.utils.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.b00sti.travelbucketlist.base.BaseViewHolder
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.databinding.ItemCountryBinding


/**
 * Created by b00sti on 12.03.2018
 */

enum class CONTINENTS {
    AFRICA,
    ANTARCTICA,
    ASIA,
    EUROPE,
    NORTH_AMERICA,
    AUSTRALIA,
    SOUTH_AMERICA
}

data class CountryItem(val name: String = "", val continent: CONTINENTS, val visited: Boolean, val photoUri: String)

interface CountryNavigator {
    fun onItemClicked(countryItem: CountryItem)
    fun onDeleteClicked(countryItem: CountryItem)
}

class CountryViewModel(val countryItem: CountryItem) : BaseViewModel<CountryNavigator>() {

    fun onItemClicked() = getNavigator().onItemClicked(countryItem)
    fun onItemDeleted() = getNavigator().onDeleteClicked(countryItem)

}

private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CountryItem>() {
    override fun areItemsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean = oldItem == newItem
}

class CountryAdapter(val callback: CountryNavigator) : ListAdapter<CountryItem, CountryAdapter.CountryHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.CountryHolder = CountryHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CountryHolder, position: Int) = holder.onBind(position)

    inner class CountryHolder(binding: ItemCountryBinding) : BaseViewHolder<ItemCountryBinding, CountryViewModel>(binding), CountryNavigator {

        override fun onItemClicked(countryItem: CountryItem) = callback.onItemClicked(countryItem)
        override fun onDeleteClicked(countryItem: CountryItem) = callback.onDeleteClicked(countryItem)

        override fun getViewModel(position: Int): CountryViewModel {
            val viewModel = CountryViewModel(getItem(adapterPosition))
            viewModel.setNavigator(this)
            return viewModel
        }

        override fun getBindingVariable(): Int = BR.vm

    }
}