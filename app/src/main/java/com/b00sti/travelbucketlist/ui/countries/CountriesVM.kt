package com.b00sti.travelbucketlist.ui.countries

import android.arch.lifecycle.MutableLiveData
import com.b00sti.travelbucketlist.base.BaseViewModel
import com.b00sti.travelbucketlist.utils.adapter.CONTINENTS
import com.b00sti.travelbucketlist.utils.adapter.CountryItem


/**
 * Created by b00sti on 12.03.2018
 */
class CountriesVM : BaseViewModel<CountriesNavigator>() {

    val countriesList = MutableLiveData<List<CountryItem>>()

    fun refresh() {
        val items = listOf(CountryItem("Poland" + System.currentTimeMillis(), CONTINENTS.EUROPE, true, ""),
                CountryItem("France" + System.currentTimeMillis(), CONTINENTS.EUROPE, false, ""),
                CountryItem("Spain" + System.currentTimeMillis(), CONTINENTS.EUROPE, true, ""))
        val list = countriesList.value?.toMutableList()
        list?.addAll(items)
        if (list != null) {
            countriesList.postValue(list)
        } else {
            countriesList.postValue(items)
        }
        getNavigator().refreshList()
    }

}